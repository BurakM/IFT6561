package Homework3Question4;

public class LambertW {
	double x;
	
	LambertW(double x){
		this.x = x;
	}
	
	public double Compute(){
		
		double eps = 0.4586887;
		double sol = (1+eps)*Math.log(6*x/(5*Math.log((12/5)*(x/Math.log(1+(12/5)*x)))))-eps*Math.log(2*x/(Math.log(1+2*x)));
		
		System.out.println(this.x);
		System.out.println(sol);
		return sol;
	}
	

}
