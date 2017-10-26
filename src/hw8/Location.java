package hw8;

/**
 * Location is an immutable class that represents a particular location on a map, 
 * containing relevant information such as:
 *  
 * @author VinnieTheVu
 *
 * @param <A> The representation of the names of the location
 * @param <B> The representation of the coordinates of the location
 * 
 * Specification fields:
 * @specfield shortName: A // The location's abbreviated name
 * @specfield fullName : A // The location's full name
 * @specfield x		   : B // The x coordinate of the location
 * @specfield y		   : B // The y coordinate of the location
 * 
 * Abstract Invariant: 
 * 	Every location must have a x and y coordinate
 */
public class Location<A, B> {
	private A shortName;
	private A fullName;
	private B x;
	private B y;
	
	/*
	 * Rep Invariant: 
	 * 	shortName != null && fullName != null && x != null && y != null
	 */
	
	/**
	 * Creates a Location given the abbreviated name, full name, and the x and y coordinates
	 * @param shortName abbreviated name of location
	 * @param fullName full name of location
	 * @param x the x coordinate 
	 * @param y the y coordinate
	 */
	public Location(A shortName, A fullName, B x, B y) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns abbreviated name of the location
	 * @return abbreviated name of the location
	 */
	public A getShortName() {
		return this.shortName;
	}
	
	/**
	 * Returns full name of the location
	 * @return full name of the location
	 */
	public A getFullName() {
		return this.fullName;
	}
	
	/**
	 * Returns x coordinate of the location
	 * @return x coordinate of the location
	 */
	public B getX() {
		return this.x;
	}
	
	/**
	 * Returns y coordinate of the locaiton
	 * @return y coordinate of the location
	 */
	public B getY() {
		return this.y;
	}
	
	/**
	 * Standard equals function 
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Location<?, ?>)) {
			return false;
		}
		Location<?, ?> location = (Location<?, ?>) obj;
		return this.shortName.equals(location.shortName) 
				&& this.fullName.equals(location.fullName)
				&& this.x.equals(location.x) 
				&& this.y.equals(location.y);
	}
	
	/**
	 * Standard hashcode function 
	 */
	@Override
	public int hashCode() {
		return shortName.hashCode() + fullName.hashCode() * 2 + x.hashCode() * 3 + y.hashCode() * 5;
	}
}
