package Homework3Question4;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

public class Main {
	public static void main (String[] args) {
		GompertzMakehamDist dist = new GompertzMakehamDist( 0.000075858, Math.log(1.09144),  0.0005);
		
		System.out.println(dist.cdf(dist.inverseF(0.97)));
		
		RandomStream stream = new MRG32k3a();
		
		double sumW1 = 0;
		double sumW2 = 0;
		int totalrun = 10000;
		double X;
		double a = 1;
		double r = 0.03;
		double b = 35;
		double c = dist.cdf(45);
		
		for (int i = 0; i<1000; i++){
			//X = dist.inverseF(c+(1-c)*stream.nextDouble());

			//sumW1 = (-a/r)*Math.exp(-r*(X-45))+(a/r)*Math.exp(-20*r);
			//sumW2 = (b)*Math.exp(-r*(X-45));		
		}
		
		//System.out.println(sumW1/totalruns);
		//System.out.println(sumW2/totalruns);
	}
}
