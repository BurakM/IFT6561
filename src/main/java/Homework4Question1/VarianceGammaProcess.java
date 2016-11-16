package Homework4Question1;

import umontreal.ssj.probdist.BetaDist;
import umontreal.ssj.probdist.GammaDist;
import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.rng.RandomStream;

public class VarianceGammaProcess {

	double mu, sigma, theta, v, deltaTau, u, deltaTime;
	
	// Constructor method
	public VarianceGammaProcess(double mu, double sigma, double theta, double v){
		this.mu = mu;
		this.sigma = sigma;
		this.theta = theta;
		this.v = v;
	}
	
	// Method 1
	public double[] Method1(int d, double T, RandomStream stream){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		
		// Initial values
		tau[0] = 0;
		path[0] = 0;
		
		// Gamma process
		for (int i=1; i<=d;i++){
			tau[i]= tau[i-1]+GammaProcess(T/d, stream.nextDouble(),this.mu,this.v);
		}
		
		// Brownian motion
		for (int i=1; i<=d;i++){			
			path[i]=path[i-1]+BrownianMotion(stream.nextDouble(),(this.theta)*(tau[i]-tau[i-1]), (this.sigma)*(this.sigma)*(tau[i]-tau[i-1]));
		}
		return path;
	}
	
	// Method 2
	public double[] Method2(int d, double T, RandomStream stream){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		
		// Initial values
		tau[0] = 0;
		path[0] = 0;
		
		// Calculate at time t=T
		tau[d] = tau[0]+GammaProcess(T, stream.nextDouble(),mu,v);	
		path[d]= path[0]+BrownianMotion(stream.nextDouble(),theta*(tau[d]-tau[0]), sigma*sigma*(tau[d]-tau[0]));
		
		// Used to construct the bridge
		int index = d/2;
		int i = 1;
		int temp;
		double mean, variance;
		int tauPlus, tauMin, tauStar;
		
		while(index>=1){
			i = 1;
			while(i*index<d){
				// Indexes
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				// Calculate the gamma process. Uses beta distribution, since we are using condition distribution
				tau[tauStar]=tau[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu,v)*(tau[tauPlus]-tau[tauMin]);
				
				// Mean and variance of brownian motion
				mean=path[tauMin]+(tau[tauStar]-tau[tauMin])/(tau[tauPlus]-tau[tauMin])*(path[tauPlus]-path[tauMin]);
				variance=(tau[tauStar]-tau[tauMin])*(tau[tauPlus]-tau[tauStar])/(tau[tauPlus]-tau[tauMin])*sigma*sigma;
				
				// Brownian motion
				path[tauStar]=BrownianMotion(stream.nextDouble(), mean, variance);	
				i=i+2;		
			}
			temp=index/2;
			index=temp;
		}
		return path;
	}
	
	public double[] Method3(int d, double T, RandomStream stream, double mu_plus, double mu_minus, double v_plus, double v_minus){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		double[] tau_plus = new double[d+1]; 
		double[] tau_minus = new double[d+1];
		
		// Initial Values
		tau_plus[0]=0; tau_minus[0]=0; tau[0]=0;
		
		// Values at time t=T
		tau_plus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_plus,v_plus);	
		tau_minus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_minus,v_minus);
		int tauPlus, tauMin, tauStar;
		
		// Used to construct the bridge
		int index = d/2;
		int i = 1;
		int temp;
		while(index>=1){
			i = 1;
			while(i*index<d){
				// Indexes
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				// Tau plus and tau minus
				tau_plus[tauStar] = tau_plus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_plus,v_plus)*(tau_plus[tauPlus]-tau_plus[tauMin]);
				tau_minus[tauStar] = tau_minus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_minus,v_minus)*(tau_minus[tauPlus]-tau_minus[tauMin]);							
				
				i=i+2;	
			}
			temp=index/2;
			index=temp;
		}
		for (int j = 1; j<=d; j++){
			// Calculate variance gamma from tau+ and tau-
			path[j] = tau_plus[j] - tau_minus[j];
		}
		return path;
	}
	
	// Brownian motion method
	public double BrownianMotion(double u, double mean, double variance){
		return mean+Math.sqrt(variance)*NormalDist.inverseF01(u);
	}
	
	// Gamma process method
	public double GammaProcess(double deltaTime, double u, double mu, double v){
		return GammaDist.inverseF(deltaTime/v*mu*mu, mu/v, 7, u);
	}
	
	// Beta distribution method
	// Take notice of the truncature 
	public double Beta(double t, double ta, double tb, double u,double mu, double v){
		return BetaDist.inverseF((t-ta)*mu*mu/v, (tb-t)*mu*mu/v, 0.0001+0.9997*u);
	}
	
}
