package Homework2Question3;

import umontreal.ssj.randvar.NormalGen;

// Dirty work done in this class

public class Simulator {
	// Parameters of option
	double s0;
	double sigma;
	double T;
	double K;
	double r;
	int d;
	
	// Value of options and portfolio at time = T
	double finalOptionValue;
	double finalPortValue;
	
	// Constructor methods
	public Simulator(double s, double sigma, double T, double K, double r, int d){
		this.s0 = s;
		this.sigma = sigma;
		this.T = T;
		this.K = K;
		this.r = r;
		this.d = d;
	}
	
	// Execute simulation
	public void runSimulation(NormalGen normalGen){
		double t = 0;
		double B = 0;
		double S = this.s0;
		double Y = 0;
		double previousDelta;

		// Create option
		Option option = new Option(this.s0, this.sigma, this.T, this.K, this.r);
		// Get option's initial delta.
		previousDelta = option.Delta(); 
		
		// Initialize portfolio to initial hedge and value of option.
		Y = -previousDelta*S+option.Price();
		
		for (int j = 1; j < this.d; j++){
			
			// Step time
			t = t + this.T/this.d;
			// Generate next price
			B = B + Math.pow(this.T/this.d,0.5)*normalGen.nextDouble();
			S = this.s0*Math.exp((this.r-Math.pow(this.sigma,2)/2)*t+this.sigma*B);
			// Update option with information
			option.Update(S, this.T-t);
			// Obtain current portfolio value
			Y = Math.exp(this.r*this.T/this.d)*Y-(option.Delta()-previousDelta)*S;
			// Store previous delta.
			previousDelta = option.Delta();
		}

		// Get last price of spot
		B = B + Math.pow(this.T/this.d, 0.5)*normalGen.nextDouble();
		S = this.s0*Math.exp((this.r-Math.pow(this.sigma,2)/2)*t+this.sigma*B);
		
		// Get final portfolio values values.
		this.finalOptionValue =  Math.max(0, S-this.K);
		this.finalPortValue = Y + option.Delta()*S;
		
	}
}

