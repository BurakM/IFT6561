package Homework4Question1;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;

public class Main {
	public static void main (String[] args) {
		double n = 10000;
		double K = 101;
		double s0 = 100;
		double T = 1;
		double t0 = 0;
		double r = 0.1;
		int d = 16;
		double v=0.3; 
		double theta=-0.1436;
		double sigma=0.12136;
		double mu=1;
		double omega=1/v*Math.log(1-theta*v-sigma*sigma*v/2);
		RandomStream stream = new MRG32k3a();
		
		Tally Results = new Tally("Results");
		
		VarianceGammaProcess VGP = new VarianceGammaProcess(mu, sigma, theta, v);
		double[] path;
		
		for (int k=0; k<n; k++){
			double sum = 0;
			path = VGP.Method1(d, T, stream);
			for (int i=1; i<=d; i++){
				sum=sum+s0*Math.exp(r*(i*T/d)+path[i]+omega*(i*T/d));	
			}
			Results.add(Math.max(0, Math.exp(-r*T)*((double)1/d*sum-K)));
		}
		System.out.println (Results.report (0.95, 3));
		
	}
}
