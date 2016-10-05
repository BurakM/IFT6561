package Homework2Question3;

import umontreal.ssj.stat.Tally;

// Question number 3 from the second assignment. 
// Code executed from this file

public class Main {
	public static void main(String[] args) {
		
		// Declares the parameters defined in question.
		double T = 1;
		int d;
		double K = 100;
		double S = K;
	    double r = 0.03;
	    double sigma = 0.2;
	    int runs = 1000;
	    
	    // Tallies are done in these objects
	    Tally stats = new Tally ("Statistics on hedge, monthly");
	    Tally stats2 = new Tally ("Statistics on hedge, weekly");
	    Tally stats3 = new Tally ("Statistics on hedge, daily");
	    
	    
	    // Monthly hedging
	    d = 12;
	    System.out.println ("Montly hedging");
	    SimulationCollector simcollect = new SimulationCollector(S, sigma, T, K, r, d, runs, stats);
	    simcollect.RunSimulations();
	    System.out.println (stats.report (0.95, 3));
	    
	    
	    // Weekly hedging
	    d = 52;
	    System.out.println ("Weekly hedging");
	    SimulationCollector simcollect2 = new SimulationCollector(S, sigma, T, K, r, d, runs, stats2);
	    simcollect2.RunSimulations();
	    System.out.println (stats2.report (0.95, 3));
	    
	    
	    // Daily hedging
	    d = 250;
	    System.out.println ("Daily hedging");
	    SimulationCollector simcollect3 = new SimulationCollector(S, sigma, T, K, r, d, runs, stats3);
	    simcollect3.RunSimulations();
	    System.out.println (stats3.report (0.95, 3));
	}
}
