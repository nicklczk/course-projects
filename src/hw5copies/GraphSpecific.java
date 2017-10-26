package hw5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** A graph is a collection of nodes (also called vertices) and edges. 
 * Each edge connects two nodes. In a directed graph, edges are one-way: 
 * an edge e = ⟨A,B⟩ indicates B that is directly reachable from A. 
 * To indicate that B is directly reachable from A and A from B, a directed 
 * graph would have edges ⟨A,B⟩ and ⟨B,A⟩. The children of node B are the 
 * nodes to which there is an edge from B.
 * 
 * Specification fields:
 * @specfield nodes: Map<String, Map<String, Set<String>>> // All of the nodes in the graph
 * 
 * Abstract Invariant:
 * 	All nodes in the graph are unique 
 */
public class GraphSpecific {
	/** The unique nodes of this graph */
	//							   Edge
	//			Parent		Child	+	Labels
	private Map<String, Map<String, Set<String>>> nodes;

	/* Abstraction Function:
	 * 	Graph, g, represents a collection of nodes and its outgoing edges. 
	 * 	The nodes are connected to other nodes through their edges. 
	 * 
	 * Representation Invariant:
	 * 	for all node in nodes, node != null 
	 */
	
	/** Creates a new empty Graph
	 * @effects Constructs an empty Graph
	 */
	public Graph() {
		this.nodes = new HashMap<String, Map<String, Set<String>>>();
		checkRep();
	}
	
	/** Returns names of nodes of graph 
	 * @requires for all node in nodesID, node != null
	 * @return set of all the nodes in the graph in alphabetical order
	 */
	public Set<String> getNodes() {
		return this.nodes.keySet();
	}
	
	/** Returns edges of specified parent
	 * @param parent
	 * @requires parent != null
	 * @return edges of specified parent
	 */
	public Map<String, Set<String>> getEdges(String parent) {
		return new HashMap<String, Set<String>>(this.nodes.get(parent));
	}
	
	/** Returns true if specified node is a node of this Graph. Returns false otherwise
	 * @param node the node to be checked if node of this Graph
	 * @requires node != null
	 * @return true if node is node of Graph, false otherwise
	 */
	public boolean containsNode(String node) {
		return this.nodes.containsKey(node);
	}
	
	/** Adds specified node to Graph if not already in graph
	 * @param node node to be added to graph
	 * @requires node != null
	 * @effects adds node to graph
	 * @throws IllegalArgumentException if node is already in the graph
	 */
	public void addNode(String node) throws IllegalArgumentException {
		if (this.containsNode(node)) {
			throw new IllegalArgumentException(node + " is already in the graph");
		} else {
			this.nodes.put(node, new HashMap<String, Set<String>>());
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
		if (!this.containsNode(parent) || !this.containsNode(child)) {
			throw new IllegalArgumentException();
		} else {
			Map<String, Set<String>> parentEdges = this.nodes.get(parent);
			if (!parentEdges.containsKey(child)) {
				Set<String> labels = new HashSet<String>();
				labels.add(edgeLabel);
				parentEdges.put(child, labels);
			} else {
				parentEdges.get(child).add(edgeLabel);
			}
		}
	}
	
	/** Checks that the representation invariant holds (if any). */
	public void checkRep() {
		for (String node : this.nodes.keySet()) {
			assert node != null;
		}
	}
}