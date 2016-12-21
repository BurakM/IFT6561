package FinalHomework;

import umontreal.ssj.probdist.BetaDist;
import umontreal.ssj.probdist.GammaDist;
import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.rng.RandomStream;
import umontreal.ssj.stat.Tally;
import umontreal.ssj.util.RootFinder;

public class AsianOption {

	double mu, sigma, theta, v, deltaTau, u, deltaTime, K, s0, T, t0, r, omega;
	double [] pathGlobal;
	int d;
	
	// Constructor method
	public AsianOption(double mu, double sigma, double theta, double v, double omega, 
			double K, double s0, double T, double t0, double r, int d){
		this.mu = mu;
		this.sigma = sigma;
		this.theta = theta;
		this.v = v;
		this.K = K;
		this.s0 = s0;
		this.T = T;
		this.t0 = t0;
		this.r = r;
		this.d = d;
		this.omega = 1/v*Math.log(1-theta*v-sigma*sigma*v/2);
	}
	
	// Method 1
	public void Method1(int d, double T, RandomStream stream){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		
		// Initial values
		tau[0] = 0;
		path[0] = 0;
		
		// Gamma process
		for (int i=1; i<=d;i++){
			tau[i]= tau[i-1]+GammaProcess(T/d, stream.nextDouble(),this.mu,this.v,0);
		}
		
		// Brownian motion
		for (int i=1; i<=d;i++){			
			path[i]=path[i-1]+BrownianMotion(stream.nextDouble(),(this.theta)*(tau[i]-tau[i-1]), (this.sigma)*(this.sigma)*(tau[i]-tau[i-1]));
		}
		this.pathGlobal = path;
	}
	
	// Method 2
	public void Method2(int d, double T, RandomStream stream){
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		
		// Initial values
		tau[0] = 0;
		path[0] = 0;
		
		// Calculate at time t=T
		tau[d] = tau[0]+GammaProcess(T, stream.nextDouble(),mu,v, 0);	
		path[d]= path[0]+BrownianMotion(stream.nextDouble(),theta*(tau[d]-tau[0]), sigma*sigma*(tau[d]-tau[0]));
		
		// Used to construct the bridge
		int index = d/2;
		int i = 1;
		int temp;
		double mean, variance;
		int tauPlus, tauMin, tauStar;
		
		while(index>=1){
			i = 1;
			while(i*index<d){
				// Indexes
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				// Calculate the gamma process. Uses beta distribution, since we are using condition distribution
				tau[tauStar]=tau[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu,v)*(tau[tauPlus]-tau[tauMin]);
				
				// Mean and variance of brownian motion
				mean=path[tauMin]+(tau[tauStar]-tau[tauMin])/(tau[tauPlus]-tau[tauMin])*(path[tauPlus]-path[tauMin]);
				variance=(tau[tauStar]-tau[tauMin])*(tau[tauPlus]-tau[tauStar])/(tau[tauPlus]-tau[tauMin])*sigma*sigma;
				
				// Brownian motion
				path[tauStar]=BrownianMotion(stream.nextDouble(), mean, variance);	
				i=i+2;		
			}
			temp=index/2;
			index=temp;
		}
		this.pathGlobal = path;
	}
	
	// Method 3
	public void Method3(int d, double T, RandomStream stream){
		
		// Required constants
		double mu_plus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)+this.theta)/2;
		double mu_minus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)-this.theta)/2;
		double v_plus=mu_plus*mu_plus*this.v;
		double v_minus=mu_minus*mu_minus*this.v;
		
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		double[] tau_plus = new double[d+1]; 
		double[] tau_minus = new double[d+1];
		
		// Initial Values
		tau_plus[0]=0; tau_minus[0]=0; tau[0]=0;
		
		// Values at time t=T
		tau_plus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_plus,v_plus, 0);	
		tau_minus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_minus,v_minus, 0);
		int tauPlus, tauMin, tauStar;
		
		// Used to construct the bridge
		int index = d/2;
		int i = 1;
		int temp;
		while(index>=1){
			i = 1;
			while(i*index<d){
				// Indexes
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				// Tau plus and tau minus
				tau_plus[tauStar] = tau_plus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_plus,v_plus)*(tau_plus[tauPlus]-tau_plus[tauMin]);
				tau_minus[tauStar] = tau_minus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_minus,v_minus)*(tau_minus[tauPlus]-tau_minus[tauMin]);							
				
				i=i+2;	
			}
			temp=index/2;
			index=temp;
		}
		for (int j = 1; j<=d; j++){
			// Calculate variance gamma from tau+ and tau-
			path[j] = tau_plus[j] - tau_minus[j];
		}
		this.pathGlobal = path;
	}
	
	// Brownian motion method
	public double BrownianMotion(double u, double mean, double variance){
		return mean+Math.sqrt(variance)*NormalDist.inverseF01(u);
	}
	
	// Gamma process method
	public double GammaProcess(double deltaTime, double u, double mu, double v, double thetaIS){
		return GammaDist.inverseF(deltaTime/v*mu*mu, mu/v + thetaIS, 7, u);
	}
	
	// Beta distribution method
	// Take notice of the truncature 
	public double Beta(double t, double ta, double tb, double u,double mu, double v){
		return BetaDist.inverseF((t-ta)*mu*mu/v, (tb-t)*mu*mu/v, 0.0001+0.99965*u);
	}
	
	// Calculate the payoff of the asian option
	public double GetPayoff(){

		double sum = 0;

		for (int i=1; i<=d; i++){
			sum=sum+this.s0*Math.exp(this.r*(i*this.T/this.d)+this.pathGlobal[i]+this.omega*(i*this.T/this.d));	
		}
		
		return (Math.max(0, Math.exp(-this.r*this.T)*((double)1/this.d*sum-this.K)));
	}
	
	// Performs n indep. runs using stream and collects statistics in statValue.
	public void simulateRuns (int n, int method, RandomStream stream, Tally statValue) {
		statValue.init();
		for (int i=0; i<n; i++) {
			// Which method to use
			if (method == 1) {Method1(this.d, this.T, stream);}
			else if (method == 2) {Method2(this.d, this.T, stream);}
			else if (method == 3) {Method3(this.d, this.T, stream);}
			statValue.add (GetPayoff ());
			stream.resetNextSubstream();
		}
	}

	// Accumulates runs
	public Tally MonteCarlo(int n, int method, RandomStream stream){
		Tally statValue  = new Tally ("MC averages for Asian option");
		simulateRuns(n, method, stream, statValue);
		return statValue;
	}
	
	// Like simulate runs, but for the derivative
	public void Derivative (int n, int method, RandomStream stream, Tally statValue) {
		
		// This method switches between correct v and incorrect v
		double correctV = this.v;
		double delta = 0.001;
		double temp1, temp2;
		
		statValue.init();
		for (int i=0; i<n; i++) {
			// Using correct V
			this.v = correctV;
			if (method == 1) {Method1(this.d, this.T, stream);}
			else if (method == 2) {Method2(this.d, this.T, stream);}
			else if (method == 3) {Method3(this.d, this.T, stream);}
			temp1 = GetPayoff ();
			
			// Reset start of the substream
			stream.resetStartSubstream();
			
			// Using changed V
			this.v = correctV + delta;
			if (method == 1) {Method1(this.d, this.T, stream);}
			else if (method == 2) {Method2(this.d, this.T, stream);}
			else if (method == 3) {Method3(this.d, this.T, stream);}
			temp2 = GetPayoff ();
			
			// The derivative
			statValue.add ((temp2-temp1)/delta);
			stream.resetNextSubstream();
		}
		this.v = correctV;
	}
	
	// Runs Monte Carlo simulation for the derivative
	public Tally MonteCarloDerivative(int n, int method, RandomStream stream){
		Tally statValue  = new Tally ("MC averages for Asian option derivative");
		Derivative(n, method, stream, statValue);
		return statValue;
	}
	
	// Find the root of the quadratic equation
	public double Root(){
		
		// Required constants
		double mu_plus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)+this.theta)/2;
		double mu_minus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)-this.theta)/2;
		double v_plus=mu_plus*mu_plus*this.v;
		double v_minus=mu_minus*mu_minus*this.v;
		double lambda_plus=mu_plus/v_plus;
		double lambda_minus=mu_minus/v_minus;
		double a_plus=mu_plus*mu_plus/v_plus;
		double a_minus=mu_minus*mu_minus/v_minus;
		
		double c = Math.log(this.K/this.s0)-this.r-this.omega;
		
		// Coefficients
		double A = c;
        double B = a_plus + a_minus - c*lambda_plus + c*lambda_minus;
        double C = a_plus*lambda_minus - a_minus*lambda_plus - c*lambda_plus*lambda_minus;
		
        MyFunction myFunc = new MyFunction(A, B, C);
        double solution = RootFinder.brentDekker(0, lambda_plus, myFunc, 0.00001);
		return solution;
	}	
	
	// Simulation runs for importance sampling
	public void simulateRunsIS (int n, RandomStream stream, Tally statValue, double thetaIS) {
		statValue.init();
		for (int i=0; i<n; i++) {
			statValue.add (Method3IS(this.d, this.T, stream, thetaIS));
			stream.resetNextSubstream();
		}
	}

	// Monte Carlo simulation for importance sampling
	public Tally MonteCarloIS(int n, RandomStream stream, double thetaIS){
		Tally statValue  = new Tally ("MC IS averages for Asian option");
		simulateRunsIS(n, stream, statValue, thetaIS);
		return statValue;
	}
	
	// Simulation runs for conditional Monte Carlo
	public void simulateRunsTruncated (int n, RandomStream stream, Tally statValue, double thetaIS) {
		statValue.init();
		for (int i=0; i<n; i++) {
			statValue.add (Method3Truncated(this.d, this.T, stream, thetaIS));
			stream.resetNextSubstream();
		}
	}
	
	// Monte Carlo conditional
	public Tally MonteCarloTruncated(int n, RandomStream stream, double thetaIS){
		Tally statValue  = new Tally ("MC IS and truncated averages for Asian option");
		simulateRunsTruncated(n, stream, statValue, thetaIS);
		return statValue;
	}
	
	// Method 3 but for importance sampling
	// includes exponential twisting
	public double Method3IS(int d, double T, RandomStream stream, double thetaIS){
		double mu_plus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)+this.theta)/2;
		double mu_minus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)-this.theta)/2;
		double v_plus=mu_plus*mu_plus*this.v;
		double v_minus=mu_minus*mu_minus*this.v;
		
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		double[] tau_plus = new double[d+1]; 
		double[] tau_minus = new double[d+1];
		
		// Initial Values
		tau_plus[0]=0; tau_minus[0]=0; tau[0]=0;
		
		// Values at time t=T
		tau_plus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_plus,v_plus, -thetaIS);	
		tau_minus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_minus,v_minus, thetaIS);
		int tauPlus, tauMin, tauStar;
		
		// Used to construct the bridge
		int index = d/2;
		int i = 1;
		int temp;
		while(index>=1){
			i = 1;
			while(i*index<d){
				// Indexes
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				// Tau plus and tau minus
				tau_plus[tauStar] = tau_plus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_plus,v_plus)*(tau_plus[tauPlus]-tau_plus[tauMin]);
				tau_minus[tauStar] = tau_minus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_minus,v_minus)*(tau_minus[tauPlus]-tau_minus[tauMin]);							
				
				i=i+2;	
			}
			temp=index/2;
			index=temp;
		}
		for (int j = 1; j<=d; j++){
			// Calculate variance gamma from tau+ and tau-
			path[j] = tau_plus[j] - tau_minus[j];
		}
		this.pathGlobal = path;
		
		
		double lambda_plus=mu_plus/v_plus;
		double lambda_minus=mu_minus/v_minus;
		double a_plus=mu_plus*mu_plus/v_plus;
		double a_minus=mu_minus*mu_minus/v_minus;
		
		// Likelihood
		double L = Math.pow(lambda_plus/(lambda_plus-thetaIS),a_plus)*Math.exp(-thetaIS*tau_plus[1])
				*Math.pow(lambda_minus/(lambda_minus+thetaIS),a_minus)*Math.exp(thetaIS*tau_minus[1]);
		
		double sum = 0;
		for (i=1; i<=d; i++){
			sum=sum+this.s0*Math.exp(this.r*(i*this.T/this.d)+this.pathGlobal[i]+this.omega*(i*this.T/this.d));	
		}
		
		return (L*Math.max(0, Math.exp(-this.r*this.T)*((double)1/this.d*sum-this.K)));
	}
	
	// Method 3 but for truncated distribution
	public double Method3Truncated(int d, double T, RandomStream stream, double thetaIS){
		// Required constants
		double mu_plus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)+this.theta)/2;
		double mu_minus=(Math.sqrt(this.theta*this.theta+2*this.sigma*this.sigma/this.v)-this.theta)/2;
		double v_plus=mu_plus*mu_plus*this.v;
		double v_minus=mu_minus*mu_minus*this.v;
		double lambda_plus=mu_plus/v_plus;
		double lambda_minus=mu_minus/v_minus;
		double a_plus=mu_plus*mu_plus/v_plus;
		double a_minus=mu_minus*mu_minus/v_minus;
		
		double[] tau  = new double[d+1];
		double[] path = new double[d+1];
		double[] tau_plus = new double[d+1]; 
		double[] tau_minus = new double[d+1];
		
		// Initial Values
		tau_plus[0]=0; tau_minus[0]=0; tau[0]=0;
		
		// Values at time t=T
		tau_minus[d]= tau[0]+GammaProcess(T, stream.nextDouble(),mu_minus,v_minus, thetaIS);

		double trunc=GammaDist.cdf(a_plus, lambda_plus, 10, Math.log(this.K/this.s0)-this.r-this.omega+tau_minus[d]);
		tau_plus[d]= tau[0]+GammaProcess(T, trunc + (1-trunc)*stream.nextDouble(),mu_plus,v_plus, 0);	
		
		int tauPlus, tauMin, tauStar;
		
		// Used to construct the bridge
		int index = d/2;
		int i = 1;
		int temp;
		while(index>=1){
			i = 1;
			while(i*index<d){
				// Indexes
				tauPlus=index*(i+1);
				tauMin =index*(i-1);
				tauStar=index*(i);
		
				// Tau plus and tau minus
				tau_plus[tauStar] = tau_plus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_plus,v_plus)*(tau_plus[tauPlus]-tau_plus[tauMin]);
				tau_minus[tauStar] = tau_minus[tauMin]+Beta(tauStar*T/d, tauMin*T/d, tauPlus*T/d,  stream.nextDouble(),mu_minus,v_minus)*(tau_minus[tauPlus]-tau_minus[tauMin]);							
				
				i=i+2;	
			}
			temp=index/2;
			index=temp;
		}
		for (int j = 1; j<=d; j++){
			// Calculate variance gamma from tau+ and tau-
			path[j] = tau_plus[j] - tau_minus[j];
		}
		this.pathGlobal = path;
		
		// Likelihood
		double L = Math.exp(thetaIS*tau_minus[1])*Math.pow(lambda_minus/(lambda_minus+thetaIS),a_minus)*(1-trunc);
		
		double sum = 0;
		for (i=1; i<=d; i++){
			sum=sum+this.s0*Math.exp(this.r*(i*this.T/this.d)+this.pathGlobal[i]+this.omega*(i*this.T/this.d));	
		}
		
		return (L*Math.max(0, Math.exp(-this.r*this.T)*((double)1/this.d*sum-this.K)));
	}
}
