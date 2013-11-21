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

import collections.Iterables;

public class PartialMultiGraphTest {

	static Graph<String, Graph.Edge<String>> g;
	static PartialGraph<String, Graph.Edge<String>> pg;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";
	static Graph.Edge<String> E1; // (ONE, TWO)
	static Graph.Edge<String> E2; // (TWO, THREE)

	@Before
	public void setUp() throws Exception {
		// Graph ({1, 2, 3, 4}, {(1,2), (1,3), (2,1), (2,2), (3,2)})

		String[] vertices = { ONE, TWO, THREE, FOUR };
		String[][] edges = { { ONE, THREE }, { TWO, ONE }, { TWO, TWO },
				{ THREE, TWO } };

		Set<Graph.Edge<String>> edgesC = new HashSet<Graph.Edge<String>>();
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		E1 = new DirectedEdge<String>(ONE, TWO);
		E2 = new DirectedEdge<String>(TWO, THREE);
		g.addEdge(E1);
		for (String[] e : edges) {
			DirectedEdge<String> de = new DirectedEdge<String>(e[0], e[1]);
			g.addEdge(de);
			edgesC.add(de);
		}
		pg = g.partialGraph(edgesC);
	}

	@Test
	public void testSpanningSubgraphImpl() {
		assertTrue(pg.vertices().equals(g.vertices()));
	}

	@Test
	public void testAddEdgeFromSuperGraph() {
		pg.removeEdge(E1);
		assertTrue(pg.addEdge(E1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdgeNotFromSuperGraph() {
		pg.addEdge(E2);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddVertex() {
		pg.addVertex(FIVE);
	}

	@Test
	public void testRemoveAllEdges() {
		assertTrue(pg.removeAllEdges(g.edges()));
		assertTrue(pg.edges().isEmpty());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAllVertices() {
		pg.removeAllVertices(g.vertices());
	}

	@Test
	public void testRemoveEdge() {
		pg.addEdge(E1);
		assertTrue(pg.removeEdge(E1));
		assertFalse(pg.containsEdge(E1));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveVertex() {
		pg.removeVertex(ONE);
	}

	@Test
	public void testAreNeighbors() {
		assertTrue(pg.areNeighbors(ONE, TWO));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear() {
		pg.clear();
	}

	@Test
	public void testContainsEdge() {
		pg.addEdge(E1);
		assertTrue(pg.containsEdge(E1));
		assertFalse(pg.containsEdge(E2));
	}

	@Test
	public void testContainsVertex() {
		assertTrue(pg.containsVertex(ONE));
		assertFalse(pg.containsVertex(FIVE));
	}

	@Test
	public void testDegree() {
		assertTrue(pg.degree(ONE) == 2);
		assertTrue(pg.degree(FOUR) == 0);
	}

	@Test
	public void testEdges() {
		assertFalse(pg.edges().isEmpty());
		for (Edge<String> e : pg.edges()) {
			assertTrue(g.containsEdge(e));
		}
	}

	private void clearEdges(Iterable<Graph.Edge<String>> it) {
		List<Graph.Edge<String>> l = new ArrayList<Graph.Edge<String>>();
		Iterables.fillList(l, it);
		pg.removeAllEdges(l);
	}

	@Test
	public void testIncidentEdgesV() {
		clearEdges(pg.incidentEdges(TWO));
		assertTrue(pg.degree(TWO) == 0);
	}

	@Test
	public void testIncidentEdgesVV() {
		clearEdges(pg.incidentEdges(ONE, TWO));
		assertFalse(pg.areNeighbors(ONE, TWO));
	}

	@Test
	public void testIncomingEdges() {
		clearEdges(pg.incomingEdges(ONE));
		assertTrue(pg.indegree(ONE) == 0);
	}

	@Test
	public void testIndegree() {
		assertTrue(pg.indegree(ONE) == 1);
		assertTrue(pg.indegree(TWO) == 2);
		assertTrue(pg.indegree(THREE) == 1);
		assertTrue(pg.indegree(FOUR) == 0);
	}

	@Test
	public void testIsEmpty() {
		assertFalse(pg.isEmpty());
	}

	@Test
	public void testNeighbors() {
		for (String neighbor : Iterables.fillList(pg.neighbors(TWO)))
			clearEdges(pg.incidentEdges(TWO, neighbor));
		assertTrue(pg.degree(TWO) == 0);
	}

	@Test
	public void testOrder() {
		assertTrue(pg.order() == 4);
	}

	@Test
	public void testOutdegree() {
		assertTrue(pg.outdegree(ONE) == 1);
		assertTrue(pg.outdegree(TWO) == 2);
		assertTrue(pg.outdegree(THREE) == 1);
		assertTrue(pg.outdegree(FOUR) == 0);
	}

	@Test
	public void testPredecessors() {
		for (String pred : Iterables.fillList(pg.predecessors(TWO)))
			clearEdges(pg.outgoingEdges(pred, TWO));
		assertTrue(pg.indegree(TWO) == 0);
	}

	@Test
	public void testSize() {
		assertTrue(pg.size() == 4);
	}

	@Test
	public void testVertices() {
		assertTrue(pg.containsVertex(TWO));
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testTestVersion() {
		g.addVertex(FIVE);
		pg.containsVertex(FOUR);
	}
}
