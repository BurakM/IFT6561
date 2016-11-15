package Homework4Question1;

import umontreal.ssj.probdist.BetaDist;
import umontreal.ssj.probdist.GammaDist;
import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.rng.RandomStream;

public class VarianceGammaProcess {

	double mu, sigma, theta, v, deltaTau, u, deltaTime;
	
	public VarianceGammaProcess(double mu, double sigma, double theta, double v){
		this.mu = mu;
		this.sigma = sigma;
		this.theta = theta;
		this.v = v;
	}
	
	public double[] Method1(int d, double T, RandomStream stream){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		tau[0] = 0;
		path[0] = 0;
		for (int i=1; i<=d;i++){
			tau[i]= tau[i-1]+GammaProcess(T/d, stream.nextDouble(),mu,v);
		}
		for (int i=1; i<=d;i++){			
			path[i]=path[i-1]+BrownianMotion(stream.nextDouble(),theta*(tau[i]-tau[i-1]),sigma*sigma*(tau[i]-tau[i-1]));
		}
		return path;
	}
	
	public double[] Method2(int d, double T, RandomStream stream){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		tau[0] = 0;
		path[0] = 0;
		tau[d] = tau[0]+GammaProcess(T, stream.nextDouble(),mu,v);	
		path[d]= path[0]+BrownianMotion(stream.nextDouble(),theta*(tau[d]-tau[0]), sigma*sigma*(tau[d]-tau[0]));
		int index = d/2;
		int i = 1;
		int temp;
		double mean, variance;
		int tauPlus, tauMin, tauStar;
		
		while(index>=1){
			
			i = 1;
			while(i*index<d){
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				tau[tauStar]=tau[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu,v)*(tau[tauPlus]-tau[tauMin]);
				
				mean=path[tauMin]+(tau[tauStar]-tau[tauMin])/(tau[tauPlus]-tau[tauMin])*(path[tauPlus]-path[tauMin]);
				variance=(tau[tauStar]-tau[tauMin])*(tau[tauPlus]-tau[tauStar])/(tau[tauPlus]-tau[tauMin])*sigma*sigma;
				
				path[tauStar]=BrownianMotion(stream.nextDouble(), mean, variance);	
				i=i+2;
				
			}
			temp=index/2;
			index=temp;
		}
		return path;
	}
	
	public double[] Method3(int d, double T, RandomStream stream){
		double mu_plus=(Math.sqrt(theta*theta+2*sigma*sigma/v)+theta)/2;
		double mu_minus=(Math.sqrt(theta*theta+2*sigma*sigma/v)-theta)/2;
		double v_plus=mu_plus*mu_plus*v;
		double v_minus=mu_minus*mu_minus*v;
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		double[] tau_plus = new double[d+1]; 
		double[] tau_minus = new double[d+1];
		tau_plus[0]=0; tau_minus[0]=0; tau[0]=0;
		
		tau_plus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_plus,v_plus);	
		tau_minus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_minus,v_minus);
		int tauPlus, tauMin, tauStar;
		
		int index = d/2;
		int i = 1;
		int temp;
		while(index>=1){
			
			i = 1;
			while(i*index<d){
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				tau_plus[tauStar] = tau_plus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_plus,v_plus)*(tau_plus[tauPlus]-tau_plus[tauMin]);
				tau_minus[tauStar] = tau_minus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_minus,v_minus)*(tau_minus[tauPlus]-tau_minus[tauMin]);							
				
				i=i+2;
				
			}
			temp=index/2;
			index=temp;
		}
		for (int j = 1; j<=d; j++){
			path[j] = tau_plus[j] - tau_minus[j];
		}
		return path;
	}
	
	public double BrownianMotion(double u, double mean, double variance){
		return mean+Math.sqrt(variance)*NormalDist.inverseF01(u);
	}
	
	public double GammaProcess(double deltaTime, double u, double mu, double v){
		return GammaDist.inverseF(deltaTime/v*mu*mu, mu/v, 7, u);
	}
	
	public double Beta(double t, double ta, double tb, double u,double mu, double v){
		return BetaDist.inverseF((t-ta)*mu*mu/v, (tb-t)*mu*mu/v, u*0.99999);
	}
	
}
