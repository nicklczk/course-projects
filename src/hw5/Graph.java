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
 * @specfield nodes: Map<T, Map<T, Set<V>>> // All of the nodes in the graph
 * 
 * Abstract Invariant:
 * 	All nodes in the graph are unique 
 */
public class Graph<K, V> {
	/** The unique nodes of this graph */
	//		Map<Parent, Edge<Child, Labels>>
	private Map<K, Edge<K, V>> nodes;
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
		this.nodes = new HashMap<K, Edge<K, V>>();
		//checkRep();
	}
	
	/** Returns names of nodes of graph 
	 * @requires for all node in nodesID, node != null
	 * @return set of all the nodes in the graph in natural order
	 */
	public Set<K> getNodes() {
		return this.nodes.keySet();
	}
	
	/** Returns edges of specified parent
	 * @param parent
	 * @requires parent != null
	 * @return edges of specified parent
	 */
	public Edge<K, V> getEdges(K parent) {
		return this.nodes.get(parent).copy();
	}
	
	/** Returns true if specified node is a node of this Graph. Returns false otherwise
	 * @param node the node to be checked if node of this Graph
	 * @requires node != null
	 * @return true if node is node of Graph, false otherwise
	 */
	public boolean containsNode(K node) {
		return this.nodes.containsKey(node);
	}
	
	/** Adds specified node to Graph if not already in graph
	 * @param node node to be added to graph
	 * @requires node != null
	 * @effects adds node to graph
	 * @throws IllegalArgumentException if node is already in the graph
	 */
	public void addNode(K node, Class<?> cls) throws IllegalArgumentException {
		if (this.containsNode(node)) {
			throw new IllegalArgumentException(node + " is already in the graph");
		} else {
			if (cls == EdgeSet.class) {
				this.nodes.put(node, new EdgeSet<K, V>());
				//checkRep();
			} else if (cls == EdgeSingle.class) {
				this.nodes.put(node, new EdgeSingle<K, V>());
				//checkRep();
			}
		}
	}
	
	/** Adds edge connecting specified parent and child in graph
	 * @param parent parent node
	 * @param child child node
	 * @param edgeLabel label pertaining to edge between parent and child
	 * @requires parent != null && child != null && edgeLabel != null
	 * @throws IllegalArgumentException if parent or child is not a node in graph
	 */
	public void addEdge(K parent, K child, V edgeLabel) throws IllegalArgumentException {
		if (!this.containsNode(parent) || !this.containsNode(child)) {
			throw new IllegalArgumentException();
		} else {
			Edge<K, V> parentEdges = this.nodes.get(parent);
			parentEdges.addEdge(child, edgeLabel);
			//checkRep();
		}
	}
	
	/** Checks that the representation invariant holds (if any). */
	public void checkRep() {
		for (K node : this.nodes.keySet()) {
			assert node != null;
		}
	}
}