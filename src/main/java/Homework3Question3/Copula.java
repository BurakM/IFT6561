package Homework3Question3;

import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.randvar.NormalGen;
import umontreal.ssj.rng.RandomStream;

public class Copula {
	double p;
	double py;
	double u1;
	double u2;
	RandomStream stream;
	NormalDist distNormal = new NormalDist();
	NormalGen genNormal;
	
	Copula(double rhos, RandomStream stream){
		this.p = (rhos + 1)/2;
		this.py = 2*Math.sin(rhos*Math.PI/6);
		this.stream = stream;
		this.genNormal = new NormalGen(stream);
	}
	
	public void NORTA(){
		double z1 = this.genNormal.nextDouble();
		double z2 = this.genNormal.nextDouble();
		
		double y1 = z1;
		double y2 = this.py*z1 + z2*Math.pow((1-(Math.pow(this.py,2))), 0.5);
		
		this.u1 = distNormal.cdf(y1);
		this.u2 = distNormal.cdf(y2);
	} 
	
	public void Frechet(){
		this.u1 = this.stream.nextDouble();
		double testP = this.stream.nextDouble();
		if (testP < this.p){
			this.u2 = this.u1; 
		}
		else{
			this.u2 = (1-this.u1); 
		}
	} 
}
