package gutenberg.workers;

import java.util.Random;

/*
 * Our own randomizer class that does better than the LCG algorithm used by 
 * Random 
 * 
 * The code here has been taken from 
 * http://www.javamex.com/tutorials/random_numbers/java_util_random_subclassing.shtml
 * 
 * The above has a wonderful discussion on how good Java's Randomizer is (not very) and how 
 * one can do better 
 */


public class XORRandom extends Random {
	
	/**
     * 
     */
    public XORRandom(int max) {
		this.max = max;
	} 
	
	public int nextInt() {
		long x = this.seed ; 
		x ^= ( x << 21) ;
		x ^= ( x >>> 35) ;
		x ^= ( x << 4) ;
		this.seed = x ; 
		return (int)(x % max) ;
	}

	private long seed = System.nanoTime() ;
	private int max ;
	
    private static final long serialVersionUID = 2360131650554833587L;
}
