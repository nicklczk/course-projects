package hw5testcopies;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hw5.Edge;
import hw5.GraphNode;
import hw5tobeupdated.TestCaseGenerator;

public class EdgeTestCopy {
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	private GraphNode zeroEdges = new GraphNode("zeroEdges");
	
	private GraphNode addEdge(GraphNode parent, GraphNode child, String label) {
		parent.addEdge(child, label);
		return parent;
	}
	private GraphNode oneEdge = addEdge(new GraphNode("oneEdge"), zeroEdges, "oneEdge to zeroEdges");
	
	private GraphNode multEdges = caseGenerator.constructChildMultEdges();
	private GraphNode[] cases = {zeroEdges, oneEdge, multEdges};
	

	private List<String> getLabelTestCases() {
		List<String> cases = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			cases.add("" + i);
		}
		return cases;
	}
	
	@Test
	public void testConstructorChildZeroEdges() {
		new Edge(zeroEdges, "testChildZeroEdges");
	}
	
	@Test 
	public void testConstructorChildOneEdge() {
		new Edge(oneEdge, "testChildOneEdge");
	}
	
	@Test
	public void testConstructorChildMultEdges() {
		new Edge(multEdges, "testChildMultEdges");
	}
	
	@Test
	public void testGetLabel() {
		List<String> cases = getLabelTestCases();
		for (String test : cases) {
			Edge curr = new Edge(zeroEdges, test);
			assertEquals(test, curr.getLabel());
		}
	}
	
	@Test
	public void testGetChild() {
		for (int i = 0; i < cases.length; i++) {
			Edge currEdge = new Edge(cases[i], "test" + i);
			GraphNode currNode = currEdge.getChild();
			assertEquals(cases[i].getID(), currNode.getID());
			for (int j = 0, length = cases[i].getAllEdges().size(); j < length; j++) {
				assertEquals(currNode.getAllEdges().get(j), cases[i].getAllEdges().get(j));
			}
		}
	}
	
	@Test
	public void testChangeLabel() {
		String label = "edge";
		for (int i = 0; i < 3; i++) {
			label += " " + i;
			GraphNode node = new GraphNode(label);
			Edge edge = new Edge(node, "label for " + node.getID());
			assertEquals("label for " + node.getID(), edge.getLabel());
		}
	}
	
	@Test
	public void testToString() {
		String childID = "";
		String label = "";
		for (int i = 0; i < 3; i++) {
			childID += "child ";
			label += "label";
			String expected = "Child: " + childID + ", Label: " + label;
			Edge edge = new Edge(new GraphNode(childID), label);
			assertEquals(expected, edge.toString());
		}
	}
}

