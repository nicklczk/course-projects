package hw5tobeupdated;
 
import java.util.HashSet;
import java.util.Set;

/**
 * Edge represents a mutable edge(s) that connects two nodes
 * 
 * Specification fields:
 * @specfield label : Set<String> // Representative of different edges 
 * 									 pointing to the same child. Contains
 * 									 information pertaining to that specific
 * 									 edge.
 * @specfield child  : GraphNode  // Node that this node points to
 * 
 * Abstract Invariant:
 * 	An edge must connect two nodes and the labels must be unique
 */
public class Edge implements Comparable<Edge> {
	/** The node that the edge(s) are pointing to */
	private GraphNode child;
	
	/** The different labels representing different edges pointing to the same child */
	private Set<String> label;
	
	/* Abstraction Function:
	 * 	AF(r) = Each label in r.label represents an edge from parent to r.child
	 * 
	 * Representation Invariant:
	 * 	child != null && label != null && 
	 *  for each labels in label, labels != null
	 */
	
	/** Creates a new edge with with label pointing to GraphNode child
	 * @param child the GraphNode that this edge is pointing to
	 * @param label the information associated with this edge
	 * @requires child != null && label != null
	 * @effects Constructs a new edge with GraphNode child and String label
	 */
	public Edge(GraphNode child, String label) {
		this.child = child;
		this.label = new HashSet<String>();
		this.label.add(label);
		checkRep();
	}
	
	/** Creates new edge(s) with label(s) pointing to GraphNode child
	 * @param child the GraphNode that this edge is pointing to
	 * @param label the information associated with this edge, with each unique label
	 * 		  corresponding to a different edge of the same child
	 * @requires child != null && !label.isEmpty()
	 * @effects Constructs a new edge with GraphNode child and String label
	 */
	public Edge(GraphNode child, Set<String> label) {
		this.child = child;
		this.label = label;
		checkRep();
	}
	
	/** Returns label(s) associated with this edge
	 * @return the label(s) associated with this edge
	 */
	public Set<String> getLabels() {
		Set<String> copy = new HashSet<String>(this.label);
		return copy;
	}
	
	/** Returns GraphNode that this edge is pointing to
	 * @requires this.child != null
	 * @return GraphNode that this edge is pointing to
	 */
	public GraphNode getChild() {
		GraphNode copy = new GraphNode(child.getID(), child.getAllEdges());
		assert child.equals(copy);
		return copy;
	}
	
	
	/** Returns true if label is a label of an edge of this child node, false otherwise
	 * @param label specified label to be checked if is label of this edge
	 * @return true if label is label of an edge of this child node, false otherwise
	 */
	public boolean isLabel(String label) {
		return this.label.contains(label);
	}
	
	/** Returns true if this edge contains no labels, false otherwise
	 * @return true if this edge contains no labels, false otherwise 
	 */
	public boolean isEmpty() {
		return this.label.isEmpty();
	}
	
	/** Adds edge pointing to this child if the label is unique 
	 * @param label specified additional edge that points to this child
	 * @requires label != null
	 * @effects adds edge pointing to this child if label is unique
	 * @return true if edge successfully added, false otherwise
	 */
	public Boolean addLabel(String label) {
		boolean unique = this.label.add(label);
		checkRep();
		return unique;
	}
	
	/** Removes edge pointing to this child if edge exists
	 * @param label specified edge that points to this child
	 * @requires label != null
	 * @effects removes edge pointing to this child with specified label if
	 * 			it exists
	 * @return true if edge is removed, false otherwise
	 */
	public Boolean removeLabel(String label) {
		boolean exists = this.label.remove(label);
		checkRep();
		return exists;
	}
	
	/** Returns String representation of this edge
	 * @requires this.child != null && this.label != null
	 * @return Child denoted by "Child:" followed by a space and child id
	 * 		   Label denoted by "Label(s):"
	 * 		   Each label will be separated by a \n 
	 * 		   Child and label separated by a \n
	 * 		   Child appears first, label follows it
	 */
	public String toString() {
		String rep = "Child: " + this.child.getID() + "\nLabel(s):";
		
		for (String labels : label) {
			rep += "\n" + labels;
		}
		return rep;
	}
	
	/** 
	 * Standard equality operation.
	 * @param obj The object to be compared for equality.
	 * @return true if and only if 'obj' is an instance of a Edge
	 * and 'this' and 'obj' represent the same edge.
	 */
	@Override
	public boolean equals(/*@Nullable*/ Object obj) {
		if (obj instanceof Edge) {
			Edge compare = (Edge) obj;
			return this.child.getID().equals(compare.child.getID()) && this.label.equals(compare.label) && 
				   this.hashCode() == compare.hashCode(); 
		} else {
			return false;
		}
	}
	
	/** Standard hashCode function.
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
	public int hashCode() {
		// all instances whose fields are null must return the same thing
		if (this.child == null || this.label == null) {
			return 0;
		} else {
			return (this.child.getID().hashCode() * 2) + (this.label.hashCode() * 3);
		}
	}
	
	/** Compares two Edges
	 * @param compareEdge The Edge to be compared
	 * @requires compareEdge != null
	 * @return value less than zero if this child's id is lexigraphically less, 0 if they are the 
	 * 		   the same edge, and a value greater than zero of this child's id is lexgraphically
	 * 		   greater 
	 */
	public int compareTo(Edge compareEdge) {
		if (this.equals(compareEdge)) {
			return 0;
		} else {
			return this.child.getID().compareTo(compareEdge.child.getID());
		}
	}
	
	/** Checks that the representation invariant holds (if any). */
	private void checkRep() {
		assert (this.label != null && this.child != null);
		for (String labels : label) assert (labels != null);
	}
}
