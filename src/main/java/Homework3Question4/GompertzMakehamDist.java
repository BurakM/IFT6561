package Homework3Question4;

import umontreal.ssj.probdist.ContinuousDistribution;

public class GompertzMakehamDist extends ContinuousDistribution {
	double alpha;
	double beta;
	double lambda;
	
	public GompertzMakehamDist(double alpha, double beta, double lambda){
		this.alpha = alpha;
		this.beta = beta;
		this.lambda = lambda;
	}
	
	public double cdf(double x) {
		return (1-Math.exp(-this.lambda*x-(this.alpha/this.beta)*(Math.exp(this.beta*x)-1)));
	}
	
	public double density(double x) {
		return ((this.lambda+this.alpha*Math.exp(this.beta*x))*Math.exp(-this.lambda*x
				-(this.alpha/this.beta)*(Math.exp(this.beta*x)-1)));
	}
	
	public double inverseF (double u) {
		double x = (this.alpha*Math.exp(this.alpha/this.lambda)*Math.pow((1-u), (-this.beta/this.lambda)))/(this.lambda);
		//double x = (this.alpha/this.lambda)*Math.exp(this.alpha/this.lambda-this.beta*Math.log(1-u)/this.lambda);
		LambertW lambertW = new LambertW(x);
		return this.alpha/(this.beta*this.lambda) - (1/this.lambda)*Math.log(1-u) - (1/this.beta)*lambertW.Compute();
	}

	public double[] getParams() {
		return (new double[]{this.alpha, this.beta, this.lambda});
	}
}
