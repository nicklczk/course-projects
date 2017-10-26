package hw3;

import java.util.Comparator;

public class BallComparator implements Comparator<Ball>{
	/**
     * Implements the Comparator interface.
     * @return an int indicating natural ordering of Balls based on volume, where:
     * -1 - Ball one < Ball two
     * 0  - Ball one and Ball two reference the same ball or Ball one < Ball two
     * 1  - Ball one > Ball two
     */
    public int compare(Ball one, Ball two) {
    	if (one.equals(two)) {
    		return 0;
    	} else if (one.getVolume() < two.getVolume()) {
    		return -1;
    	} else {
    		return 1;
    	}
    }
}
