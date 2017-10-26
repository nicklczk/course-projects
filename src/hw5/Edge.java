package hw5;

import java.util.Set;

public interface Edge<K, V> {
	void addEdge(K child, V label);
	Edge<K, V> emptyEdge();
	Edge<K, V> copy();
	Set<K> getChildren();
	Set<V> getLabels(K child);
	V getLabel(K child);
	boolean containsChild(K child);
}
