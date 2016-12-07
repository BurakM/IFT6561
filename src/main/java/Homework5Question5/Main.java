package Homework5Question5;

import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;

public class Main {
	public static void main(String[] args) {
		// Parameters of the option
		double r = 0.05;
		double sigma = 0.50;
		double T = 1;
		double s0 = 100;
		double K = 100;
		int d = 12;
		double timeStep = T/(double)d;
		
		// Number of iterations
		int numOfSim = 1000000;
		
		// Barrier of option
		double barrier = 90;
		
		// Random number generator
		RandomStream stream = new MRG32k3a();
		
		// Barrier hit or not
		int barrierHit;
	
		// Price process
		double B = 0;
		double S = 0;		
		
		// Result for CMC method
		double Xe;
	
		// Black Scholes method
		BlackScholes Option = new BlackScholes(K, T, r, sigma);
		
		// Tallies
		Tally statX = new Tally("X");
		Tally statXe = new Tally("Xe");

		// Iterate simulations
		for(int j=0; j<numOfSim;j++){	
			
			// Initialize every iteration
			Xe = 0;
			barrierHit = 0;
			
			// Discrete time iterations
			for (int i=1; i<=d; i++){
				
				// Spot price process
				B = B+Math.sqrt(timeStep)*NormalDist.inverseF01(stream.nextDouble());
				S = s0*Math.exp((r-sigma*sigma/2)*(timeStep*i)+sigma*B);
				
				//  Barrier hit
				if(S<=barrier){
					// Barrier hit flag
					barrierHit = 1;
					// Uses Black Scholes Calculation
					if(Xe==0){
						Xe= Math.exp(-r*timeStep*i)*Option.Calculate(timeStep*i,S);
					}						
				}
			}
			
			// Tally up the results
			statX.add(Math.exp(-r*T)*Math.max(0, S-K)*(double)barrierHit);
			statXe.add(Xe);
		}
		
		// Output results
		System.out.println(statX.report());
		System.out.println(statXe.report());
		System.out.println("Barrier = " + barrier + ", variance reduction: " + statX.variance()/statXe.variance() );
	}
}
