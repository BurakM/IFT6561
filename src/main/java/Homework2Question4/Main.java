package Homework2Question4;

// Question 4, homework 2

public class Main {
	public static void main (String[] args) {
		
		// Values of problem
		double r = 0.05;
		double sigma = 0.5;
		double s0= 100;
		double strike = 100;
		int s = 12;
		double[] zeta = new double[s+1];   zeta[0] = 0.0;
	    for (int j=1; j<=s; j++) {zeta[j] = (double)j / (double)s;}
	    
	    // Size of step for numerical differentiation
		double delta = 0.01	;
		
		// Number of runs
		int runs = 10000;
		
		// Initialize class that contains the simulations
		Collector question = new Collector(r, sigma, strike, s0, delta, s, zeta);
		
		// Execute simulations and output stats for
		// Ind. random numbers
		question.IRN(runs);
		System.out.println (question.stat.report());
		
		// Execute simulations and output stats for
		// Common random numbers
		question.CRN(runs);
		System.out.println (question.stat.report());

		// Execute simulations and output stats for
		// stochastic derivative
		question.StochasticDerivative(runs);
		System.out.println (question.stat.report());
	}
		
}

