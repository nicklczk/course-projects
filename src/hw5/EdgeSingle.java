package hw5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EdgeSingle<K, V> implements Edge<K, V> {
	private Map<K, V> edge;

	public EdgeSingle() {
		edge = new HashMap<K, V>();
	}
	
	public EdgeSingle(EdgeSingle<K, V> edge) {
		this.edge = new HashMap<K, V>(edge.edge);
	}
	
	@Override
	public void addEdge(K child, V label) {
		edge.put(child, label);
	}

	@Override
	public Edge<K, V> emptyEdge() {
		return new EdgeSingle<K, V>();
	}

	@Override
	public Edge<K, V> copy() {
		Edge<K, V> copy = new EdgeSingle<K, V>(this);
		return copy;
	}

	@Override
	public Set<K> getChildren() {
		return edge.keySet();
	}

	@Override
	public Set<V> getLabels(K child) {
		Set<V> set = new HashSet<V>();
		set.add(edge.get(child));
		return set;
	}
	
	@Override
	public V getLabel(K child) {
		return edge.get(child);
	}
	
	@Override
	public boolean containsChild(K child) {
		return edge.containsKey(child);
	}
}
