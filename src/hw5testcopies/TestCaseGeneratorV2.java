package hw5testcopies;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import hw5.Edge;
import hw5.GraphNode;

public class TestCaseGeneratorV2 {
	private GraphNode zeroEdges = new GraphNode("zeroEdges");
	
	public TreeSet<String> genMultLabels(int labels) {
		TreeSet<String> threeLabels = new TreeSet<String>();
		for (int i = 0; i < labels; i++) {
			threeLabels.add("label" + i);
		}
		return threeLabels;
	}
	
	public String genToString(int labels) {
		String rep = "Child: zeroEdges\nLabel(s):";
		for (int i = 0; i < labels; i++) {
			rep += "\nlabel" + i;
		}
		return rep;
	}
	
	public List<Edge> genEdgesOneChild(int numberOfEdges) {
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 1; i <= numberOfEdges; i++) {
			edges.add(new Edge(zeroEdges, genMultLabels(i)));
		}
		return edges;
	}
	
	public List<Edge> genEdgesMultChild(int numberOfEdges){
		List<Edge> edges = new ArrayList<Edge>();
		for (int i = 1; i <= numberOfEdges; i++) {
			edges.add(new Edge(new GraphNode("node" + i), genMultLabels(i)));
		}
		return edges;
	}
	
	public List<TreeSet<String>> genLabels(int numberOfLabels) {
		List<TreeSet<String>> labels = new ArrayList<TreeSet<String>>();
		for (int i = 1; i <= numberOfLabels; i++) {
			labels.add(genMultLabels(i));
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
}
