package graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import graph.Graph.Edge;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class InducedSubMultiGraphTest {

	static Graph<String, Graph.Edge<String>> g;
	static InducedSubgraph<String, Graph.Edge<String>> isg;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";
	static final String SIX = "six";
	static final String SEVEN = "seven";
	static Graph.Edge<String> E1; // (ONE, TWO)
	static Graph.Edge<String> E2; // (TWO, THREE)

	@Before
	public void setUp() throws Exception {
		// Graph ({1, 2, 3, 4}, {(1,2), (1,3), (2,1), (2,2), (3,2)})

		String[] verticesG = { ONE, TWO, THREE, FOUR, FIVE, SIX };
		String[][] edgesG = { { ONE, THREE }, { TWO, ONE }, { TWO, TWO },
				{ THREE, TWO }, { SIX, TWO }, { FIVE, THREE }, { ONE, FIVE } };
		Set<String> vertices = new HashSet<String>();
		vertices.add(ONE);
		vertices.add(TWO);
		vertices.add(THREE);
		vertices.add(FOUR);

		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : verticesG)
			g.addVertex(v);
		E1 = new DirectedEdge<String>(ONE, TWO);
		E2 = new DirectedEdge<String>(TWO, THREE);
		g.addEdge(E1);
		for (String[] e : edgesG)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
		isg = g.inducedSubgraph(vertices);
	}

	@Test
	public void testInducedSubgraphImpl() {
		Graph<String, Graph.Edge<String>> sG = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : g.vertices())
			sG.addVertex(v);
		for (Edge<String> e : g.edges())
			sG.addEdge(e);
		sG.removeVertex(FIVE);
		sG.removeVertex(SIX);
		assertTrue(sG.equals(isg));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdge() {
		isg.addEdge(E2);
	}

	@Test
	public void testAddVertexFromSuperGraph() {
		isg.addVertex(FIVE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddVertexNotFromSuperGraph() {
		assertTrue(isg.addVertex(SEVEN));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAllEdges() {
		isg.removeAllEdges(g.edges());
	}

	@Test
	public void testRemoveAllVertices() {
		assertTrue(isg.removeAllVertices(isg.vertices()));
		assertTrue(isg.vertices().isEmpty());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveEdge() {
		isg.removeEdge(E1);
	}

	@Test
	public void testRemoveVertex() {
		assertTrue(isg.removeVertex(ONE));
		assertFalse(isg.containsVertex(ONE));
	}

	@Test
	public void testAreNeighbors() {
		assertTrue(isg.areNeighbors(ONE, TWO));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear() {
		isg.clear();
	}

	@Test
	public void testContainsEdge() {
		assertTrue(isg.containsEdge(E1));
		assertFalse(isg.containsEdge(E2));
	}

	@Test
	public void testContainsVertex() {
		assertTrue(isg.containsVertex(ONE));
		assertFalse(isg.containsVertex(FIVE));
	}

	@Test
	public void testDegree() {
		assertTrue(isg.degree(ONE) == 3);
		assertTrue(isg.degree(FOUR) == 0);
	}

	@Test
	public void testEdges() {
		Graph<String, Graph.Edge<String>> sG = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : g.vertices())
			sG.addVertex(v);
		for (Edge<String> e : g.edges())
			sG.addEdge(e);
		sG.removeVertex(FIVE);
		sG.removeVertex(SIX);
		assertFalse(isg.edges().isEmpty());
		assertTrue(sG.edges().equals(isg.edges()));
	}

	private void clearVertices(Iterable<String> it) {
		List<String> l = new ArrayList<String>();
		for (String s : it)
			l.add(s);
		isg.removeAllVertices(l);
	}

	@Test
	public void testIncidentEdgesV() {
		clearVertices(isg.neighbors(ONE));
		assertTrue(isg.degree(ONE) == 0);
	}

	@Test
	public void testIncomingEdges() {
		clearVertices(isg.predecessors(ONE));
		assertTrue(isg.indegree(ONE) == 0);
	}

	@Test
	public void testIndegree() {
		assertTrue(isg.indegree(ONE) == 1);
		assertTrue(isg.indegree(TWO) == 3);
		assertTrue(isg.indegree(THREE) == 1);
		assertTrue(isg.indegree(FOUR) == 0);
	}

	@Test
	public void testIsEmpty() {
		assertFalse(isg.isEmpty());
	}

	@Test
	public void testOrder() {
		assertTrue(isg.order() == 4);
	}

	@Test
	public void testOutdegree() {
		assertTrue(isg.outdegree(ONE) == 2);
		assertTrue(isg.outdegree(TWO) == 2);
		assertTrue(isg.outdegree(THREE) == 1);
		assertTrue(isg.outdegree(FOUR) == 0);
	}

	@Test
	public void testOutgoingEdgesV() {
		clearVertices(isg.successors(ONE));
		assertTrue(isg.outdegree(ONE) == 0);
	}

	@Test
	public void testSize() {
		assertTrue(isg.size() == 5);
	}

	@Test
	public void testSuccessors() {
		clearVertices(isg.successors(ONE));
		assertTrue(isg.outdegree(ONE) == 0);
	}

	@Test
	public void testVertices() {
		assertTrue(isg.containsVertex(TWO));
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testTestVersion() {
		g.addEdge(E2);
		isg.containsEdge(E1);

	}
}