package Homework4Question1;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;

public class Main {
	public static void main (String[] args) {
		// Number of runs
		double n = 100;
		
		// Characteristics of the asian option
		double K = 101;
		double s0 = 100;
		double T = 1;
		double t0 = 0;
		double r = 0.1;
		int d = 16;
		
		// Variance Gamma process parameters
		double v = 0.3; 
		double theta = -0.1436;
		double sigma = 0.12136;
		double mu = 1;
		double omega = 1/v*Math.log(1-theta*v-sigma*sigma*v/2);
		
		// Used for method 3
		double mu_plus=(Math.sqrt(theta*theta+2*sigma*sigma/v)+theta)/2;
		double mu_minus=(Math.sqrt(theta*theta+2*sigma*sigma/v)-theta)/2;
		double v_plus=mu_plus*mu_plus*v;
		double v_minus=mu_minus*mu_minus*v;
		RandomStream stream = new MRG32k3a();
		
		// Tallies
		Tally Results1 = new Tally("Results - Method 1");
		Tally Results2 = new Tally("Results - Method 2");
		Tally Results3 = new Tally("Results - Method 3");
		
		// Instance of VGP class
		VarianceGammaProcess VGP = new VarianceGammaProcess(mu, sigma, theta, v);
		
		// Process is stored in this vector
		double[] path;
		
		// Method 1
		for (int k=0; k<n; k++){
			double sum = 0;
			path = VGP.Method1(d, T, stream);
			for (int i=1; i<=d; i++){
				sum=sum+s0*Math.exp(r*(i*T/d)+path[i]+omega*(i*T/d));	
			}
			Results1.add(Math.max(0, Math.exp(-r*T)*((double)1/d*sum-K)));
		}
		System.out.println (Results1.report (0.95, 3));
		
		// Method 2
		for (int k=0; k<n; k++){
			double sum = 0;
			path = VGP.Method2(d, T, stream);
			for (int i=1; i<=d; i++){
				sum=sum+s0*Math.exp(r*(i*T/d)+path[i]+omega*(i*T/d));	
			}
			Results2.add(Math.max(0, Math.exp(-r*T)*((double)1/d*sum-K)));
		}
		System.out.println (Results2.report (0.95, 3));
		
		// Method 3
		for (int k=0; k<n; k++){
			double sum = 0;
			path = VGP.Method3(d, T, stream, mu_plus, mu_minus, v_plus, v_minus);
			for (int i=1; i<=d; i++){
				sum=sum+s0*Math.exp(r*(i*T/d)+path[i]+omega*(i*T/d));	
			}
			Results3.add(Math.max(0, Math.exp(-r*T)*((double)1/d*sum-K)));
		}
		System.out.println (Results3.report (0.95, 3));
	}
}