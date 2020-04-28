
public class TowersOfHanoi {


    /**
     * output a sequence of disk moves for the Towers of Hanoi problem
     *
     * @param n number of disks
     * @param x source tower
     * @param y destination tower
     * @param z intermediate tower
     */
	
    public static void towersOfHanoi(int n, int x, int y, int z) {   // Move the top n disks from tower x to tower y.
        // Use tower z for intermediate storage.
    	if(n > 0) {
    	towersOfHanoi(n-1, x,z,y);
    	System.out.println("Move top disk from tower " + x + " to top of tower "+ y);
    	towersOfHanoi(n-1, z,y,x);
    	} else {
    		return;
    	}
    }
}
