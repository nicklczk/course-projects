package hw5copies;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import hw5.Edge;
import hw5.GraphNode;

/** A graph is a collection of nodes (also called vertices) and edges. 
 * Each edge connects two nodes. In a directed graph, edges are one-way: 
 * an edge e = ⟨A,B⟩ indicates B that is directly reachable from A. 
 * To indicate that B is directly reachable from A and A from B, a directed 
 * graph would have edges ⟨A,B⟩ and ⟨B,A⟩. The children of node B are the 
 * nodes to which there is an edge from B.
 * 
 * Specification fields:
 * @specfield nodes : Set<GraphNode> // All of the nodes in the graph
 * 
 * Abstract Invariant:
 * 	All nodes in the graph are unique 
 */
public class GraphV1 implements Iterable<GraphNode> {
	/** The unique nodes of this graph */
	private Set<GraphNode> nodes;
	private Set<String> nodesID;

	/* Abstraction Function:
	 * 	Graph, g, represents a collection of nodes and its outgoing edges. 
	 * 	The nodes are connected to other nodes through their edges. 
	 * 
	 * Representation Invariant:
	 * 	for all node in nodes, node != null 
	 * 	for all node in nodes, node.getID() is in nodesID
	 */
	
	/** Creates a new empty Graph
	 * @effects Constructs an empty Graph
	 */
	public Graph() {
		this.nodes = new HashSet<GraphNode>();
		this.nodesID = new HashSet<String>();
		checkRep();
	}
	
	/** Creates a new Graph with one node
	 * @param node the single node of the graph 
	 * @requires node != null
	 * @effects Constructs a new Graph with one node
	 * @throws IllegalArgumnetException if node.isParent()
	 */
	public Graph(GraphNode node) throws IllegalArgumentException {
		this();
		this.addNode(node);
	}
	
	/** Creates a new Graph with multiple nodes
	 * @param nodes the nodes of the graph
	 * @requires for all node in nodes, node != null 
	 * @effects Constructs a new GraphNode with  multiple nodes
	 * @throws IllegalArgumentException if a node in nodes is a parent
	 */
	/*
	public Graph(Set<GraphNode> nodes) throws IllegalArgumentException {
		this();
		for (GraphNode node : nodes) {
			if (node.isParent()) {
				throw new IllegalArgumentException(node.getID() + " is a parent");
			} else {
				this.nodes.add(node);
			}
		}
		checkRep();
	}
	*/
	
	/** Returns all nodes of graph 
	 * @requires for all node in nodes, node != null
	 * @return the a list of all the nodes in the graph
	 */
	/*
	public Set<GraphNode> getAllNodes() {
		Set<GraphNode> copy = new HashSet<GraphNode>(this.nodes);
		return copy;
	}
	*/
	
	/** Returns specified node in graph 
	 * @requires this.isNode(node)
	 * @return the node in the graph if it exists 
	 */
	/*
	public GraphNode getNode(GraphNode node) {
		for (GraphNode currNode : nodes) {
			if (node.equals(currNode)) {
				
			}
		}
	}
	*/
	
	/** Returns outgoing edges containing specified parent and child in this GraphNode
	 * @param parent a parent node in this GraphNode
	 * @param child a child node of the specified parent node
	 * @requires isNode(parent) && isChild(parent, child)
	 * @return list of outgoing edges containing specified parent and child 
	 */
	/*
	public Edge getChildrenEdges(GraphNode parent, GraphNode child) {
		
	}
	*/
	
	/** Returns all outgoing edges pertaining to the specified parent 
	 * @param parent a node of this graph
	 * @requires isNode(parent) 
	 * @return list of all edges pertaining to the specified parent
	 */
	/*
	public List<Edge> getParentEdges(String parent) {
		throw new IllegalArgumentException("not implemented yet");
	}
	*/
	
	private GraphNode getNode(String target) {
		for (GraphNode node : this.nodes) {
			if (node.getID().equals(target)) {
				return node;
			}
		}
		throw new IllegalArgumentException(target + " not found");
	}
	
	/** Returns true if specified node is a node of this Graph. Returns false otherwise
	 * @param node the node to be checked if node of this Graph
	 * @requires node != null
	 * @return true if node is node of Graph, false otherwise
	 */
	public boolean isNode(GraphNode node) {
		//return this.nodes.contains(node);
		return this.nodesID.contains(node.getID());
	}
	
	// Redundant method?
	/*
	public boolean isChild(GraphNode parent, GraphNode child) throws IllegalArgumentException {
		if (!isNode(parent)) {
			throw new IllegalArgumentException("Parent is not a node in this graph");
		} else {
			return parent.isChild(child);
		}
	}
	*/
	
	/** Removes specified node from graph if it is in the graph.
	 * @param node specified node to be removed from the graph 
	 * @requires node != null 
	 * @effects removes specified node from the graph, along with any edges
	 * 		    containing it.
	 * @return true if node was in the graph and was removed, false otherwise
	 */
	public boolean removeNode(GraphNode node) {
		if (isNode(node)) {
			for (GraphNode currNode : this.nodes) {
				if (currNode.equals(node)) {
					this.nodes.remove(currNode);
					this.nodesID.remove(currNode.getID());
				} else {
					//this.nodes.remove(currNode);
					currNode.removeChild(node);
					//this.nodes.add(currNode);
				}
			}
			checkRep();
			return true;
		}
		return false;
	}
	
	/** Removes all outgoing edges of specified node if it is in the graph
	 * @param node specified node whose outgoing edges will be removed 
	 * @requires node != null
	 * @effects Removes all outgoing edges of specified node.
	 * @return True if node is in graph and outgoing edges are removed, false otherwise 
	 * @throws IllegalArgumentException if node is not in the graph 
	 */
	/*
	public boolean removeOutgoingEdges(GraphNode node) {
		if (!this.isNode(node)) {
			//throw new IllegalArgumentException(node.getID() + " is not in this graph");
			return false;
		} else {
			for (GraphNode currNode : this.nodes) {
				System.out.println(currNode.hashCode() + ", " + node.hashCode());
				if (currNode.equals(node)) {
					//this.nodes.remove(currNode);
					currNode.removeAllEdges();
					//this.nodes.add(currNode);
					checkRep();
					return true;
				}
			}
			return false;
		}
	}
	*/
	
	/** Removes all incoming edges of specified child if it is in the graph
	 * @param child specified child node whose incoming edges will be removed
	 * @effects Removes all incoming edges of specified child node. If this child
	 * 			node is not a parent of any other node, it will be removed from the
	 *          graph.
	 */
	/*
	public void removeChildEdges(String child) {
		throw new IllegalArgumentException("not implemented yet");
	}
	*/
	
	/** Adds specified node to Graph if not already in Graph
	 * @param node node to be added to graph
	 * @requires node != null
	 * @effects adds node to next Graph
	 * @return true if node added, false if not
	 * @throws IllegalArgumentException if node.isParent()
	 */
	public boolean addNode(GraphNode node) throws IllegalArgumentException {
		if (node.isParent()) {
			throw new IllegalArgumentException(node.getID() + " is a parent");
		} else {
			if (!this.isNode(node)) {
				this.nodes.add(node);
				this.nodesID.add(node.getID());
				checkRep();
				return true;
			}
			return false;
		}
	}
	
	/** Adds edge connecting specified parent and child in graph
	 * @param parent specified parent node
	 * @param edge edge(s) connecting parent to child
	 * @requires parent != null && edge != null 
	 * @effects adds edge connecting specified parent and child in Graph. 
	 * 		    adds child to graph if not already in graph
	 * @throws IllegalArgumentException if parent is not a node in graph or 
	 * 		   child is not a node in the graph or it is a parent
	 */
	public void addEdge(GraphNode parent, Edge edge) throws IllegalArgumentException {
		GraphNode child = edge.getChild();
		if (!this.isNode(parent) || !this.isNode(child) || child.isParent()) {
			throw new IllegalArgumentException(parent.getID() + " or " + child.getID() + 
											   " is not a node in this graph or " + child.getID() 
											   + " is a parent");
		/*
		} else if (child.isParent() && !this.isNode(child)) {
			throw new IllegalArgumentException(child.getID() + " is a parent and not a node in this graph");
		*/
		} else {
			/*
			// It is not a parent or it is a node in the graph
			if (!this.isNode(child)) {
				this.addNode(child);
			}
			*/
			parent.addEdge(edge);
			checkRep();
			/*
			if (!this.isNode(child)) {
				this.addNode(child);
			}
			*/
		}
	}
	
	/** Returns String representation of this graph 
	 * @requires this.nodes != null
	 * @return parent nodes in alphabetical order, following behavior toStrin behavior of 
	 * 		   GraphNode
	 */
	/*
	public String toString() {
		
	}
	*/
	
	/**
	 * Returns an iterator of the nodes contained in the graph
	 *
	 * @return an iterator of the nodes contained in the graph in order from
	 *         the beginning of the list to the end of the list.
	 */
	public Iterator<GraphNode> iterator() {
		return nodes.iterator();
	}
	
	public void checkRep() {
		Set<String> ids = new HashSet<String>();
		for (GraphNode node : this.nodes) {
			assert node != null;
			ids.add(node.getID());
		}
		assert ids.equals(nodesID);
	}
}
