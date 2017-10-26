package hw5copies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hw5.Edge;
import hw5.GraphNode;

/**
 * GraphNode represents an immutable node with its outgoing edges
 * 
 * Specification fields:
 * @specfield id 	: String 	 // Identification distinguishing this node from others
 * @specfield edges : List<Edge> // All of the outgoing edges from this node
 * 
 * Abstract Invariant:
 * 	A node must have some form of id
 */
public class GraphNodeCopyV2 implements Iterable<Edge> {
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
	 * @requires this.edges != null
	 * @return list of all children
	 */
	public List<GraphNode> getChildren() {
		List<GraphNode> children = new ArrayList<GraphNode>();
		for (Edge edge : edges) {
			children.add(edge.getChild());
		}
		return children;
	}
	
	/** Returns index of particular child
	 * @param child the specified child
	 * @return integer index of specified child, -1 if not child
	 */
	public int indexOfChild(String child) {
		int index = 0;
		for (Edge edge : edges) {
			if (edge.getChild().id.equals(child)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/** Returns edge(s) connecting parent to specified child 
	 * @param child a child of this GraphNode
	 * @requires child != null 
	 * @return copy of edge(s) pertaining to the specified child, null if no edges connecting to 
	 * 		   specified child
	 */
	public Edge getEdge(String child) {
		int index = indexOfChild(child);
		if (index == -1) {
			return null;
		} else {
			Edge target = edges.get(index);
			return new Edge(target.getChild(), target.getLabels());
		}
	}
	
	/** Returns true if child is a child of this GraphNode. Returns false otherwise
	 * @param child the node to be checked if child of this GraphNode
	 * @requires child != null
	 * @return true if child is child of GraphNode, false otherwise
	 */
	public boolean isChild(String child) {
		return indexOfChild(child) != -1;
	}
	
	/** Removes all edges associated with given child
	 * @param child the child to be removed from this GraphNode
	 * @throws IllegalArgumentException if child is not child of parent
	 * @return updated copy of this GraphNode with child removed
	 */
	public GraphNode removeChild(String child) throws IllegalArgumentException {
		int index = indexOfChild(child);
		if (index == -1) {
			throw new IllegalArgumentException();
		} else {
			List<Edge> copyEdges = this.getAllEdges();
			copyEdges.remove(index);
			return new GraphNode(this.id, copyEdges);
		}
	}
	
	/** Removes all outgoing edges of this GraphNode
	 * @return Copy of updated GraphNode with no outgoing edges
	 */
	public GraphNode removeAllEdges() {
		return new GraphNode(this.id);
	}
	
	/** Adds edge from this GraphNode to child GraphNode with edge label
	 * @param child the specified child which the edge will point to
	 * @param label the label associated with the added edge
	 * @requires child != null && label != null
	 * @effects adds edge connecting this GraphNode to child GraphNode with
	 * 			given edge label
	 */
	public GraphNode addEdge(GraphNode child, String label) {
		List<Edge> copyEdges = this.getAllEdges();
		int index = indexOfChild(child.id);
		if (index == -1) {
			copyEdges.add(new Edge(child, label));
		} else {
			Edge copyEdge = copyEdges.remove(index).addEdge(label);
			copyEdges.add(index, copyEdge);
		}
		return new GraphNode(this.id, copyEdges);
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
			return this.id.equals(compare.id) && this.edges.equals(compare.edges);
		} else {
			return false;
		}
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
