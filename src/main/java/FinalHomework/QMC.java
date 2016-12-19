package FinalHomework;

import umontreal.ssj.hups.BakerTransformedPointSet;
import umontreal.ssj.hups.DigitalNet;
import umontreal.ssj.hups.KorobovLattice;
import umontreal.ssj.hups.LMScrambleShift;
import umontreal.ssj.hups.PointSet;
import umontreal.ssj.hups.PointSetIterator;
import umontreal.ssj.hups.PointSetRandomization;
import umontreal.ssj.hups.RandomShift;
import umontreal.ssj.hups.Rank1Lattice;
import umontreal.ssj.hups.SobolSequence;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.stat.Tally;

public class QMC extends AsianOption {

	public QMC(double mu, double sigma, double theta, double v, double omega, 
			double K, double s0, double T, double t0, double r, int d){
		
		super (mu, sigma, theta, v, omega, K, s0, T, t0, r, d);
	}
	
	public void simulateQMC (int m, PointSet p, PointSetRandomization r, Tally statQMC, int method) {
		Tally statValue  = new Tally ("stat on value of Asian option");
		PointSetIterator stream = p.iterator ();
		for (int j = 0; j < m; j++) {
			r.randomize (p);
			stream.resetStartStream();
			// Appelle la méthode dans Asian
			simulateRuns(p.getNumPoints(), method, stream, statValue);
			statQMC.add (statValue.average());
		}
	}
	
	public void simulateQMCDerivative (int m, PointSet p, PointSetRandomization r, Tally statQMC, int method) {
		Tally statValue  = new Tally ("stat on value of Asian option");
		PointSetIterator stream = p.iterator ();
		for (int j = 0; j < m; j++) {
			r.randomize (p);
			stream.resetStartStream();
			// Appelle la méthode dans Asian
			Derivative(p.getNumPoints(), method, stream, statValue);
			statQMC.add (statValue.average());
		}
	}
	
	public Tally SobS(int m, int n, int method){
		DigitalNet p = new SobolSequence (n, 31, 2*this.d); // 2^{16} points.
        PointSetRandomization r = new RandomShift (new MRG32k3a());	 
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMC (m, p, r, statQMC, method);
        return statQMC;
	}
	
	public Tally SobLMSS(int m, int n, int method){
		DigitalNet p = new SobolSequence (n, 31, 2*this.d); // 2^{16} points.
        // Left matrix scramble followed by a random digital shift
        PointSetRandomization r = new LMScrambleShift (new MRG32k3a());
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMC (m, p, r, statQMC, method);
        return statQMC;
	}
	
	public Tally KorS(int m, int n, int method){
		KorobovLattice k = new KorobovLattice ((int)Math.pow (2, n), 5693, 2*this.d);
        PointSetRandomization r = new RandomShift (new MRG32k3a());
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMC (m, k, r, statQMC, method);
        return statQMC;
	}
	
	public Tally KorSB(int m, int n, int method){
		KorobovLattice k = new KorobovLattice ((int)Math.pow (2, n), 5693, 2*this.d);
        PointSetRandomization r = new RandomShift (new MRG32k3a());
        PointSet kb = new BakerTransformedPointSet (k);
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMC (m, kb, r, statQMC, method);
        return statQMC;
	}
	
	public Tally questionB(int m, int n, int method, int a[]){
		Rank1Lattice k = new Rank1Lattice((int)Math.pow (2, n), a, 2*d);
        PointSetRandomization r = new RandomShift (new MRG32k3a());
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMC (m, k, r, statQMC, method);
        return statQMC;
	}
	
	public Tally questionBakerB(int m, int n, int method, int a[]){
		Rank1Lattice k = new Rank1Lattice((int)Math.pow (2, n), a, 2*d);
		PointSet kb = new BakerTransformedPointSet(k);
        PointSetRandomization r = new RandomShift (new MRG32k3a());
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMC (m, kb, r, statQMC, method);
        return statQMC;
	}
	
	public Tally questionBDerivative(int m, int n, int method, int a[]){
		Rank1Lattice k = new Rank1Lattice((int)Math.pow (2, n), a, 2*d);
        PointSetRandomization r = new RandomShift (new MRG32k3a());
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMCDerivative (m, k, r, statQMC, method);
        return statQMC;
	}
	
	public Tally questionBakerBDerivative(int m, int n, int method, int a[]){
		Rank1Lattice k = new Rank1Lattice((int)Math.pow (2, n), a, 2*d);
		PointSet kb = new BakerTransformedPointSet(k);
        PointSetRandomization r = new RandomShift (new MRG32k3a());
        Tally statQMC = new Tally ("QMC averages for Asian option");
        simulateQMCDerivative (m, kb, r, statQMC, method);
        return statQMC;
	}
}
