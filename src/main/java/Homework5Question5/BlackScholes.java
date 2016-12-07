package Homework5Question5;

import umontreal.ssj.probdist.NormalDist;

public class BlackScholes {
	
	double S, K, r, sigma, T, to, price;
	
	// Constructor method for Black Scholes Calculator
	public BlackScholes(double K, double T, double r, double sigma) {
		
		this.K = K;
		this.T = T;	
		this.r = r;
		this.sigma = sigma;
	}

	// Calculate Black Scholes price
	public double Calculate(double barrierT, double S){
		
		double d1 =(Math.log(S/K)+(r+sigma*sigma/2)*(T-barrierT))/(Math.sqrt(T-barrierT)*sigma);
		double d2 = d1-Math.sqrt(T-barrierT)*sigma;
		price = S*NormalDist.cdf01(d1)-K*Math.exp(-r*(T-barrierT))*NormalDist.cdf01(d2);
		return price;
	}
}	
	