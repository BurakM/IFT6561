package Homework4Question2;

import umontreal.ssj.charts.ScatterChart;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.charts.ScatterChart;

public class Main {
	public static void main (String[] args) {
		int numOfRuns = 1000;
		// Generator
		RandomStream stream = new MRG32k3a();
		
		// Results storage
		double[] vec = new double[2];
		double[][] resultsD = new double[2][numOfRuns];
		double[][] resultsE	= new double[2][numOfRuns];
		double[][] resultsG = new double[2][numOfRuns];
		
		// Instance of class
		RejectionMethod  rejmethod = new RejectionMethod(stream, 4, 5, 1/(2*Math.PI), 2);
		
		// Count number of runs needed
		double sumD = 0;
		double sumE = 0;
		double sumG = 0;
		
		for(int i = 0; i<numOfRuns; i++){
			// Question D
			vec = rejmethod.QuestionD();
			resultsD[0][i] = vec[0];
			resultsD[1][i] = vec[1];
			sumD = sumD + vec[2];
			
			// Question E
			vec = rejmethod.QuestionE();
			resultsE[0][i] = vec[0];
			resultsE[1][i] = vec[1];
			sumE = sumE + vec[2];
			
			// Question G
			vec = rejmethod.QuestionG();
			resultsG[0][i] = vec[0];
			resultsG[1][i] = vec[1];
			sumG = sumG + vec[2];
		}
		
		// Charting and tallies
		ScatterChart chartD = new ScatterChart("Question D", "X", "Y", resultsD);
		chartD.view(1200, 800);
		System.out.printf("Question d: %f\n", numOfRuns/sumD);
		ScatterChart chartE = new ScatterChart("Question E", "X", "Y", resultsE);
		chartE.view(1200, 800);
		System.out.printf("Question e: %f\n", numOfRuns/sumE);
		ScatterChart chartG = new ScatterChart("Question G", "X", "Y", resultsG);
		chartG.view(1200, 800);
		System.out.printf("Question g: %f\n", numOfRuns/sumG);
	}

}
