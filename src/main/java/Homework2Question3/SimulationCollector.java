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
	
	public void RunSimulations(){
		TallyStore tallhist = new TallyStore();
		
		this.stats.init();
		double[][] points = new double[2][this.runs];
		Simulator simulation = new Simulator(this.S, this.sigma, this.T, this.K, this.r, this.d);
	    for (int i = 0; i < this.runs; i++)
	    {
			NormalGen normalGen = new NormalGen(new MRG32k3a());
		    simulation.runSimulation(normalGen);
		    this.stats.add(simulation.finalOptionValue-simulation.finalPortValue);
		    tallhist.add(simulation.finalOptionValue-simulation.finalPortValue);
		    points[0][i] = simulation.finalOptionValue;
	        points[1][i] = simulation.finalPortValue;
	    }
	   
	    ScatterChart chart = new ScatterChart("Scatter Plot - Portfolio vs. Option with " + Integer.toString(this.d) + " redhedges", "Option Value at time T", "Portfolio Value at time T", points);
	    chart.view(1200, 800);
	    HistogramChart hist = new HistogramChart("Histogram Option minus Portfolio " + Integer.toString(this.d) + " redhedges","Option minus Portfolio", "Occurences" , tallhist);
	    hist.view(1200, 800);
	}

}
