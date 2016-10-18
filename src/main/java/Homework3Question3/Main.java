package Homework3Question3;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

class Main {
	public static void main (String[] args) {
		RandomStream stream = new MRG32k3a();
		Copula copula09 = new Copula(0.9, stream);
		Copula copula08 = new Copula(0.8, stream);
		
		int totalruns = 1000000;
		int count09NORTA = 0;
		int count08NORTA = 0;
		int count09Frechet = 0;
		int count08Frechet = 0;
		for (int i = 0; i < totalruns; i++){
			copula09.NORTA();
			if(copula09.u1 + copula09.u2 < 0.1){
				count09NORTA++;
			}
			copula08.NORTA();
			if(copula08.u1 - copula08.u2 < 0.05){
				count08NORTA++;
			}
			copula09.Frechet();
			if(copula09.u1 + copula09.u2 < 0.1){
				count09Frechet++;
			}
			copula08.Frechet();
			if(copula08.u1 - copula08.u2 < 0.05){
				count08Frechet++;
			}
		}
		
		System.out.println ("NORTA with rho_s   = 0.9: " + (double) count09NORTA/totalruns);
		System.out.println ("NORTA with rho_s   = 0.8: " + (double) count08NORTA/totalruns);
		System.out.println ("Frechet with rho_s = 0.9: " + (double) count09Frechet/totalruns);
		System.out.println ("Frechet with rho_s = 0.8: " + (double) count08Frechet/totalruns);
	}
}
