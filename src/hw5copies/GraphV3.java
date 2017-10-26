package hw5;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** A graph is a collection of nodes (also called vertices) and edges. 
 * Each edge connects two nodes. In a directed graph, edges are one-way: 
 * an edge e = ⟨A,B⟩ indicates B that is directly reachable from A. 
 * To indicate that B is directly reachable from A and A from B, a directed 
 * graph would have edges ⟨A,B⟩ and ⟨B,A⟩. The children of node B are the 
 * nodes to which there is an edge from B.
 * 
 * Specification fields:
 * @specfield nodes   : Set<GraphNode> // All of the nodes in the graph
 * @specfield nodesID : SET<String>    // All of the nodes IDs in the graph
 * 
 * Abstract Invariant:
 * 	All nodes in the graph are unique 
 */
public class GraphV3 {
	/** The unique nodes of this graph */
	private Map<String, GraphNode> nodes;

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
		this.nodes = new HashMap<String, GraphNode>();
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
		checkRep();
	}
	
	/** Returns names of nodes of graph 
	 * @requires for all node in nodesID, node != null
	 * @return set of all the nodes in the graph in alphabetical order
	 */
	public Set<String> getNodes() {
		return new TreeSet<String>(this.nodes.keySet());
	}
	
	/** Returns set of edges of specified parent
	 * @param parent
	 * @requires parent != null
	 * @return Set of edges of specified parent
	 */
	public Set<Edge> getEdges(String parent) {
		GraphNode parentNode = this.getNode(parent);
		Set<Edge> edges = parentNode.getAllEdges();
		return edges;
	}
	
	/* Returns direct reference to target node in graph
	 * @param target node to be found
	 * @requires target != null
	 * @return reference to target node
	 * @throws NullPointerException if target is not in the graph
	 */
	private GraphNode getNode(String target) throws NullPointerException  {
		GraphNode curr = this.nodes.get(target);
		if (curr == null) {
			throw new NullPointerException(target + " is not in the graph");
		}
		return curr;
	}
	
	/** Returns copy of node in graph
	 * @param target node to be found
	 * @requires target != null
	 * @return copy of target graph node
	 * @throws NullPointerException if target is not in the graph
	 */
	public GraphNode copyNode(String target) throws NullPointerException {
		GraphNode original = getNode(target);
		GraphNode copy = new GraphNode(original.getID(), original.getAllEdges());
		return copy;
	}
	
	/** Returns true if specified node is a node of this Graph. Returns false otherwise
	 * @param node the node to be checked if node of this Graph
	 * @requires node != null
	 * @return true if node is node of Graph, false otherwise
	 */
	public boolean isNode(GraphNode node) {
		return isNode(node.getID());
	}
	
	public boolean isNode(String node) {
		return this.nodes.containsKey(node);
	}
	
	/*
	/** Removes specified node from graph if it is in the graph.
	 * @param node specified node to be removed from the graph 
	 * @requires node != null 
	 * @effects removes specified node from the graph, along with any edges
	 * 		    containing it.
	 * @return true if node was in the graph and was removed, false otherwise
	public boolean removeNode(GraphNode node) {
		GraphNode removed = this.nodes.remove(node.getID());
		checkRep();
		return removed != null;
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
				this.nodes.put(node.getID(), node);
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
	 * @throws IllegalArgumentException if parent or child is not a node in graph 
	 */
	public void addEdge(GraphNode parent, Edge edge) throws IllegalArgumentException {
		GraphNode child = edge.getChild();
		if (!this.isNode(parent) || !this.isNode(child)) {
			throw new IllegalArgumentException(parent.getID() + " or " + child.getID() + 
											   " is not a node in this graph");
		} else {
			parent.addEdge(edge);
			checkRep();
		}
	}
	
	/** Adds edge connecting specified parent and child in graph
	 * @param parent parent node
	 * @param child child node
	 * @param edgeLabel label pertaining to edge between parent and child
	 * @requires parent != null && child != null && edgeLabel != null
	 * @throws IllegalArgumentException if parent or child is not a node in graph
	 */
	public void addEdge(String parent, String child, String edgeLabel) throws IllegalArgumentException {
		addEdge(this.getNode(parent), new Edge(this.getNode(child), edgeLabel));
	}
	
	public void checkRep() {
		Set<String> ids = this.getNodes();
		for (String id : ids) {
			assert this.getNode(id) != null;
		}
	}
}
