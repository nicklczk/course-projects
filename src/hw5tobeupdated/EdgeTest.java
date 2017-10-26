
package hw5tobeupdated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import hw5.Edge;
import hw5tobeupdated.TestCaseGenerator;

public class EdgeTest {
	/*
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	private GraphNode zeroEdges = new GraphNode("zeroEdges");
	private List<TreeSet<String>> labels = caseGenerator.genLabels(4);
	private Set<Edge> edges = caseGenerator.genEdgesOneChild(4);
	private Set<Edge> edgesMultChild = caseGenerator.genEdgesMultChild(4);
	private List<String> strings = caseGenerator.genStrings(4);
	private Set<String> twoLabels = caseGenerator.genMultLabels(2);
	private Set<String> threeLabels = caseGenerator.genMultLabels(3);
	private Set<String> fourLabels = caseGenerator.genMultLabels(4);
	private Edge edgeTwoLabels = new Edge(new GraphNode("node2"), twoLabels);
	private Edge edgeThreeLabels = new Edge(new GraphNode("node3"), threeLabels);
	private Edge edgeFourLabels = new Edge(new GraphNode("node4"), fourLabels);
	private GraphNode oneChildThreeLabels = new GraphNode("oneChildThreeLabels", edgeThreeLabels);
	private Edge zzz = new Edge(new GraphNode("zzzzzzzzzzzzzzzzz"), twoLabels);
	private Edge xxx = new Edge(new GraphNode("xxx"), twoLabels);

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
		HashSet<String> expected = new HashSet<String>();
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
		assertEquals("node3", edgeThreeLabels.getChild().getID());
		Set<Edge> expected = new HashSet<Edge>();
		assertEquals(expected, edgeThreeLabels.getChild().getAllEdges());
	}
	
	@Test
	public void testGetChildMultEdges() {
		Edge actual = new Edge(oneChildThreeLabels, "testOneChildMultLabels");
		assertEquals("oneChildThreeLabels", actual.getChild().getID());
		Set<Edge> expected = new HashSet<Edge>();
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
	public void testAddAndRemoveLabel() {
		edgeThreeLabels.addLabel("label3");
		assertEquals(fourLabels, edgeThreeLabels.getLabels());
		edgeThreeLabels.removeLabel("label3");
		assertEquals(threeLabels, edgeThreeLabels.getLabels());
	}
	
	@Test
	public void testCompareTo() {
		for (Edge edge : edges) {
			assertEquals(0, edge.compareTo(edge));
			assertTrue(edge.compareTo(zzz) < 0);
			assertTrue(edge.compareTo(xxx) > 0);
		}
	}
	
	@Test
	public void testEquals() {
		assertTrue(edgeTwoLabels.equals(new Edge(new GraphNode("node2"), twoLabels)));
		assertTrue(edgeThreeLabels.equals(new Edge(new GraphNode("node3"), threeLabels)));
		assertTrue(edgeFourLabels.equals(new Edge(new GraphNode("node4"), fourLabels)));
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
	*/
}
