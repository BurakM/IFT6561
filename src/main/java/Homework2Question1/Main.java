package Homework2Question1;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.stat.Tally;

public class Main {
	public static void main(String[] args) {
		
		{
			Tally statC = new Tally ("Statistics on collisions");
		    Collision col = new Collision (1000000, 100, 10000);
		    col.simulateRuns (10, new SWBgenerator(), statC);
		}
		
		{
		    Tally statC = new Tally ("Statistics on collisions");
		    Collision col = new Collision (1000000, 100, 10000);
		    col.simulateRuns (10, new MRG32k3a(), statC);
		}
	    
	}
}
