package hw5testcopies;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import hw5.Edge;
import hw5.GraphNode;
import hw5tobeupdated.TestCaseGenerator;

public class EdgeTestCopyV2 {
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	private GraphNode zeroEdges = new GraphNode("zeroEdges");
	private List<TreeSet<String>> labels = caseGenerator.genLabels(4);
	private List<Edge> edges = caseGenerator.genEdgesOneChild(4);
	private List<String> strings = caseGenerator.genStrings(4);
	private TreeSet<String> twoLabels = caseGenerator.genMultLabels(2);
	private TreeSet<String> threeLabels = caseGenerator.genMultLabels(3);
	private TreeSet<String> fourLabels = caseGenerator.genMultLabels(4);
	private Edge edgeTwoLabels = new Edge(zeroEdges, twoLabels);
	private Edge edgeThreeLabels = new Edge(zeroEdges, threeLabels);
	private Edge edgeFourLabels = new Edge(zeroEdges, fourLabels);
	private GraphNode oneChildThreeLabels = new GraphNode("oneChildThreeLabels", edgeThreeLabels);

	@Test
	public void testConstructorOneEdge() {
		new Edge(zeroEdges, "testChildZeroEdges");
	}
	
	@Test 
	public void testConstructorMultEdges() {
		new Edge(zeroEdges, threeLabels);
	}
	
	@Test
	public void testGetLabelOneLabel() {
		TreeSet<String> expected = new TreeSet<String>();
		expected.add("onelabel");
		Edge actual = new Edge(zeroEdges, "onelabel");
		assertEquals(expected, actual.getLabels());
	}
	
	@Test
	public void testGetLabelsMultLabels() {
		assertEquals(threeLabels, edgeThreeLabels.getLabels());
	}
	
	@Test
	public void testGetChildZeroEdges() {
		assertEquals("zeroEdges", edgeThreeLabels.getChild().getID());
		List<Edge> expected = new ArrayList<Edge>();
		assertEquals(expected, edgeThreeLabels.getChild().getAllEdges());
	}
	
	@Test
	public void testGetChildMultEdges() {
		Edge actual = new Edge(oneChildThreeLabels, "testOneChildMultLabels");
		assertEquals("oneChildThreeLabels", actual.getChild().getID());
		List<Edge> expected = new ArrayList<Edge>();
		expected.add(edgeThreeLabels);
		assertEquals(expected, actual.getChild().getAllEdges());
	}
	
	@Test
	public void testIsLabel() {
		for (int i = 0, length = edgeThreeLabels.getLabels().size(); i < length; i++) {
			assertTrue(edgeThreeLabels.isLabel("label" + i));
		}
		assertFalse(edgeThreeLabels.isLabel("label4"));
	}
	
	@Test
	public void testAddAndRemoveEdge() {
		edgeThreeLabels = edgeThreeLabels.addEdge("label3");
		assertEquals(fourLabels, edgeThreeLabels.getLabels());
		edgeThreeLabels = edgeThreeLabels.removeEdge("label3");
		assertEquals(threeLabels, edgeThreeLabels.getLabels());
	}
	
	@Test
	public void testEquals() {
		assertTrue(edgeTwoLabels.equals(new Edge(zeroEdges, twoLabels)));
		assertTrue(edgeThreeLabels.equals(new Edge(zeroEdges, threeLabels)));
		assertTrue(edgeFourLabels.equals(new Edge(zeroEdges, fourLabels)));
		assertFalse(edgeTwoLabels.equals(edgeThreeLabels));
		assertFalse(edgeTwoLabels.equals(edgeFourLabels));
		assertFalse(edgeTwoLabels.equals("not an edge"));
	}
	
	@Test
	public void testToString() {
		assertEquals(caseGenerator.genToString(2), edgeTwoLabels.toString());
		assertEquals(caseGenerator.genToString(3), edgeThreeLabels.toString());
		assertEquals(caseGenerator.genToString(4), edgeFourLabels.toString());
	}
}

