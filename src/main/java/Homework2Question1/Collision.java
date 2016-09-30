package Homework2Question1;

import umontreal.ssj.rng.*;
import umontreal.ssj.stat.*;
public class Collision {
   int k;            // Number of locations.
   int m;            // Number of items.
   double lambda;    // Theoretical expectation of C (asymptotic).
   boolean[] used;   // Locations already used.
   
   // This is the segment of code I modified to the example
   int rootK;
   
   public Collision (int k, int rootK, int m) {
      this.rootK = rootK;
      this.m = m;
      this.k = k;
      lambda = (double) m * m / (2.0 * k);
      used = new boolean[k];
   }
   // Generates and returns the number of collisions.
   public int generateC (RandomStream stream) {
      int C = 0;
      
      // These are the variables in 3D
      int x, y, z;
      
      // Make sure no variables are ever used
      for (int i = 0; i < k; i++) used[i] = false;
      
      // Initialize random variable (skipping the first 24 realizations)
      for (int skipper = 1; skipper<25; skipper++){stream.nextInt (0, rootK-1);}
      
      for (int j = 0; j < m; j++) {
    	  
    	  // First random variable
	      x = stream.nextInt (0, rootK-1);
	      // Skip the next 19
	      for (int skipper = 1; skipper<20; skipper++){stream.nextInt (0, rootK-1);}
	      // Second random variable
	      y = stream.nextInt (0, rootK-1);
	      // Skip the next 3 
	      for (int skipper = 1; skipper<4; skipper++){stream.nextInt (0, rootK-1);}
	      z = stream.nextInt (0, rootK-1);
	      
	      // Check if location is occupied.
	      int loc = rootK*rootK*x+rootK*y+z;
	        
	      if (used[loc]) C++;
	      else used[loc] = true;
      }
      return C;
   }
   // Performs n indep. runs using stream and collects statistics in statC.
   public void simulateRuns (int n, RandomStream stream, Tally statC) {
      statC.init();
      for (int i=0; i<n; i++) statC.add (generateC (stream));
      statC.setConfidenceIntervalStudent();
      System.out.println (statC.report (0.95, 3));
      System.out.println (" Theoretical mean: " + lambda);
   }
}