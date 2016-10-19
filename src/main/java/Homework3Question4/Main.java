package Homework3Question4;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;

// This is the solution for question 4
public class Main {
	public static void main (String[] args) {
		
		// Generate the distribution
		GompertzMakehamDist dist = new GompertzMakehamDist( 0.000075858, Math.log(1.09144),  0.0005);
		
		// Generate the stream
		RandomStream stream = new MRG32k3a();
		
		// Number of runs we do.
		int totalrun = 100000;
		
		// Number of runs of the whole simulation. Used for question c). Otherwise leave as 0
		int totalrunC = 1;
		
		// The age we generate
		double X;
		
		// Parameters of the problem
		double a = 1;
		double r = 0.05;
		double b = 35;
		
		// Constant used for truncation
		double c = dist.cdf(45);
		
		// Variables used during simulation
		double W2;
		double W1;
		double floorX;
		double annuity;
		double truncatedU;
		
		// Tallies on the variables
		Tally statW2minW1 = new Tally ("W2 - W1");
		Tally statW2 = new Tally ("W2");
		Tally statW1 = new Tally ("W1");
		Tally payment = new Tally ("Life insurance");
		Tally paymentC = new Tally ("C");
		
		for (int j = 0; j<totalrunC; j++){
			
			for (int i = 0; i<totalrun; i++){
				
				// Generate X using truncated distribution
				// this generates a X bigger then 45
				truncatedU = c+(1-c)*stream.nextDouble();
				X = dist.inverseF(truncatedU);
				
				// Uncomment following line of code if you want to check
				// if there is an error on the inverse F function
				//System.out.println(dist.cdf(X)-truncatedU);
				
				// Calculate W1 and W2
				if (X > 65){
					W1 = (-a/r)*(Math.exp(-r*(X-45))-Math.exp(-20*r));
					floorX = 20;
					}
				else {
					W1 = 0;
					floorX = Math.floor(X-45);
					}
				W2 = b*Math.exp(-r*(X-45));
				annuity = (1-Math.exp(-r*floorX))/(r);
				
				// Tallies here
				statW2minW1.add( W2 - W1);
				statW2.add(W2);
				statW1.add(W1);
				payment.add(1+annuity);
				
				}
			
			if (totalrunC==1){
				System.out.println (statW2minW1.report (0.95, 3));
				System.out.println (statW2.report (0.95, 3));
				System.out.println (payment.report (0.95, 3));
				System.out.println (statW1.report (0.95, 3));
			}
			
			paymentC.add(statW1.average()/payment.average());
			statW2minW1.init();
			statW2.init();
			statW1.init();
			payment.init();
			
		}	
			
		System.out.println (paymentC.report (0.95, 3));
		
		}
		
}
