package hw5testcopies;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hw5.Edge;
import hw5.GraphNode;
import hw5tobeupdated.TestCaseGenerator;

public class GraphNodeTestCopyV2 {
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	private List<Edge> edges = caseGenerator.genEdgesMultChild(4);
	private GraphNode zeroTokenID = new GraphNode("");
	private GraphNode oneTokenID = new GraphNode("0");
	private GraphNode multTokenID = new GraphNode("0 1 2");
	private GraphNode multChildren = new GraphNode("multChildren", edges);
	
	@Test
	public void testOneArgConstructor() {
		new GraphNode("");
		new GraphNode("0");
		new GraphNode("0 1 2");
	}
	
	@Test
	public void testTwoArgConstructorEdges() {
		for (Edge edge : edges) {
			new GraphNode("test", edge);
		}
	}
	
	@Test
	public void testTwoArgConstructorListEdges() {
		new GraphNode("test", edges);
	}

	@Test
	public void testGetID() {
		assertEquals("", zeroTokenID.getID());
		assertEquals("0", oneTokenID.getID());
		assertEquals("0 1 2", multTokenID.getID());
	}
	
	
	@Test
	public void testGetAllEdgesEmpty() {
		List<Edge> expected = new ArrayList<Edge>();
		List<Edge> actual = zeroTokenID.getAllEdges();
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testGetAllEdgesNonEmpty() {
		List<Edge> actual = multChildren.getAllEdges();
		for (int i = 0, size = actual.size(); i < size; i++) {
			assertEquals(edges.get(i).toString(), actual.get(i).toString());
		}
	}
	
	
	@Test
	public void testGetChildrenNone() {
		List<GraphNode> expected = new ArrayList<GraphNode>();
		List<GraphNode> actual = zeroTokenID.getChildren();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetChildrenMultiple() {
		List<GraphNode> expected = new ArrayList<GraphNode>();
		for (int i = 1; i <= 4; i++) {
			expected.add(new GraphNode("node" + i));
		}
		List<GraphNode> actual = multChildren.getChildren();
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void testIndexOf() {
		List<GraphNode> children = multChildren.getChildren();
		for (int i = 0, size = children.size(); i < size; i++) {
			assertEquals(i, multChildren.indexOfChild(children.get(i).getID()));
		}
	}
	
	@Test
	public void testGetEdge() {
		assertEquals(null, multChildren.getEdge("nonexistent"));
		Edge expected = new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(4));
		Edge actual = multChildren.getEdge("node4");
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void testIsChild() {
		for (int i = 1, size = multChildren.getChildren().size(); i <= size; i++) {
			assertTrue(multChildren.isChild("node" + i));
		}
		assertFalse(multChildren.isChild("nonexistent"));
	}
	
	@Test
	public void testRemoveChid() {
		GraphNode expected = new GraphNode("test", caseGenerator.genEdgesMultChild(3));
		GraphNode actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual = actual.removeChild("node4");
		assertEquals(expected.toString(), actual.toString());
		
		List<Edge> expectedList = caseGenerator.genEdgesMultChild(4);
		expectedList.remove(1);
		expected = new GraphNode("test", expectedList);
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual = actual.removeChild("node2");
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void testRemoveAllEdges() {
		GraphNode expected = new GraphNode(multChildren.getID());
		GraphNode actual = multChildren.removeAllEdges();
		assertEquals(expected.toString(), actual.toString());
	}
	
	/*
	@Test
	public void testGetConnectingEdgesZeroEdges() {
		try {
			zeroTokenID.getConnectingEdge(oneTokenID.getID());
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetConnectingEdgesOneEdge() {
		// One edge shared
		List<Edge> expected = new ArrayList<Edge>();
		expected.add(new Edge(new GraphNode("edge0"), "label for edge0"));
		List<Edge> actual = multChildren.getConnectingEdge("edge0");
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void testGetConnectingEdgesMultEdges() {
		List<Edge> actual = caseGenerator.constructMultChildList();
		actual.add(new Edge(new GraphNode("edge2"), "second label for edge2"));
		actual.add(e)
	}
	*/
}
