package FinalHomework;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;

public class Main {
	public static void main (String[] args) {
			
		// Characteristics of the asian option
		double K = 101;
		double s0 = 100;
		double T = 1;
		double t0 = 0;
		double r = 0.1;
		int d = 16;
				
		// Variance Gamma process parameters
		double v = 0.3; 
		double theta = -0.1436;
		double sigma = 0.12136;
		double mu = 1;
		double omega = 1/v*Math.log(1-theta*v-sigma*sigma*v/2);
		
		
		QMC AsianQMC = new QMC(mu, sigma, theta, v, omega,	K, s0, T, t0, r, d);
		int n = 14;
		RandomStream stream = new MRG32k3a();
		
		
		// Question a)
		
		
		/*
		for (int j = 1; j<4; j++){
			System.out.println("Method " + j);
				
			Tally statMC = AsianQMC.MonteCarlo((int)Math.pow(2, n), j, stream);
				
			// Sob + S
			Tally statQMCSobS = AsianQMC.SobS(32, n, j);
			System.out.println("Variance reduction, Sob + S:");
			System.out.println(statMC.variance()/statQMCSobS.variance()/((int)Math.pow(2, n)));
				
			// Sob + LMS + S
			Tally statQMCSobLMSS = AsianQMC.SobLMSS(32, n, j);
			System.out.println("Variance reduction, Sob + LMS + S:");
			System.out.println(statMC.variance()/statQMCSobLMSS.variance()/((int)Math.pow(2, n)));
		
			// Kor + S
			Tally statQMCKorS = AsianQMC.KorS(32, n, j);
			System.out.println("Variance reduction, Kor + S:");
			System.out.println(statMC.variance()/statQMCKorS.variance()/((int)Math.pow(2, n)));
				
			// Kor + S + B
			Tally statQMCKorSB = AsianQMC.KorSB(32, n, j);
			System.out.println("Variance reduction, Kor + S + B:");
			System.out.println(statMC.variance()/statQMCKorSB.variance()/((int)Math.pow(2, n)));
		}
		*/
		
		
		
		// Question b)
		
		// All gamma = 0.1
		int a1[] = {1, 6229, 2691, 1399, 7751, 2865, 3221, 379, 
				2211, 1593, 4075, 2911, 3051, 7907, 2063, 2753, 
				5017, 5245, 7015, 5735, 6687, 2479, 3451, 5301, 
				2095, 5207, 4357, 4651, 5859, 2299, 3151, 2955};
		
		// gamma = 1/(1+j)
		int a2[] = {1, 6229, 2691, 4415, 1507, 6655, 5877, 2887, 
				3647, 4789, 6315, 2089, 3473, 7985, 4009, 8087, 
				6899, 1395, 3203, 5539, 5485, 3127, 2641, 6129, 
				5219, 2363, 7781, 31, 903, 145, 2807, 1955};
		
		/*
		for (int j = 1; j<4; j++){
			System.out.println("Method " + j);
				
			Tally statMC = AsianQMC.MonteCarlo((int)Math.pow(2, n), j, stream);
				
			// Lattice a1 without Baker
			Tally statQMCa1 = AsianQMC.questionB(32, n, j, a1);
			System.out.println("Variance reduction, lattice a1 without Baker:");
			System.out.println(statMC.variance()/statQMCa1.variance()/((int)Math.pow(2, n)));
				
			// Lattice a1 with Baker
			Tally statQMCa1Baker = AsianQMC.questionBakerB(32, n, j, a1);
			System.out.println("Variance reduction, lattice a1 with Baker:");
			System.out.println(statMC.variance()/statQMCa1Baker.variance()/((int)Math.pow(2, n)));
			
			// Lattice a2 without Baker
			Tally statQMCa2 = AsianQMC.questionB(32, n, j, a2);
			System.out.println("Variance reduction, lattice a2 without Baker:");
			System.out.println(statMC.variance()/statQMCa2.variance()/((int)Math.pow(2, n)));
				
			// Lattice a2 with Baker
			Tally statQMCa2Baker = AsianQMC.questionBakerB(32, n, j, a2);
			System.out.println("Variance reduction, lattice a2 with Baker:");
			System.out.println(statMC.variance()/statQMCa2Baker.variance()/((int)Math.pow(2, n)));
		}
		*/
		
		
		for (int j = 3; j<4; j++){
			System.out.println("Method " + j);
				
			Tally statMC = AsianQMC.MonteCarloDerivative((int)Math.pow(2, n), j, stream);
				
			// Lattice a1 without Baker - derivative
			Tally statQMCa1 = AsianQMC.questionBDerivative(32, n, j, a1);
			System.out.println("Variance reduction, lattice a1 without Baker, for derivative:");
			System.out.println(statMC.variance()/statQMCa1.variance()/((int)Math.pow(2, n)));
				
			// Lattice a1 with Baker - derivative
			Tally statQMCa1Baker = AsianQMC.questionBakerBDerivative(32, n, j, a1);
			System.out.println("Variance reduction, lattice a1 with Baker, for derivative:");
			System.out.println(statMC.variance()/statQMCa1Baker.variance()/((int)Math.pow(2, n)));
			
			// Lattice a2 without Baker - derivative
			Tally statQMCa2 = AsianQMC.questionBDerivative(32, n, j, a2);
			System.out.println("Variance reduction, lattice a2 without Baker, for derivative:");
			System.out.println(statMC.variance()/statQMCa2.variance()/((int)Math.pow(2, n)));
				
			// Lattice a2 with Baker - derivative
			Tally statQMCa2Baker = AsianQMC.questionBakerBDerivative(32, n, j, a2);
			System.out.println("Variance reduction, lattice a2 with Baker, for derivative:");
			System.out.println(statMC.variance()/statQMCa2Baker.variance()/((int)Math.pow(2, n)));
		}
	}
}
