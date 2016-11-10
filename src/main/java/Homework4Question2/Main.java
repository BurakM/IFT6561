package Homework4Question2;

import umontreal.ssj.charts.ScatterChart;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.charts.ScatterChart;

public class Main {
	public static void main (String[] args) {
		RandomStream stream = new MRG32k3a();
		double[] vec = new double[2];
		int numOfRuns = 1000;
		double[][] resultsD = new double[2][numOfRuns];
		double[][] resultsE	= new double[2][numOfRuns];
		double[][] resultsG = new double[2][numOfRuns];
		RejectionMethod  rejmethod = new RejectionMethod(stream, 4, 5, 1/(2*Math.PI), 2);
		
		for(int i = 0; i<1000; i++){
			vec = rejmethod.QuestionD();
			resultsD[0][i] = vec[0];
			resultsD[1][i] = vec[1];
			vec = rejmethod.QuestionE();
			resultsE[0][i] = vec[0];
			resultsE[1][i] = vec[1];
			vec = rejmethod.QuestionG();
			resultsG[0][i] = vec[0];
			resultsG[1][i] = vec[1];
		}
		ScatterChart chartD = new ScatterChart("Question D", "X", "Y", resultsD);
		chartD.view(1200, 800);
		ScatterChart chartE = new ScatterChart("Question E", "X", "Y", resultsE);
		chartE.view(1200, 800);
		ScatterChart chartG = new ScatterChart("Question G", "X", "Y", resultsG);
		chartG.view(1200, 800);
	}

}