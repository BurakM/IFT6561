package Homework4Question2;

import umontreal.ssj.probdist.BetaDist;
import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.rng.RandomStream;

public class RejectionMethod {
	double a, b, c, c2, r;
	RandomStream stream;
	
	// Constructor method
	public RejectionMethod(RandomStream stream, double a, double b, double c, double r){
		this.a = a;
		this.b = b;
		this.c = c;
		this.stream = stream;
		this.r = r;
		this.c2 = Math.exp(-r/2)/(2*Math.PI);
	}
	
	// Question d
	public double[] QuestionD(){
		double x, y, z;
		NormalDist dist = new NormalDist();
		
		// Counter of iterations needed
		int i = 0;
		while(true){
			i++;
			
			// Generate coordinates
			x = this.a*stream.nextDouble();
			y = this.b*stream.nextDouble();
			z = this.c*stream.nextDouble();
			
			// Rejection criteria
			if (dist.density(x)*dist.density(y) >= z){	
				break;
			}
		}
		double[] vec = new double[3];
		vec[0] = x;
		vec[1] = y;
		vec[2] = i;
		return (vec);
	}
	
	// Question e
	public double[] QuestionE(){
		double x, y, z, theta, rho;
		NormalDist dist = new NormalDist();
		
		// Bernoulli distribution parameters
		double p = this.a*this.b*this.c2/(this.a*this.b*this.c2+Math.PI*this.r*(this.c-this.c2)/4);
		// Counter of iterations needed
		int i = 0;
		while(true){
			i++;
			
			// If in the box
			if (stream.nextDouble()<=p){
				x = this.a*stream.nextDouble();
				y = this.b*stream.nextDouble();
				z = this.c2*stream.nextDouble();
				if (dist.density(x)*dist.density(y) >= z){	
					break;
				}
			}
			
			// If in cylinder
			else{
				// Polar coordinates
				theta = Math.PI*stream.nextDouble()/2;
				rho = Math.pow(r*stream.nextDouble(), 0.5);
				z = (this.c-this.c2)*stream.nextDouble()+this.c2;
				x = rho*Math.cos(theta);
				y = rho*Math.sin(theta);
				if (dist.density(x)*dist.density(y) >= z){	
					break;
				}
			}
		}
		double[] vec = new double[3];
		vec[0] = x;
		vec[1] = y;
		vec[2] = i;
		return (vec);
	}
	
	// Question G
	public double[] QuestionG(){
		double x, y;
		NormalDist dist = new NormalDist();
		
		// Counter of iterations needed
		int i = 0;
		while(true){
			i++;
			x = dist.inverseF(0.5+0.5*stream.nextDouble());
			y = dist.inverseF(0.5+0.5*stream.nextDouble());
			if (x<=a && y<=b){	
				break;
			}
		}
		double[] vec = new double[3];
		vec[0] = x;
		vec[1] = y;
		vec[2] = i;
		return (vec);
	}
}
