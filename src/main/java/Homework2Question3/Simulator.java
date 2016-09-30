package Homework2Question3;

import umontreal.ssj.randvar.NormalGen;

public class Simulator {
	double s0;
	double sigma;
	double T;
	double K;
	double r;
	int d;
	
	double finalOptionValue;
	double finalPortValue;
	
	public Simulator(double s, double sigma, double T, double K, double r, int d){
		this.s0 = s;
		this.sigma = sigma;
		this.T = T;
		this.K = K;
		this.r = r;
		this.d = d;
	}
	
	public void runSimulation(NormalGen normalGen){
		double t = 0;
		double B = 0;
		double S = this.s0;
		double Y = 0;
		double previousDelta;
		

		Option option = new Option(this.s0, this.sigma, this.T, this.K, this.r);
		previousDelta = option.Delta(); 
		
		for (int j = 1; j < this.d; j++){
			
			t = t + this.T/this.d;
			B = B + Math.pow(this.T/this.d,0.5)*normalGen.nextDouble();
			S = this.s0*Math.exp((this.r-Math.pow(this.sigma,2)/2)*t+this.sigma*B);
			
			option.Update(S, this.T-t);
			Y = Math.exp(this.r*this.T/this.d)*Y-(option.Delta()-previousDelta)*S;
			
			previousDelta = option.Delta();
		}

		B = B + Math.pow(this.T/this.d, 0.5)*normalGen.nextDouble();
		S = this.s0*Math.exp((this.r-Math.pow(this.sigma,2)/2)*t+this.sigma*B);
		this.finalOptionValue =  Math.max(0, S-this.K);
		this.finalPortValue = Y + option.Delta()*S;
		
	}
}

