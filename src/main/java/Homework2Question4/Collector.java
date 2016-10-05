package Homework2Question4;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;

// Simulations executed from this class

public class Collector {
	Asian AsianOption;
	Asian AsianOptionDelta;
	double r, sigma, strike, s0, delta;
	int s;
	double[] zeta;
	Tally stat = new Tally("Stats on delta of asian option");
	
	// Constructor class
	public Collector (double r, double sigma, double strike, double s0,
			double delta, int s, double[] zeta) {
		this.r = r;
		this.sigma = sigma;
		this.strike = strike;
		this.s0 = s0;
		this.delta = delta;
		this.s = s;
		this.zeta = zeta;
		AsianOption = new Asian(r, sigma, strike, s0, s, zeta);
		AsianOptionDelta = new Asian(r, sigma, strike, s0 + delta, s, zeta);
	}
	
	// Independant random number
	public void IRN (int n) {
		double AsianOptionPayoff;
		double AsianOptionDeltaPayoff;
		stat.init();
		RandomStream stream = new MRG32k3a();
		for (int i = 0; i < n; i++)
		{
			AsianOptionPayoff = this.AsianOption.simulateRun(1, stream);
			AsianOptionDeltaPayoff = this.AsianOptionDelta.simulateRun(1, stream);
			stat.add((AsianOptionDeltaPayoff-AsianOptionPayoff)/this.delta);
		}
	}
	
	// Common random number
	public void CRN (int n) {
		RandomStream stream = new MRG32k3a();
		double AsianOptionPayoff;
		double AsianOptionDeltaPayoff;
		stat.init();
		for (int i = 0; i < n; i++)
		{
			AsianOptionPayoff = this.AsianOption.simulateRun(1, stream);
			stream.resetStartSubstream();
			AsianOptionDeltaPayoff = this.AsianOptionDelta.simulateRun(1, stream);
			stream.resetNextSubstream();
			stat.add((AsianOptionDeltaPayoff-AsianOptionPayoff)/this.delta);
		}
	}
	
	// Stochastic derivative for question e)
	public void StochasticDerivative(int n){
		Asian AsianOptionSto = new Asian(r, sigma, strike, s0, s, zeta);
		stat.init();		
		RandomStream stream = new MRG32k3a();
		for (int i = 0; i< n; i++) { 
			AsianOptionSto.generatePath(stream);  
		    stat.add (AsianOptionSto.getPayoffSto());  
		} 
	}
}


