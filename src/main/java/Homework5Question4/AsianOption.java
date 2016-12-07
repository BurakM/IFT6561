package Homework5Question4;

import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.stat.Tally;
import umontreal.ssj.rng.RandomStream;

public class AsianOption {

	// Tallies for all methods
	Tally Stats1 = new Tally("No VRT");
	Tally Stats2 = new Tally("Geometric control variable");
	Tally Stats3 = new Tally("Sum control variable");
	Tally Stats4 = new Tally("Both control variables");
	
	// Constructor method
	public AsianOption (double s0, double r, double sigma, double T, int numSimulations, RandomStream stream, int d, double[] time, double K, boolean method5){

		// Contains the time series of the data
		double[] Arith = new double[numSimulations];
		double[] Geometric = new double[numSimulations];
		double[] Sum = new double[numSimulations];
		
		// Initialize all variables
		double varA, varSum, varG, covASum, covAG, covGSum, controlVariable1, controlVariable2, beta, 
		B, B2, S, S2, sum, sum2, mult, mult2, meanArithmetic, my, sy2, tempCV2, temp1CV1, temp2CV2, u;
		
		// Initialize all variables to their initial values
		varA = varSum = varG = covASum = covAG = covGSum = meanArithmetic = tempCV2 = temp1CV1 = temp2CV2 = 0;
		B = B2 = 0; 
		S = S2 = s0;
		
		double iter = (double)d;
		
		// We generate the prices for all the simulations
		for(int j=1; j<=numSimulations;j++){	
			sum  = 0;  mult = 1;
			sum2 = 0; mult2 = 1;
			B = B2 = 0; 
			S = S2 = s0;			
			
			// We iterate through the time vector
			for (int i=1; i<=iter;i++){
				
				u = stream.nextDouble();
				// Calculate brownian motion, and underlying price
				B = B + NormalDist.inverseF01(stream.nextDouble())*Math.sqrt(time[i]-time[i-1]);
				S = s0*Math.exp((r-sigma*sigma/2.0)*(time[i])+sigma*B);
				sum = sum+S;
				mult = mult*S;
				
				// We use this in the case of antithetic variate
				if(method5 == true){
					B2=B2+Math.sqrt(time[i]-time[i-1])*NormalDist.inverseF01(1-u);
					S2=s0*Math.exp((r-sigma*sigma/2.0)*(time[i])+sigma*B2);	
					sum2=sum2+S2;
					mult2=mult2*S2;	
				}
			}
	
			// If method 1 to 4
			if(method5 == false){
				Geometric[j-1]=Math.max(0, Math.exp(-r*T)*(Math.pow(mult,1.0/iter)-K));
				Arith[j-1]=Math.max(0, Math.exp(-r*T)*(sum/iter-K));
				Sum[j-1]=sum;
			}
			
			// Method 5
			else{
				Geometric[j-1] = (Math.max(0, Math.exp(-r*T)*(Math.pow(mult,1.0/iter)-K))+Math.max(0, Math.exp(-r*T)*(Math.pow(mult2,(1.0/iter))-K)))/2.0;
				Arith[j-1] = (Math.max(0, Math.exp(-r*T)*(sum/iter-K))+Math.max(0, Math.exp(-r*T)*(sum2/iter-K)))/2.0;
				Sum[j-1] = (sum+sum2)/2.0;
			}
	
			meanArithmetic = meanArithmetic+(Arith[j-1])/(double)numSimulations;	
		}
		
		// Calculation of control variable 1
		for (int i=1; i<=iter;i++){
			temp1CV1=temp1CV1+time[i];
			temp2CV2=temp2CV2+(time[i]-time[i-1])*Math.pow((iter-i+1),2);
		}
		my = Math.log(s0)+(r-sigma*sigma*0.5)/(iter)*temp1CV1;
		sy2 = Math.pow(sigma/iter, 2)*temp2CV2;
		
		double dnorm=(-Math.log(K)+my)/Math.sqrt(sy2);
		controlVariable1 = Math.exp(-r*T)*(Math.exp(my+sy2*0.5)*NormalDist.cdf01(dnorm+Math.sqrt(sy2))-K*NormalDist.cdf01(dnorm));

		// Calculation of control variable 2
		for (int i=1; i<=iter;i++){
			tempCV2 = tempCV2+Math.exp(r*time[i]);
		}
		controlVariable2 = s0*tempCV2;
		
		for(int i=1; i<=numSimulations;i++){	
			// ALl the variance/covariance terms.
			varA = varA+Math.pow(Arith[i-1]-meanArithmetic, 2)/(double)(numSimulations-1);
			varG = varG+Math.pow(Geometric[i-1]-controlVariable1, 2)/(double)(numSimulations-1);
			varSum = varSum+Math.pow(Sum[i-1]-controlVariable2, 2)/(double)(numSimulations-1);
			covAG = covAG+(Geometric[i-1]-controlVariable1)*(Arith[i-1]-meanArithmetic)/(double)(numSimulations-1);
			covASum = covASum+(Sum[i-1]-controlVariable2)*(Arith[i-1]-meanArithmetic)/(double)(numSimulations-1);
			covGSum = covGSum+(Sum[i-1]-controlVariable2)*(Geometric[i-1]-controlVariable1)/(double)(numSimulations-1);
		}
	
		// No variance reduction technique
		for(int i=1; i<=numSimulations;i++){	
			this.Stats1.add(Arith[i-1]);
		}
					
		// Geometric control variable		
		beta = covAG/varG;
		for(int i=1; i<=numSimulations;i++){	
			Stats2.add(Arith[i-1]-beta*(Geometric[i-1]-controlVariable1));				
		}

		// Sum control variable
		beta = covASum/varSum;
		for(int i=1; i<=numSimulations;i++){	
			Stats3.add(Arith[i-1]-beta*(Sum[i-1]-controlVariable2));
		}

		// Sum of the two control variables
		double beta1 = (varSum*covAG-covGSum*covASum)/(varG*varSum-covGSum*covGSum);
		double beta2 = (varG*covASum-covGSum*covAG)/(varG*varSum-covGSum*covGSum);
		for(int i=1; i<=numSimulations;i++){	
			Stats4.add(Arith[i-1]-beta1*(Geometric[i-1]-controlVariable1)-beta2*(Sum[i-1]-controlVariable2));
		}		
		
	}
	
}
