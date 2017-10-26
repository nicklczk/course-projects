package hw6;

/** An edge represents the connection between a parent node
 * and a child node, with the corresponding label to describe
 * the connection
 * 
 * Specification fields:
 * @specfield parent: K // parent node
 * @specfield label : V // edge label
 * @specfield child : K // child node
 * 
 * Abstract Invariant: 
 * 	Parent and child must be connected in the graph
 */
public class BookEdge<K, V> {
	/** Parent node */
	public K parent;
	/** Edge label */
	public V label;
	/** Child node */
	public K child;
	
	/* Abstraction Function: 
	 * 	Edge e represents an edge between parent and child 
	 * 
	 * Representation Invariant: 
	 * 	parent != null && label != null && child != null
	 */
	
	/** Constructs edge between parent, child with edge label
	 * @param parent parent node
	 * @param label edge label describing the edge
	 * @param child child node 
	 */
	public BookEdge(K parent, V label, K child) {
		this.parent = parent;
		this.label = label;
		this.child = child;
	}
	
	public String toString() {
		return this.parent + " to " + this.child + " with " + this.label;
	}
}
