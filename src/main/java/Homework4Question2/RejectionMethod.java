package Homework4Question2;

import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.rng.RandomStream;

public class RejectionMethod {
	double a, b, c, c2, r;
	RandomStream stream;
	
	public RejectionMethod(RandomStream stream, double a, double b, double c, double r){
		this.a = a;
		this.b = b;
		this.c = c;
		this.stream = stream;
		this.r = r;
		this.c2 = Math.exp(-r/2)/(2*Math.PI);
	}
	
	public double[] QuestionD(){
		double x, y, z;
		NormalDist dist = new NormalDist();
		int i = 0;
		while(true){
			i++;
			x = this.a*stream.nextDouble();
			y = this.b*stream.nextDouble();
			z = this.c*stream.nextDouble();
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
	
	public double[] QuestionE(){
		double x, y, z;
		NormalDist dist = new NormalDist();
		int i = 1;
		while(true){
			i++;
			x = this.a*stream.nextDouble();
			y = this.b*stream.nextDouble();
			if(Math.pow(x, 2)+Math.pow(y, 2) > this.r){
				z = this.c2*stream.nextDouble();
			}
			else{
				z = this.c*stream.nextDouble();
			}
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
	
	public double[] QuestionG(){
		double x, y;
		NormalDist dist = new NormalDist();
		int i = 1;
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
