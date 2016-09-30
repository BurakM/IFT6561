package Homework2Question3;

import umontreal.ssj.stat.Tally;

public class Main {
	public static void main(String[] args) {
		double T = 1;
		int d;
		double K = 100;
		double S = K;
	    double r = 0.03;
	    double sigma = 0.2;
	    int runs = 1000;
	    Tally stats = new Tally ("Statistics on hedge");
	    
	    d = 12;
	    System.out.println ("Montly hedging");
	    SimulationCollector simcollect = new SimulationCollector(S, sigma, T, K, r, d, runs, stats);
	    simcollect.RunSimulations();
	    System.out.println (stats.report (0.95, 3));
	    
	    d = 52;
	    System.out.println ("Weekly hedging");
	    SimulationCollector simcollect2 = new SimulationCollector(S, sigma, T, K, r, d, runs, stats);
	    simcollect2.RunSimulations();
	    System.out.println (stats.report (0.95, 3));
	    
	    d = 250;
	    System.out.println ("Daily hedging");
	    SimulationCollector simcollect3 = new SimulationCollector(S, sigma, T, K, r, d, runs, stats);
	    simcollect3.RunSimulations();
	    System.out.println (stats.report (0.95, 3));
	}
}
