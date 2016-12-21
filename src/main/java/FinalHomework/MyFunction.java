package FinalHomework;

import umontreal.ssj.functions.MathFunction;

public class MyFunction implements MathFunction{
	
	double a, b, c;
		 
	// Constructor method
	public MyFunction(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	// Evaluate function
	public double evaluate (double x){
		return a*Math.pow(x, 2) + b*x + c;
	}
}
