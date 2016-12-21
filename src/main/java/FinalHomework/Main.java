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
		int a1[] = {1, 19463, 17213, 5895, 14865, 31925, 30921, 
				26671, 1607, 32473, 27933, 25385, 4363, 27011, 
				29035, 16205, 6221, 1115, 18943, 31313, 13799, 
				17899, 6459, 5551, 22361, 31751, 27107, 3485, 
				5281, 31151, 9189, 23493};
		
		// gamma = 1/(1+j)
		int a2[] = {1, 19463, 8279, 31243, 6281, 26417, 28747, 
				10089, 8703, 14147, 10481, 29905, 1371, 23299, 
				26523, 7633, 10011, 11559, 17787, 4237, 19047, 
				9425, 16707, 26967, 9301, 15489, 25959, 4345, 
				14105, 25341, 22523, 5787};
		
		
		
		/*
		n = 16;
		
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
		
		/*
		for (int j = 3; j<4; j++){
			System.out.println("Method " + j);
				
			Tally statMC = AsianQMC.MonteCarloDerivative((int)Math.pow(2, n), j, stream);
			System.out.println("Basic Monte-Carlo, for derivative:");
			System.out.println(statMC.report());
			
			// Lattice a1 without Baker - derivative
			Tally statQMCa1 = AsianQMC.questionBDerivative(32, n, j, a1);
			System.out.println("Lattice a1 without Baker, for derivative:");
			System.out.println(statQMCa1.report());
				
			// Lattice a1 with Baker - derivative
			Tally statQMCa1Baker = AsianQMC.questionBakerBDerivative(32, n, j, a1);
			System.out.println("Lattice a1 with Baker, for derivative:");
			System.out.println(statQMCa1Baker.report());
			
			// Lattice a2 without Baker - derivative
			Tally statQMCa2 = AsianQMC.questionBDerivative(32, n, j, a2);
			System.out.println("Lattice a2 without Baker, for derivative:");
			System.out.println(statQMCa2.report());
				
			// Lattice a2 with Baker - derivative
			Tally statQMCa2Baker = AsianQMC.questionBakerBDerivative(32, n, j, a2);
			System.out.println("Lattice a2 with Baker, for derivative:");
			System.out.println(statQMCa2Baker.report());
		}
		*/
		
		
		
		// Question e)
		QMC AsianToFindRoot140 = new QMC(mu, sigma, theta, v, omega, 140, s0, 1, 0, r, 1);
		QMC AsianToFindRoot180 = new QMC(mu, sigma, theta, v, omega, 180, s0, 1, 0, r, 1);
		
		
		
		System.out.println("Root for K=140: " + AsianToFindRoot140.Root());
		System.out.println("Root for K=180: " + AsianToFindRoot180.Root());
		
		Tally noIS140 = AsianToFindRoot140.MonteCarlo((int)Math.pow(2, 14), 3, stream);
		Tally IS140 = AsianToFindRoot140.MonteCarloIS((int)Math.pow(2, 14), stream, AsianToFindRoot140.Root());
		System.out.println("Strike = " + 140);
		System.out.println(noIS140.report());
		System.out.println(IS140.report());
		
		Tally noIS180 = AsianToFindRoot180.MonteCarlo((int)Math.pow(2, 14), 3, stream);
		Tally IS180 = AsianToFindRoot180.MonteCarloIS((int)Math.pow(2, 14), stream, AsianToFindRoot180.Root());
		System.out.println("Strike = " + 180);
		System.out.println(noIS180.report());
		System.out.println(IS180.report());
		
		
		
		// Question g)
		double omega140=AsianToFindRoot140.Root()*1.01;
		double omega180=AsianToFindRoot180.Root()*1.01;
		
		System.out.println("Using omega for K=140: " + omega140);
		System.out.println("Using omega for K=180: " + omega180);
				
				
		Tally trunc140 = AsianToFindRoot140.MonteCarloTruncated((int)Math.pow(2, 14), stream, omega140);
		System.out.println("Strike = " + 140);
		System.out.println(trunc140.report());
				
		Tally trunc180 = AsianToFindRoot180.MonteCarloTruncated((int)Math.pow(2, 14), stream, omega180);
		System.out.println("Strike = " + 180);
		System.out.println(trunc180.report());
		
		
		// Question h)
		
		n = 14;
		// All gamma = 0.1
		int[] lattice1 = {1, 6229};
		
		omega140=AsianToFindRoot140.Root();
		omega180=AsianToFindRoot180.Root();
		
		System.out.println("Using omega for K=140: " + omega140);
		System.out.println("Using omega for K=180: " + omega180);
		
		System.out.println("Strike = " + 140);
		
		// SOB + LMS + S
		Tally statHsob140 = AsianToFindRoot140.SobLMSSTruncated(32, n, omega140);
		System.out.println("SOB + LMS + S and conditional MC");
		statHsob140.	setConfidenceIntervalStudent();
		System.out.println(statHsob140.report());
		System.out.println("Variance reduction:");
		System.out.println(IS140.variance()/statHsob140.variance()/((int)Math.pow(2, n)));

		
		// Lattice without Baker
		Tally statH140lattice1 = AsianToFindRoot140.questionBTruncated(32, n, lattice1, omega140);
		System.out.println("Lattice 1 without Baker and conditional MC:");
		statH140lattice1.	setConfidenceIntervalStudent();
		System.out.println(statH140lattice1.report());
		System.out.println("Variance reduction:");
		System.out.println(IS140.variance()/statH140lattice1.variance()/((int)Math.pow(2, n)));
						
		// Lattice without Baker
		Tally statH140lattice1Baker = AsianToFindRoot140.questionBakerBTruncated(32, n, lattice1, omega140);
		System.out.println("Lattice 1 with Baker and conditional MC:");
		statH140lattice1Baker.	setConfidenceIntervalStudent();
		System.out.println(statH140lattice1Baker.report());
		System.out.println("Variance reduction:");
		System.out.println(IS140.variance()/statH140lattice1Baker.variance()/((int)Math.pow(2, n)));
		
		System.out.println("Strike = " + 180);
		
		// SOB + LMS + S
		Tally statHsob180 = AsianToFindRoot180.SobLMSSTruncated(32, n, omega140);
		System.out.println("SOB + LMS + S and conditional MC");
		statHsob180.	setConfidenceIntervalStudent();
		System.out.println(statHsob180.report());
		System.out.println("Variance reduction:");
		System.out.println(IS180.variance()/statHsob180.variance()/((int)Math.pow(2, n)));
		
		// Lattice without Baker
		Tally statH180lattice1 = AsianToFindRoot180.questionBTruncated(32, n, lattice1, omega140);
		System.out.println("Lattice 1 without Baker and conditional MC:");
		statH180lattice1.	setConfidenceIntervalStudent();
		System.out.println(statH180lattice1.report());
		System.out.println("Variance reduction:");
		System.out.println(IS180.variance()/statH180lattice1.variance()/((int)Math.pow(2, n)));
						
		// Lattice without Baker
		Tally statH180lattice1Baker = AsianToFindRoot180.questionBakerBTruncated(32, n, lattice1, omega140);
		System.out.println("Lattice 1 with Baker and conditional MC:");
		statH180lattice1Baker.	setConfidenceIntervalStudent();
		System.out.println(statH180lattice1Baker.report());
		System.out.println("Variance reduction:");
		System.out.println(IS180.variance()/statH180lattice1Baker.variance()/((int)Math.pow(2, n)));
		  
		
		 
	}
	
	
}
