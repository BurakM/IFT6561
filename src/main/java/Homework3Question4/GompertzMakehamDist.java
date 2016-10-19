package Homework3Question4;

import umontreal.ssj.probdist.ContinuousDistribution;

public class GompertzMakehamDist extends ContinuousDistribution {
	double alpha;
	double beta;
	double lambda;
	
	double xmin = 45;
	double xmax = 112;
	
	// Takes in parameters of the distribution
	public GompertzMakehamDist(double alpha, double beta, double lambda){
		this.alpha = alpha;
		this.beta = beta;
		this.lambda = lambda;
	}
	
	// The cumulative distribution function method
	public double cdf(double x) {
		return (1-Math.exp(-this.lambda*x-(this.alpha/this.beta)*(Math.exp(this.beta*x)-1)));
	}
	
	public double density(double x) {
		return ((this.lambda+this.alpha*Math.exp(this.beta*x))*Math.exp(-this.lambda*x
				-(this.alpha/this.beta)*(Math.exp(this.beta*x)-1)));
	}
	
	// Quantile method
	public double inverseF (double u) {
		
		// Sets the initial range x1 and x2
		double x1 = this.xmin;
		double x2 = this.xmax;
		double x = (x2 + x1)/2;
		
		// Loops while the precision criteria isn't met
		while((x2 - x1)/x > 0.00001 && (cdf(x2)-cdf(x1) )> java.lang.Math.ulp(cdf(x2))){
			
			// Zeroes in on the root
			x = (x2 + x1)/2;
			if (cdf(x)<u){x1 = x;}
			else {x2=x;}			
		}
		return x;
	}

	public double[] getParams() {
		return (new double[]{this.alpha, this.beta, this.lambda});
	}
}
