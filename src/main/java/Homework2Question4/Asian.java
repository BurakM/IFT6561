package Homework2Question4;

import umontreal.ssj.rng.*;
import umontreal.ssj.probdist.NormalDist;

// Class where characteristics/methods of asian contained.
// Code based off ssj's example code.
// Some methods modified.

public class Asian {
   double strike;    // Strike price.
   int s;            // Number of observation times.
   double s0;        // Initial value
   double discount;  // Discount factor exp(-r * zeta[t]).
   double[] muDelta; // Differences * (r - sigma^2/2).
   double[] sigmaSqrtDelta; // Square roots of differences * sigma.
   double[] logS;    // Log of the GBM process: logS[t] = log (S[t]).
   // Array zeta[0..s] must contain zeta[0]=0.0, plus the s observation times.
   
   public Asian (double r, double sigma, double strike,
                 double s0, int s, double[] zeta) {
      this.strike = strike;
      this.s = s;
      this.s0 = s0;
      discount = Math.exp (-r * zeta[s]);
      double mu = r - 0.5 * sigma * sigma;
      muDelta = new double[s];
      sigmaSqrtDelta = new double[s];
      logS = new double[s+1];
      double delta;
      for (int j = 0; j < s; j++) {
         delta = zeta[j+1] - zeta[j];
         muDelta[j] = mu * delta;
         sigmaSqrtDelta[j] = sigma * Math.sqrt (delta);
      }
      logS[0] = Math.log (s0);
   }
   // Generates the process S.
   public void generatePath (RandomStream stream) {
       for (int j = 0; j < s; j++)
          logS[j+1] = logS[j] + muDelta[j] + sigmaSqrtDelta[j]
                   * NormalDist.inverseF01 (stream.nextDouble());
   }
   // Computes and returns the discounted option payoff.
   public double getPayoff () {
       double average = 0.0;  // Average of the GBM process.
       for (int j = 1; j <= s; j++) average += Math.exp (logS[j]);
       average /= s;
       if (average > strike) return discount * (average - strike);
       else return 0.0;
   }
   
   // Modified payoff function used to calculate stochastic derivative
   public double getPayoffSto () {
	   double average = 0.0;  // Average of the GBM process.
	   for (int j = 1; j <= s; j++) average += Math.exp (logS[j]);
	   average /= s;
	   if (average > strike) return discount * (average/s0);
	   else return 0.0;
   }
    
   // Performs 1 run using stream and collects statistics and output .
   public double simulateRun (int n, RandomStream stream) {
	   generatePath (stream);
	   return getPayoff();
   }

}