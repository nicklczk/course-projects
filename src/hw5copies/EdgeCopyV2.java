package hw5copies;

import java.util.Set;
import java.util.TreeSet;

import hw4.RatNum;
import hw5.Edge;
import hw5.GraphNode;

/**
 * Edge represents a immutable edge(s) that connects two nodes
 * 
 * Specification fields:
 * @specfield label : Set<String> // Representative of different edges 
 * 									 pointing to the same child. Contains
 * 									 information pertaining to that specific
 * 									 edge.
 * @specfield child  : GraphNode  // Node that this node points to
 * 
 * Abstract Invariant:
 * 	An edge must connect two nodes
 */
public class EdgeCopyV2 {
	/** The node that the edge(s) are pointing to */
	private GraphNode child;
	
	/** The different labels representing different edges pointing to the same child */
	private Set<String> label;
	
	/* Abstraction Function:
	 * 	Edge, e, represents the connection between a node and child node. 
	 * 	The edge has a label that denote some characteristic about the edge. 
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
		this.label = new TreeSet<String>();
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
	public Edge(GraphNode child, TreeSet<String> label) {
		this.child = child;
		this.label = label;
		checkRep();
	}
	
	/** Returns label(s) associated with this edge
	 * @return the label(s) associated with this edge
	 */
	public TreeSet<String> getLabels() {
		return copyLabel();
	}
	
	/** Returns GraphNode that this edge is pointing to
	 * @requires this.child != null
	 * @return GraphNode that this edge is pointing to
	 */
	public GraphNode getChild() {
		return copyNode();
	}
	
	/** Returns a copy of the GraphNode that this edge is pointing to
	 * @requires this.child != null
	 * @return copy of GraphNode that this edge is pointing to
	 */
	private GraphNode copyNode() {
		return new GraphNode(child.getID(), child.getAllEdges());
	}
	
	/** Returns true if label is a label of an edge of this child node, false otherwise
	 * @param label specified label to be checked if is label of this edge
	 * @return true if label is label of an edge of this child node, false otherwise
	 */
	public boolean isLabel(String label) {
		return this.label.contains(label);
	}
	
	/** Returns updated Edge with additional specified edge pointing to this child 
	 * @param label specified additional edge that points to this child
	 * @requires label != null
	 * @return updated copy of Edge with additional specified edge pointing to this child
	 */
	public Edge addEdge(String label) {
		TreeSet<String> updated = copyLabel();
		updated.add(label);
		return new Edge(getChild(), updated);
	}
	
	/** Returns updated Edge with specified edge removed
	 * @param label
	 * @return updated copy of Edge with specified edge removed
	 * @throws IllegalArgumentException if !isLabel(label)
	 */
	public Edge removeEdge(String label) 
						  throws IllegalArgumentException {
		if (!isLabel(label)) {
			throw new IllegalArgumentException();
		} else {
			TreeSet<String> updated = copyLabel();
			updated.remove(label);
			return new Edge(getChild(), updated);
		}
	}
	
	/** Returns copy of labels of edges pointing to this child
	 * @return copy of labels of edges pointing to this child
	 */
	private TreeSet<String> copyLabel() {
		TreeSet<String> copy = new TreeSet<String>();
		for (String labels : this.label) {
			copy.add(labels);
		}
		return copy;
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
			return this.child.equals(compare.child) && this.label.equals(compare.label);
		} else {
			return false;
		}
	}
	
	/** Checks that the representation invariant holds (if any). */
	private void checkRep() {
		assert (this.label != null && this.child != null);
		for (String labels : label) assert (labels != null);
	}
}
