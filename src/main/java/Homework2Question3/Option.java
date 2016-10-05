package Homework2Question3;

import umontreal.ssj.probdist.NormalDist;

// This class stores methods/variables of option

public class Option {
	double s;
	double sigma;
	double T; // This is the time remaining
	double K;
	double r;
	
	// Constructor class
	public Option(double s, double sigma, double T, double K, double r){
		this.s = s;
		this.sigma = sigma;
		this.T = T;
		this.K = K;
		this.r = r;
	}
	
	// Pricing method
	public double Price(){
		double z0= (Math.log(this.K/this.s)-(this.r-Math.pow(this.sigma, 2)/2)*this.T)/(this.sigma*Math.pow(this.T, 0.5));
		NormalDist distNormal = new NormalDist();
		double price = this.s*distNormal.cdf(-z0+this.sigma*Math.pow(this.T, 0.5))-this.K*(Math.exp(-this.r*this.T))*distNormal.cdf(-z0);
		return price;
	}
	
	// Delta of option method
	public double Delta(){
		double z0= (Math.log(this.K/this.s)-(this.r-Math.pow(this.sigma, 2)/2)*this.T)/(this.sigma*Math.pow(this.T, 0.5));
		NormalDist distNormal = new NormalDist();
		double delta = distNormal.cdf(-z0+this.sigma*Math.pow(this.T, 0.5));
		return delta;
	}
	
	// Update time to expiry and spot price.
	public void Update(double s, double T){
		this.T = T; 
		this.s = s;
	}
	
}
