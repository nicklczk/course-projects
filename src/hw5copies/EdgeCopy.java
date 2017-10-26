package hw5copies;

import hw5.Edge;
import hw5.GraphNode;

/**
 * Edge represents an immutable edge that connects two nodes
 * 
 * Specification fields:
 * @specfield label : String 	// Info pertaining to edge
 * @specfield child  : GraphNode // Node that this node points to
 * 
 * Abstract Invariant:
 * 	An edge must connect two nodes
 */
public class EdgeCopy {
	/** The node that this edge is pointing to */
	private GraphNode child;
	
	/** The label denoting a property of the edge */
	private String label;
	
	/* Abstraction Function:
	 * 	Edge, e, represents the connection between a node and child node. 
	 * 	The edge has a label that denote some characteristic about the edge. 
	 * 
	 * Representation Invariant:
	 * 	child != null && label != null
	 */
	
	/** Creates a new edge with GraphNode child and String label
	 * @param child the GraphNode that this edge is pointing to
	 * @param label the information associated with this edge
	 * @requires child != null && label != null
	 * @effects Constructs a new edge with GraphNode child and String label
	 */
	public Edge(GraphNode child, String label) {
		this.child = child;
		this.label = label;
		checkRep();
	}
	
	/** Returns label associated with this edge
	 * @requires this.label != null
	 * @return the label associated with this edge
	 */
	public String getLabel() {
		return this.label;
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
	
	/** Returns this edge with newLabel as the label
	 * @requires this.child != null && newLabel != null
	 * @param newLabel the new label for this edge
	 * @return this edge where this.label = newLabel
	 */
	public Edge changeLabel(String newLabel) {
		return new Edge(getChild(), newLabel);
	}
	
	/** Returns String representation of this edge
	 * @requires this.child != null && this.label != null
	 * @return Child denoted by "Child:" followed by a space and child id
	 * 		   Label denoted by "Label:" followed by a space and the label 
	 * 		   Child and label separated by a comma and a space.
	 * 		   Child appears first, label follows it
	 */
	public String toString() {
		return "Child: " + this.child.getID() + ", Label: " + this.label;
	}
	
	/** Checks that the representation invariant holds (if any). */
	private void checkRep() {
		assert (this.label != null && this.child != null);
	}
}
