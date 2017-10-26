package hw5tobeupdated;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hw5.Edge;
import hw5.Graph;

public class TestCaseGenerator {
	/*
	private GraphNode zeroEdges = new GraphNode("zeroEdges");
	
	public Set<String> genMultLabels(int labels) {
		Set<String> threeLabels = new TreeSet<String>();
		for (int i = 0; i < labels; i++) {
			threeLabels.add("label" + i);
		}
		return threeLabels;
	}
	
	public String genToString(int labels) {
		String rep = "Child: node" + labels + "\nLabel(s):";
		for (int i = 0; i < labels; i++) {
			rep += "\nlabel" + i;
		}
		return rep;
	}
	
	public Set<Edge> genEdgesOneChild(int numberOfEdges) {
		Set<Edge> edges = new TreeSet<Edge>();
		for (int i = 1; i <= numberOfEdges; i++) {
			edges.add(new Edge(zeroEdges, genMultLabels(i)));
		}
		return edges;
	}
	
	public Set<Edge> genEdgesMultChild(int numberOfEdges){
		Set<Edge> edges = new TreeSet<Edge>();
		for (int i = 1; i <= numberOfEdges; i++) {
			edges.add(new Edge(new GraphNode("node" + i), genMultLabels(i)));
		}
		return edges;
	}
	
	
	public List<TreeSet<String>> genLabels(int numberOfLabels) {
		List<TreeSet<String>> labels = new ArrayList<TreeSet<String>>();
		for (int i = 1; i <= numberOfLabels; i++) {
			labels.add((TreeSet<String>) (genMultLabels(i)));
		}
		return labels;
	}
	
	public List<String> genStrings(int numberOfLabels) {
		List<String> strings = new ArrayList<String>();
		for (int i = 1; i <= numberOfLabels; i++) {
			strings.add(genToString(i));
		}
		return strings;
	}
	/*
	public Graph genGraphOneChild(GraphNode child) {
		Graph test = new Graph();
		test.addNode(child);
		for (int i = 0; i < 10; i++) {
			GraphNode curr = new GraphNode("node" + i);
			test.addNode(curr);
			assert test.isNode(curr);
			Edge edge = new Edge(child, genMultLabels(10));
			test.addEdge(curr, edge);
			assert curr.isParent();
		}
		return test;
	}
	
	public void makeParent(Graph graph, GraphNode parent) {
		for (GraphNode node : graph) {
			if (!node.equals(parent)) {
				graph.addEdge(parent, new Edge(node, "mychildnow"));
				assert parent.isChild(node);
			}
		}
		assert parent.isParent();
		//return graph;
	}
	*/
}
