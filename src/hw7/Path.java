package hw7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hw6.BookEdge;

/**
 * Path represents the path from some starting point to some destination point
 * 
 * @author VinnieTheVu
 * 
 * Specification fields:
 * @specfield weight: double 					// Represents weight of the path
 * @specfield path  : List<BookEdge<L, Double>> // Represents the connections from one node to the next
 * 												// comprising the entire path
 * @param <L> The representation of the nodes
 * 
 * Abstract Invariant:
 * 	A path must have a start and an end
 */
public class Path<L> implements Comparable<Path<L>>, Iterable<BookEdge<L, Double>>{
	/** Weight of the path */
	private double weight;
	/** The connection from node to node representing the path */
	private List<BookEdge<L, Double>> path;
	
	/*
	 * Abstraction Function: 
	 * 	List l represents each connection from one node to the next summing up
	 * 	to represent the entire path from beginning to end
	 * 
	 * Representation Invariant:
	 * 	weight != null && path != null
	 */
	
	/**
	 * Creates empty path
	 */
	public Path() {
		path = new ArrayList<BookEdge<L, Double>>();
		weight = 0.0;
	}
	
	/**
	 * Copy constructor 
	 * @param path the path to be copied
	 * @requires path != null
	 * @effects Constructs a copy of given path
	 */
	public Path(Path<L> path) {
		this.path = new ArrayList<BookEdge<L, Double>>(path.path);
		this.weight = path.weight;
	}
	
	/**
	 * Adds a connection from last node in the path to another node, making
	 * this other node the last node in the path
	 * @param edge connection from last node in path to another node
	 * @requires edge != null
	 * @effects adds connection from last node in path to another node and adjusts weight to 
	 * 			accomodate new connection
	 */
	public void add(BookEdge<L, Double> edge) {
		this.path.add(edge);
		weight += edge.label;
	}
	
	/**
	 * Removes a connection from the path
	 * @param index connection to be removed
	 * @requires index == 0 || index == path.size() - 1
	 * @effects removes specified connection from path and adjusts weight to accomodate 
	 * 			removal of connection
	 */
	public void remove(int index) {
		weight -= get(index).label;
		this.path.remove(index);
	}
	
	/**
	 * Returns size of path
	 * @return size of path
	 */
	public int size() {
		return this.path.size();
	}
	
	/**
	 * Gets specified connection in path
	 * @param index connection's place in path
	 * @return specified connection in path 
	 */
	public BookEdge<L, Double> get(int index) {
		return this.path.get(index);
	}
	
	/**
	 * Gets weight of path
	 * @return weight of path
	 */
	public double getWeight() {
		return this.weight;
	}
	
	/**
	 * Returns string representation of path, where path is represented
	 * by its weight
	 * @return string representation of path
	 */
	public String toString() {
		return "" + this.weight;
	}

	/**
	 * Standard compareTo method 
	 */
	@Override
	public int compareTo(Path<L> compare) {
		if (this.equals(compare)) {
			return 0;
		} else {
			return Double.compare(this.weight, compare.weight);
		}
	}
	
	/**
	 * Standard Iterator method 
	 */
	@Override
	public Iterator<BookEdge<L, Double>> iterator() {
		return path.iterator();
	}
}
