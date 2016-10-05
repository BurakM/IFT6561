package Homework2Question1;

import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.stat.Tally;

// Question 1 from homework 2

public class Main {
	public static void main(String[] args) {
		
		{
			// Using SWB Generator
			Tally statC = new Tally ("Statistics on collisions");
		    Collision col = new Collision (1000000, 100, 10000);
		    col.simulateRuns (10, new SWBgenerator(), statC);
		}
		
		{
			// Using MRG32k3a generator
		    Tally statC = new Tally ("Statistics on collisions");
		    Collision col = new Collision (1000000, 100, 10000);
		    col.simulateRuns (10, new MRG32k3a(), statC);
		}
	    
	}
}
