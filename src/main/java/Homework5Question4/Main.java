package Homework5Question4;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

public class Main {
	public static void main (String[] args) {
		// Initial constants
		double sigma = 0.2;
		double r = 0.08;
		double s0 = 100;
		double T = (double)120/365;
		int numSimulations = 10000;
		
		// Different strike for option
		double[] K = new double[4];
		K[0] = 80; K[1] = 90; K[2] = 100; K[3] = 110;
		
		// Generate vectors of t_i for 3 methods
		int d1=10; int d2=10; int d3=120;
		double[] time1, time2, time3;	
		time1= new double[d1+1]; time2= new double[d2+1]; time3= new double[d3+1];
		time1[0] = time2[0] = time3[0] = 0;
		for (int i=1; i<=d1;i++){time1[i]= (double)(110+i)/365;}
		for (int i=1; i<=d2;i++){time2[i]= (double)(12*i)/365;}
		for (int i=1; i<=d3;i++){time3[i]= (double)(i)/365;}

		// Random number stream
		RandomStream stream = new MRG32k3a();
		
		// For methods 1 to 4
		// For all strike prices
		// For all time setups
		for(int i = 0; i<4; i++){
			AsianOption option1 = new AsianOption(s0, r, sigma, T, numSimulations, stream, d1, time1, K[i], false);
			System.out.println("With time step 1, K = " + K[i] + ", MC estimation with method 1 = " + option1.Stats1.average() + ", Variance = " + option1.Stats1.variance() );
			System.out.println("With time step 1, K = " + K[i] + ", MC estimation with method 2 = " + option1.Stats2.average() + ", Variance = " + option1.Stats2.variance() + ", Reduction of:" + option1.Stats1.variance()/option1.Stats2.variance());
			System.out.println("With time step 1, K = " + K[i] + ", MC estimation with method 3 = " + option1.Stats3.average() + ", Variance = " + option1.Stats3.variance() + ", Reduction of:" + option1.Stats1.variance()/option1.Stats3.variance());
			System.out.println("With time step 1, K = " + K[i] + ", MC estimation with method 4 = " + option1.Stats4.average() + ", Variance = " + option1.Stats4.variance() + ", Reduction of:" + option1.Stats1.variance()/option1.Stats4.variance());
			AsianOption option2 = new AsianOption(s0, r, sigma, T, numSimulations, stream, d2, time2, K[i], false);
			System.out.println("With time step 2, K = " + K[i] + ", MC estimation with method 1 = " + option2.Stats1.average() + ", Variance = " + option2.Stats1.variance() );
			System.out.println("With time step 2, K = " + K[i] + ", MC estimation with method 2 = " + option2.Stats2.average() + ", Variance = " + option2.Stats2.variance() + ", Reduction of:" + option2.Stats1.variance()/option2.Stats2.variance());
			System.out.println("With time step 2, K = " + K[i] + ", MC estimation with method 3 = " + option2.Stats3.average() + ", Variance = " + option2.Stats3.variance() + ", Reduction of:" + option2.Stats1.variance()/option2.Stats3.variance());
			System.out.println("With time step 2, K = " + K[i] + ", MC estimation with method 4 = " + option2.Stats4.average() + ", Variance = " + option2.Stats4.variance() + ", Reduction of:" + option2.Stats1.variance()/option2.Stats4.variance());
			AsianOption option3 = new AsianOption(s0, r, sigma, T, numSimulations, stream, d3, time3, K[i], false);
			System.out.println("With time step 3, K = " + K[i] + ", MC estimation with method 1 = " + option3.Stats1.average() + ", Variance = " + option3.Stats1.variance() );
			System.out.println("With time step 3, K = " + K[i] + ", MC estimation with method 2 = " + option3.Stats2.average() + ", Variance = " + option3.Stats2.variance() + ", Reduction of:" + option3.Stats1.variance()/option3.Stats2.variance());
			System.out.println("With time step 3, K = " + K[i] + ", MC estimation with method 3 = " + option3.Stats3.average() + ", Variance = " + option3.Stats3.variance() + ", Reduction of:" + option3.Stats1.variance()/option3.Stats3.variance());
			System.out.println("With time step 3, K = " + K[i] + ", MC estimation with method 4 = " + option3.Stats4.average() + ", Variance = " + option3.Stats4.variance() + ", Reduction of:" + option3.Stats1.variance()/option3.Stats4.variance());
		}
		
		// For method 5
		// For all strike prices
		// For all time setups
		for(int i = 0; i<4; i++){
			AsianOption option1_base = new AsianOption(s0, r, sigma, T, numSimulations, stream, d1, time1, K[i], false);
			AsianOption option1 = new AsianOption(s0, r, sigma, T, numSimulations, stream, d1, time1, K[i], true);
			System.out.println("With time step 1, K = " + K[i] + ", MC estimation with method 5 = " + option1.Stats4.average() + ", Variance = " + option1.Stats4.variance() + ", Reduction of:" + option1_base.Stats1.variance()/option1.Stats4.variance());
			AsianOption option2_base = new AsianOption(s0, r, sigma, T, numSimulations, stream, d1, time1, K[i], false);
			AsianOption option2 = new AsianOption(s0, r, sigma, T, numSimulations, stream, d2, time2, K[i], true);
			System.out.println("With time step 2, K = " + K[i] + ", MC estimation with method 5 = " + option2.Stats4.average() + ", Variance = " + option2.Stats4.variance() + ", Reduction of:" + option2_base.Stats1.variance()/option2.Stats4.variance());
			AsianOption option3_base = new AsianOption(s0, r, sigma, T, numSimulations, stream, d1, time1, K[i], false);
			AsianOption option3 = new AsianOption(s0, r, sigma, T, numSimulations, stream, d3, time3, K[i], true);
			System.out.println("With time step 3, K = " + K[i] + ", MC estimation with method 5 = " + option3.Stats4.average() + ", Variance = " + option3.Stats4.variance() + ", Reduction of:" + option3_base.Stats1.variance()/option3.Stats4.variance());
		}
	}
}
