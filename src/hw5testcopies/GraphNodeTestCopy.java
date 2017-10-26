package hw5testcopies;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hw5.Edge;
import hw5.GraphNode;
import hw5tobeupdated.TestCaseGenerator;

public class GraphNodeTestCopy {
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	private GraphNode zeroTokenID = new GraphNode("");
	private GraphNode oneTokenID = new GraphNode("0");
	private GraphNode multTokenID = new GraphNode("0 1 2");
	private GraphNode multChildren = caseGenerator.constructChildMultEdges();
	
	@Test
	public void testOneArgConstructor() {
		new GraphNode("");
		new GraphNode("0");
		new GraphNode("0 1 2");
	}
	
	@Test
	public void testTwoArgConstructor() {
		new GraphNode("emptyList", new ArrayList<Edge>());
		new GraphNode("nonEmptyList", caseGenerator.constructMultChildList());
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
		List<Edge> expected = caseGenerator.constructMultChildList();
		List<Edge> actual = multChildren.getAllEdges();
		for (int i = 0, size = expected.size(); i < size; i++) {
			assertEquals(expected.get(i).toString(), actual.get(i).toString());
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
		for (int i = 0; i < 3; i++) {
			expected.add(new GraphNode("edge" + i));
		}
		List<GraphNode> actual = multChildren.getChildren();
		assertEquals(expected.toString(), actual.toString());
	}
	
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
		actual.add(e);
	}
}

