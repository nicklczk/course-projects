package hw5tobeupdated;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hw5.Edge;
import hw5.Graph;

public class GraphTest {
	/*
	private TestCaseGenerator caseGenerator = new TestCaseGenerator();
	
	@Test
	public void testZeroArgConstructor() {
		Graph test = new Graph();
	}
	
	// Cases
	// 1. GraphNode is not a parent
	// 2. GraphNode is a parent (exception)
	@Test
	public void testOneArgConstructor() {
		// Case 1
		Graph valid = new Graph(new GraphNode("not a parent"));
		
		// Case 2
		try {
			Graph invalid = new Graph(new GraphNode("parent", new Edge(new GraphNode("child"), "invalid")));
		} catch (IllegalArgumentException e) {
		}
	}
	
	// Cases
	// 1. GraphNode is not in the graph (false)
	// 2. GraphNode is in the graph (true)
	@Test
	public void testIsNode() {
		// Case 1
		Graph test = new Graph();
		assertFalse(test.isNode(new GraphNode("not in graph")));
		
		// Case 2
		GraphNode inGraph = new GraphNode("in the graph");
		test = new Graph(inGraph);
		assertTrue(test.isNode(inGraph));
	}
	
	/*
	// Cases
	// 1. GraphNode is a node in the graph (true)
	//		1a. It is not a children of any of the other nodes
	//		1b. It is a children of other nodes in the graph, and hence
	//			must be removed as a child
	// 2. GraphNode is not a node in the graph (false)
	@Test
	public void testRemoveNode() {
		// Case 1a
		GraphNode notAChild = new GraphNode("not a child");
		Graph test = new Graph(notAChild);
		assertTrue(test.removeNode(notAChild));
		assertFalse(test.isNode(notAChild));
		
		// Case 1b
		GraphNode child = new GraphNode("child");
		test = caseGenerator.genGraphOneChild(child);
		assertTrue(test.isNode(child));
		assertTrue(test.removeNode(child));
		assertFalse(test.isNode(child));
		Set<String> nodes = test.getNodes();
		for (String node : nodes) {
			assertFalse(test.copyNode(node).isChild(child));
		}
		
		// Case 2
		test = new Graph();
		assertFalse(test.removeNode(notAChild));
	}
	
	// Cases
	// 1. GraphNode is not a node in the graph (false)
	// 2. GraphNode is a node in the graph
	//		a. it has no outgoing edges (true)
	//		b. it has outgoing edges (true)
	/*
	@Test
	public void testRemoveOutgoingEdges() {
		
		// Case 1
		GraphNode notNode = new GraphNode("not node");
		Graph test = new Graph();
		assertFalse(test.removeOutgoingEdges(notNode));
		
		// Case 2a
		GraphNode isNode = new GraphNode("is node");
		test = caseGenerator.genGraphOneChild(isNode);
		assertTrue(test.removeOutgoingEdges(isNode));
		assertFalse(isNode.isParent());
		
		
		// Case 2b
		Graph test;
		GraphNode isParent = new GraphNode("is parent");
		test = caseGenerator.genGraphOneChild(isParent);
		caseGenerator.makeParent(test, isParent);
		assertTrue(test.isNode(isParent));
		assertTrue(isParent.isParent());
	    assertTrue(test.removeOutgoingEdges(isParent));
		assertFalse(isParent.isParent());
		for (GraphNode node : test) {
			assertFalse(isParent.isChild(node));
		}
	}
	*/
	
	/*
	// Cases
	// 1. GraphNode is a parent so exception thrown
	// 2. GraphNode is not a parent and is in the graph (false)
	// 3. GraphNode is not a parent and is not in the graph (true)
	@Test
	public void testAddNode() {
		// Case 2 
		Graph test = new Graph(new GraphNode("test"));
		assertFalse(test.addNode(new GraphNode("test")));
		
		// Case 3
		test = new Graph();
		assertTrue(test.addNode(new GraphNode("test")));
	}
	
	// Cases
	// 1. Parent is not in the graph (exception)
	// 2. Child is not in the graph (exception)
	// 3. The child is a parent (exception)
	// 4. Edge is added 
	@Test
	public void testAddEdgeNode() {
		Graph test = new Graph();
		GraphNode parent = new GraphNode("parent");
		GraphNode child = new GraphNode("child");
		test.addNode(parent);
		test.addNode(child);
		test.addEdge(parent, new Edge(child, "relationship"));
		assertTrue(parent.isParent());
		assertTrue(parent.isChild(child));
	}
	
	// Cases
	// 1. Parent is not in the graph (exception)
	// 2. Child is not in the graph (exception)
	// 3. Edge is added 
	@Test
	public void testAddEdgeString() {
		Graph test = new Graph();
		GraphNode parent = new GraphNode("parent");
		GraphNode child = new GraphNode("child");
		test.addNode(parent);
		test.addNode(child);
		test.addEdge("parent", "child", "edgeLabel");
		assertTrue(parent.isParent());
		assertTrue(parent.isChild(child));
	}
	*/
}