package hw5tobeupdated;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * GraphNode represents an mutable node with its outgoing edges
 * 
 * Specification fields:
 * @specfield id 	: String 	 // Identification distinguishing this node from others
 * @specfield edges : Set<Edge>  // All of the outgoing edges from this node
 * 
 * Abstract Invariant:
 * 	A node must have some form of id
 */
public class GraphNode implements Iterable<Edge>, Comparable<GraphNode> {
	/** The unique identifier for this node */
	private String id;
	
	/** The outgoing edges of this node */
	private Set<Edge> edges;
	
	/* Abstraction Function:
	 * 	AF(r) = Each edge in r.edges represents a set of edges from r, 
	 * 	denoted by r.id to some child
	 *  
	 * Representation Invariant:
	 * 	id != null && edges != null && 
	 * 	for all edge in edges, edge != null
	 */
	
	/** Creates a new GraphNode with identification id
	 * @param id the unique identifier for the GraphNode
	 * @requires id != null
	 * @effects Constructs a new GraphNode with identification id
	 */
	public GraphNode(String id) {
		this.id = id;
		edges = new HashSet<Edge>();
		checkRep();
	}
	
	/** Creates a new GraphNode with identification id and its edge(s) to one child
	 * @param id identification used to distinguish this GraphNode from others
	 * @param edges all of the outgoing edges for this GraphNode to one child
	 * @requires id != null && edges != null
	 * @effects Constructs a new GraphNode with identification id and edge(s) to one child
	 */
	public GraphNode(String id, Edge edges) {
		this(id);
		this.edges.add(edges);
		checkRep();
	}
	
	/** Creates a new GraphNode with identification id and its edge(s)
	 * @param id identification used to distinguish this GraphNode from others
	 * @param edges all of the outgoing edges for this GraphNode
	 * @requires id != null && edges != null
	 * @effects Constructs a new GraphNode with identification id and edge(s) 
	 */
	public GraphNode(String id, Set<Edge> edges) {
		this.id = id;
		this.edges = edges;
		checkRep();
	}
	
	/** Returns id of this GraphNode
	 * @requires this.id != null
	 * @return the id of this GraphNode
	 */
	public String getID() {
		return this.id;
	}
	
	/** Returns all outgoing edges of this GraphNode 
	 * @requires this.edges != null
	 * @return Set of all outgoing edges of this GraphNode
	 */
	public Set<Edge> getAllEdges() {
		Set<Edge> copy = new HashSet<Edge>(this.edges);
		assert this.edges.equals(copy);
		return copy;
	}
	
	/** Returns edge(s) connecting parent to specified child 
	 * @param child a child of this GraphNode
	 * @requires child != null 
	 * @return copy of edge(s) pertaining to the specified child, null if no edges connecting to 
	 * 		   specified child
	 * @throws IllegalArgumentException if argument is not a child of this node 
	 */
	public Edge getEdge(GraphNode child) throws IllegalArgumentException {
		for (Edge edge : edges) {
			if (edge.getChild().equals(child)) {
				Edge copy = new Edge(child, edge.getLabels());
				assert edge.equals(copy);
				return copy;
			}
		}
		throw new IllegalArgumentException();
	}
	
	/** Returns true if child is a child of this GraphNode. Returns false otherwise
	 * @param child the node to be checked if child of this GraphNode
	 * @requires child != null
	 * @return true if child is child of GraphNode, false otherwise
	 */
	public boolean isChild(GraphNode child) {
		for (Edge edge : edges) {
			if (edge.getChild().equals(child)) {
				return true;
			}
		}
		return false;
	}
	
	/** Returns true if this node has children, false otherwise
	 * @return true if this node has children, false otherwise
	 */
	public boolean isParent() {
		return !this.edges.isEmpty();
	}
	
	/** Removes edge associated with given child
	 * @param child the specified child
	 * @param label the label to be removed
	 * @requires child != null && label != null
	 * @effects removes label from given child if child and label exist
	 * @return true edge exists, false otherwise
	 * @throws IllegalArgumentException thrown if child does not exist
	 */
	public boolean removeEdge(GraphNode child, String label) throws IllegalArgumentException {
		Edge old = getEdge(child);
		edges.remove(old);
		Boolean removed = old.removeLabel(label);
		if (!old.isEmpty()) {
			edges.add(old);
		}
		return removed;
	}
	
	/** Removes all edges associated with given child
	 * @param child the child to be removed from this GraphNode
	 * @requires child != null
	 * @effects removes edge containing child from this node if child is present
	 * @return true if child is present, false otherwise
	 */
	public boolean removeChild(GraphNode child) {
		for (Edge edge : edges) {
			if (edge.getChild().equals(child)) {
				this.edges.remove(edge);
				checkRep();
				return true;
			}
		}
		return false;
	}
	
	/** Removes all outgoing edges of this GraphNode
	 * @effects removes all edges of this node 
	 */
	public void removeAllEdges() {
		this.edges.clear();
		checkRep();
	}
	
	/** Adds edge from this GraphNode to child GraphNode with edge label
	 * @param newEdge the specified edge to be added to this GraphNode
	 * @requires newEdge != null 
	 * @effects adds edge connecting this GraphNode to given child GraphNode with
	 * 			given edge label
	 * @return true if child is already child of this node, false otherwise
	 */
	public boolean addEdge(Edge newEdge) {
		for (Edge edge : edges) {
			if (edge.getChild().equals(newEdge.getChild())) {
				for (String label : newEdge.getLabels()) {
					edge.addLabel(label);
					assert edge.isLabel(label);
				}
				checkRep();
				return true;
			}
		}
		this.edges.add(newEdge);
		checkRep();
		return false;
	}
	
	/** Returns String representation of this GraphNode
	 * @requires this.id != null && this.edges != null
	 * @return In form of Parent \n child \n label
	 * 		   GraphNode denoted by "Parent:" followed by a space and parent id 
	 * 		   Edges follow toString behavior of Edge 
	 */
	public String toString() {
		String rep = "Parent: " + id;
		for (Edge edge : edges) {
			rep += "\n" + edge;
		}
		return rep;
	}
	
	/** 
	 * Standard equality operation.
	 * @param obj The object to be compared for equality.
	 * @return true if and only if 'obj' is an instance of a GraphNode
	 * and 'this' and 'obj' represent the same graphnode.
	 */
	@Override
	public boolean equals(/*@Nullable*/ Object obj) {
		if (obj instanceof GraphNode) {
			GraphNode compare = (GraphNode) obj;
			return this.edges.equals(compare.edges) && this.id.equals(compare.id) &&
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
		if (this.id == null || this.edges == null) {
			return 0;
		} else {
			return (this.id.hashCode() * 2) + (this.edges.hashCode() * 3);
		}
	}
	
	/** Returns an iterator of the edges contained in the list.
	 * @return an iterator of the edges contained in the stack in order from
	 *         the beginning of the list to the end of the list.
	 */
	public Iterator<Edge> iterator() {
		return edges.iterator();
	}
	
	/** Compares two GraphNodes
	 * @param compareNode The GraphNode to be compared
	 * @requires compareNode != null
	 * @return value less than zero if this node's id is lexigraphically less, 0 if they are the 
	 * 		   the same node, and a value greater than zero of this node's id is lexgraphically
	 * 		   greater 
	 */
	public int compareTo(GraphNode compareNode) {
		if (this.equals(compareNode)) {
			return 0;
		} else {
			return this.id.compareTo(compareNode.id);
		}
	}
	
	/** Checks that the representation invariant holds (if any). */
	private void checkRep() {
		assert (id != null && edges != null);
		for (Edge edge : edges) assert (edge != null);
	}
}