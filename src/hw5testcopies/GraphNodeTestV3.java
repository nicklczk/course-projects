package hw5.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import hw5.Edge;
import hw5.GraphNode;

public class GraphNodeTestV3 {
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	private Set<Edge> edges = caseGenerator.genEdgesMultChild(4);
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
	
	// Cases
	// 1. 
	@Test
	public void testEquals() {
		assertTrue(zeroTokenID.equals(zeroTokenID));
		assertTrue(multChildren.equals(multChildren));
		assertFalse(multChildren.equals(new GraphNode("multChildren")));
		assertFalse(multChildren.equals(new GraphNode("multChildren", caseGenerator.genEdgesMultChild(3))));
	}
	
	@Test
	public void testGetID() {
		assertEquals("", zeroTokenID.getID());
		assertEquals("0", oneTokenID.getID());
		assertEquals("0 1 2", multTokenID.getID());
	}
	
	
	@Test
	public void testGetAllEdgesEmpty() {
		Set<Edge> expected = new HashSet<Edge>();
		Set<Edge> actual = zeroTokenID.getAllEdges();
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testGetAllEdgesNonEmpty() {
		Set<Edge> actual = multChildren.getAllEdges();
		Set<Edge> expected = edges;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetEdge() {
		Edge expected = new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(4));
		Edge actual = multChildren.getEdge(new GraphNode("node4"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIsChild() {
		for (int i = 1, size = multChildren.getAllEdges().size(); i <= size; i++) {
			assertTrue(multChildren.isChild(new GraphNode("node"+ i)));
		}
		assertFalse(multChildren.isChild(new GraphNode("nonexistent")));
	}
	
	@Test
	public void testRemoveChild() {
		GraphNode expected = new GraphNode("test", caseGenerator.genEdgesMultChild(3));
		GraphNode actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.removeChild(new GraphNode("node4"));
		assertEquals(expected, actual);
		
		Set<Edge> expectedList = caseGenerator.genEdgesMultChild(4);
		expectedList.remove(new Edge(new GraphNode("node2"), caseGenerator.genMultLabels(2)));
		expected = new GraphNode("test", expectedList);
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.removeChild(new GraphNode("node2"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRemoveAllEdges() {
		GraphNode expected = new GraphNode(multChildren.getID());
		GraphNode actual = new GraphNode(multChildren.getID(), edges);
		actual.removeAllEdges();
		assertEquals(expected, actual);
	}
	
	// Cases
	// 1. When you add a completely new child
	// 		a. One label
	//		b. Mult labels
	// 2. When you add an edge for an existing child
	//		a. One new label
	//			i. new label
	//			ii. existing label so nothing happens
	//		b. Mult new labels
	//			i. all new labels
	//			ii. some existing labels so nothing happens to those ones
	@Test
	public void testAddEdge() {
		// 1a
		GraphNode expected =  new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		GraphNode actual = new GraphNode("test", caseGenerator.genEdgesMultChild(3));
		actual.addEdge(new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(4)));
		assertEquals(expected, actual);
		
		// 1b
		Set<Edge> expectedSet = caseGenerator.genEdgesMultChild(4);
		expectedSet.add(new Edge(new GraphNode("node5"), "node5"));
		expected = new GraphNode("test", expectedSet);
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.addEdge(new Edge(new GraphNode("node5"), "node5"));
		assertEquals(expected, actual);
		
		// 2a.i
		expectedSet = caseGenerator.genEdgesMultChild(4);
		Edge expectedEdge = new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(4));
		assertTrue(expectedSet.contains(expectedEdge));
		
		expectedSet.remove(expectedEdge);
		assertFalse(expectedSet.contains(expectedEdge));
		
		expectedEdge.addLabel("onenewlabel");
		assertFalse(expectedEdge.equals(new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(4))));
		assertTrue(expectedEdge.getLabels().contains("onenewlabel"));
		
		expectedSet.add(expectedEdge);
		assertTrue(expectedSet.contains(expectedEdge));
		
		expected = new GraphNode("test", expectedSet);
		assertTrue(expected.getAllEdges().contains(expectedEdge));
		
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		assertFalse(actual.getAllEdges().contains(expectedEdge));
		assertFalse(actual.getAllEdges().equals(expected.getAllEdges()));
		
		actual.addEdge(new Edge(new GraphNode("node4"), "onenewlabel"));
		assertEquals(expected, actual);
		
		// 2a.ii
		expected = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		expectedEdge = new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(4));
		assertTrue(expected.getAllEdges().contains(expectedEdge) && 
				   actual.getAllEdges().contains(expectedEdge));
		actual.addEdge(expectedEdge);
		assertEquals(expected, actual);
		
		// 2b.i
		Set<String> expectedLabels = caseGenerator.genMultLabels(8);
		expectedEdge = new Edge(new GraphNode("node4"), expectedLabels);
		expected = new GraphNode("test", caseGenerator.genEdgesMultChild(3));
		expected.addEdge(expectedEdge);
		assertTrue(expected.getAllEdges().contains(expectedEdge));
		
		expectedLabels = new TreeSet<String>();
		for (int i = 4; i < 8; i++) {
			expectedLabels.add("label" + i);
		}
		expectedEdge = new Edge(new GraphNode("node4"), expectedLabels);
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.addEdge(expectedEdge);
		assertEquals(expected, actual);
		
		// 2b.ii
		expectedLabels = caseGenerator.genMultLabels(5);
		expectedEdge = new Edge(new GraphNode("node4"), expectedLabels);
		expected = new GraphNode("test", caseGenerator.genEdgesMultChild(3));
		expected.addEdge(expectedEdge);
		assertTrue(expected.getAllEdges().contains(expectedEdge));
		
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.addEdge(expectedEdge);
		assertEquals(expected, actual);
	}
	
	// Cases
	// 1. Label exists so it is removed
	// 2. Label does not exist so nothing changes
	@Test
	public void testRemoveEdge() {
		// 1
		GraphNode expected = new GraphNode("test", caseGenerator.genEdgesMultChild(3));
		Edge expectedEdge = new Edge(new GraphNode("node4"), caseGenerator.genMultLabels(3));
		expected.addEdge(expectedEdge);
		GraphNode actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.removeEdge(new GraphNode("node4"), "label3");
		assertEquals(expected, actual);
		
		// 2
		expected = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual = new GraphNode("test", caseGenerator.genEdgesMultChild(4));
		actual.removeEdge(new GraphNode("node4"), "label4");
		assertEquals(expected, actual);		
	}
	
	@Test
	public void testCompareTo() {
		assertEquals(0, zeroTokenID.compareTo(zeroTokenID));
		assertEquals(0, oneTokenID.compareTo(oneTokenID));
		assertEquals(0, multTokenID.compareTo(multTokenID));
		assertTrue(zeroTokenID.compareTo(oneTokenID) < 0);
		assertTrue(oneTokenID.compareTo(zeroTokenID) > 0);
		assertTrue(oneTokenID.compareTo(multTokenID) < 0);
		assertTrue(multTokenID.compareTo(oneTokenID) > 0);
	}
	
	@Test
	public void testToString() {
		String expected = "Parent: ";
		String actual = zeroTokenID.toString();
		assertEquals(expected, actual);
		expected = "Parent: 0";
		actual = oneTokenID.toString();
		assertEquals(expected, actual);
		expected = "Parent: 0 1 2";
		actual = multTokenID.toString();
		assertEquals(expected, actual);
		List<String> expectedList = caseGenerator.genStrings(4);
		expected = "Parent: multChildren";
		for (String string : expectedList) {
			expected += "\n" + string;
		}
		actual = multChildren.toString();
		assertEquals(expected, actual);
	}
}

