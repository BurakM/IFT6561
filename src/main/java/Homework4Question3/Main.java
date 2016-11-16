package Homework4Question3;

import umontreal.ssj.probdist.BetaDist;
import umontreal.ssj.probdist.GammaDist;

public class Main {

	public static void main (String[] args) {
		
		// Parameters
		double n = 10000;
		double y = 2;
		double alpha1 = 0.025;
		double alpha2 = 0.025;
		
		// Question A
		System.out.printf("Confidence interval a): %f %f \n", BetaDist.inverseF(y, (n-y+1), alpha1), BetaDist.inverseF(y+1, (n-(y+1)+1), 1-alpha2));
		
		// Question B
		System.out.printf("Confidence interval b): %f %f \n", GammaDist.inverseF(y, n, 5, alpha1), GammaDist.inverseF(y, n, 5, 1-alpha1));
		
	}
}
