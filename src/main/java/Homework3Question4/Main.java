package Homework3Question4;

public class Main {
	public static void main (String[] args) {
		GompertzMakehamDist test = new GompertzMakehamDist( 0.000075858, Math.log(1.09144),  0.0005);
		//test.cdf(x)
		//test.density(x)
		System.out.print(test.inverseF(0.5));
	}
}
