package Homework3Question4;

public class LambertW {
	double x;
	
	LambertW(double x){
		this.x = x;
	}
	
	public double Compute(int n){
		double sum = 0;
		for(int i = 1; i < n; i++){
			sum = sum + (Math.pow(-i, i-1)*Math.pow(x, i))/(factorial(i));
		}
		return 0;
	}
	
	public int factorial(int n) {
		// This method was found on stackoverflow
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
