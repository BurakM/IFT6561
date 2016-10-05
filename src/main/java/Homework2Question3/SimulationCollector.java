package Homework2Question3;

import umontreal.ssj.charts.HistogramChart;
import umontreal.ssj.charts.ScatterChart;
import umontreal.ssj.randvar.NormalGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.stat.Tally;
import umontreal.ssj.stat.TallyStore;

public class SimulationCollector {
	double S, sigma, T, K, r;
	int d, runs;
	Tally stats;
	
	// Constructor. Parses arguments.
	public SimulationCollector(double S, double sigma, double T, double K, double r, int d, int runs, Tally stats){
		this.S = S;
		this.sigma = sigma;
		this.T = T;
		this.K = K;
		this.r = r;
		this.d = d;
		this.runs = runs;
		this.stats = stats;
	}
	
	// Execute simulations
	public void RunSimulations(){
		
		// Tally storage
		TallyStore tallhist = new TallyStore();
		this.stats.init();
		
		// Results stored here
		double[][] points = new double[2][this.runs];
		
		// Initialize simulation
		Simulator simulation = new Simulator(this.S, this.sigma, this.T, this.K, this.r, this.d);
	    for (int i = 0; i < this.runs; i++)
	    {
	    	// Generate rand number generator
			NormalGen normalGen = new NormalGen(new MRG32k3a());
			
			// Execute simulation
		    simulation.runSimulation(normalGen);
		    
		    // Collect results
		    this.stats.add(simulation.finalOptionValue-simulation.finalPortValue);
		    tallhist.add(simulation.finalOptionValue-simulation.finalPortValue);
		    points[0][i] = simulation.finalOptionValue;
	        points[1][i] = simulation.finalPortValue;
	    }
	   
	    // Chart results
	    ScatterChart chart = new ScatterChart("Scatter Plot - Portfolio vs. Option with " + Integer.toString(this.d) + " hedge periods", "Option Value at time T", "Portfolio Value at time T", points);
	    chart.view(1200, 800);
	    HistogramChart hist = new HistogramChart("Histogram Option minus Portfolio " + Integer.toString(this.d) + " hedge periods","Option minus Portfolio", "Occurences" , tallhist);
	    hist.view(1200, 800);
	}

}
