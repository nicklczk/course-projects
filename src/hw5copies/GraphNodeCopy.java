package hw5copies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hw5.Edge;
import hw5.GraphNode;

/**
 * GraphNode represents an mutable node with its outgoing edges
 * 
 * Specification fields:
 * @specfield id 	: String 	 // Identification distinguishing this node from others
 * @specfield edges : List<Edge> // All of the outgoing edges from this node
 * 
 * Abstract Invariant:
 * 	A node must have some form of id
 */
public class GraphNodeCopy implements Iterable<Edge> {
	/** The unique identifier for this node */
	private String id;
	
	/** The outgoing edges of this node */
	private List<Edge> edges;
	
	/* Abstraction Function:
	 * 	GraphNode, n, represents a node and its outgoing edges. 
	 * 	The node, denoted by id, is connected to other nodes through its edges, this.edges. 
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
		edges = new ArrayList<Edge>();
		checkRep();
		//throw new IllegalArgumentException("not implemented yet");
	}
	
	/*
	/** Creates a new GraphNode with identification id and one edge
	 * @param id identification used to distinguish this GraphNode from others
	 * @param edge GraphNode's single outgoing edge 
	 * @requires id != null && edge != null
	 * @effects Constructs a new GraphNode with identification id and one edge
	 */
	/*
	public GraphNode(String id, Edge edge) {
		this(id);
		edges.add(edge);
		checkRep();
		//throw new IllegalArgumentException("not implemented yet");
	}
	*/
	
	/** Creates a new GraphNode with identification id and its edges 
	 * @param id identification used to distinguish this GraphNode from others
	 * @param edges all of the outgoing edges for this GraphNode
	 * @requires id != null && edges != null &&
	 * 			 for all edge in edges, edge != null
	 * @effects Constructs a new GraphNode with identification id and multiple edges
	 */
	public GraphNode(String id, List<Edge> edges) {
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
	 * @return List of all outgoing edges of this GraphNode
	 */
	public List<Edge> getAllEdges() {
		return copyEdges();
		//throw new IllegalArgumentException("not implemented yet");
	}
	
	/** Returns a copy of all outgoing edges of this GraphNode 
	 * @requires this.edges != null
	 * @return List of all outgoing edges of this GraphNode
	 */
	private List<Edge> copyEdges() {
		List<Edge> copy = new ArrayList<Edge>();
		for (Edge edge : edges) {
			copy.add(edge);
		}
		return copy;
	}
	
	/** Returns children of this GraphNode
	 * @requires this.edges != null &&
	 * 			 for all edge in this.edges, edge != null 
	 * @return list of all children
	 */
	public List<GraphNode> getChildren() {
		List<GraphNode> children = new ArrayList<GraphNode>();
		for (Edge edge : edges) {
			children.add(edge.getChild());
		}
		return children;
	}
	
	/** Returns edge(s) connecting parent to specified child 
	 * @param child a child of this GraphNode
	 * @throws IllegalArgumentException if child is not a child of this GraphNode 
	 * @return list of all edges pertaining to the specified child 
	 */
	public List<Edge> getConnectingEdge(String child) 
									   throws IllegalArgumentException {
		if (!isChild(child)) {
			throw new IllegalArgumentException(child + " is not a child of " + id);
		}
		List<Edge> connectingEdges = new ArrayList<Edge>();
		for (Edge edge : edges) {
			if (edge.getChild().getID().equals(child)) {
				connectingEdges.add(edge);
			}
		}
		return connectingEdges;
	}
	
	/** Returns true if child is a child of this GraphNode. Returns false otherwise
	 * @param child the node to be checked if child of this GraphNode
	 * @requires child != null
	 * @return true if child is child of GraphNode, false otherwise
	 */
	public boolean isChild(String child) {
		for (Edge edge : edges) {
			if (edge.getChild().getID().equals(child)) { 
				return true;
			}
		}
		return false;
	}
	
	/** Removes all edges associated with given child
	 * @param child
	 * @requires isChild(child)
	 * @effects removes all edges associated with given child
	 */
	public void removeEdges(String child) {
		throw new IllegalArgumentException("not implemented yet");
	}
	
	/** Removes all outgoing edges of this GraphNode
	 * @effects removes all edges of this GraphNode
	 */
	public void removeAllEdges() {
		throw new IllegalArgumentException("not implemented yet");
	}
	
	/** Adds edge from this GraphNode to next GraphNode with edge label
	 * @param child
	 * @param label
	 * @requires child != null && label != null
	 * @effects adds edge connecting this GraphNode to next GraphNode with
	 * 			given edge label
	 */
	public void addEdge(GraphNode child, String label) {
		edges.add(new Edge(child, label));
		checkRep();
	}
	
	/** Returns String representation of this GraphNode
	 * @requires this.id != null && this.edges != null
	 * @return In form of GraphNode \n child + label
	 * 		   GraphNode denoted by "Parent:" followed by a space and parent id 
	 * 		   Edges follow toString behavior of Edge 
	 * 		   Each edge separated by \n
	 */
	public String toString() {
		String rep = "Parent: " + id;
		for (Edge edge : edges) {
			rep += "\n" + edge;
		}
		return rep;
	}
	
	/**
	 * Returns an iterator of the edges contained in the list.
	 *
	 * @return an iterator of the edges contained in the stack in order from
	 *         the beginning of the list to the end of the list.
	 */
	public Iterator<Edge> iterator() {
		return edges.iterator();
	}
	
	/** Checks that the representation invariant holds (if any). */
	private void checkRep() {
		assert (id != null && edges != null);
		for (Edge edge : edges) assert (edge != null);
	}
}