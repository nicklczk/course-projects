package hw5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EdgeSet<K, V> implements Edge<K, V> {
	private Map<K, Set<V>> edges;
	
	public EdgeSet() {
		edges = new HashMap<K, Set<V>>();
	}
	
	public EdgeSet(EdgeSet<K, V> edge) {
		this.edges = new HashMap<K, Set<V>>(edge.edges);
	}
	
	@Override
	public Edge<K, V> copy() {
		Edge<K, V> copy = new EdgeSet<K, V>(this);
		return copy;
	}
	
	@Override
	public void addEdge(K child, V edgeLabel) {
		if (!edges.containsKey(child)) {
			Set<V> labels = new HashSet<V>();
			labels.add(edgeLabel);
			edges.put(child, labels);
		} else {
			edges.get(child).add(edgeLabel);
		}
	}

	@Override
	public Edge<K, V> emptyEdge() {
		return new EdgeSet<K, V>();
	}
	
	@Override
	public Set<K> getChildren() {
		return edges.keySet();
	}
	
	@Override
	public Set<V> getLabels(K child) {
		return edges.get(child);
	}
	
	@Override
	public V getLabel(K child) {
		return null;
	}

	@Override
	public boolean containsChild(K child) {
		return edges.containsKey(child);
	}

}
