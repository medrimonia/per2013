package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import collections.Iterables;

public class MultiGraphTest {

	static Graph<String, Graph.Edge<String>> g;
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
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		E1 = new DirectedEdge<String>(ONE, TWO);
		E2 = new DirectedEdge<String>(TWO, THREE);
		g.addEdge(E1);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
	}
	
	@Test
	public void testAddEdge() {
		assertTrue(g.addEdge(E2));
		assertFalse(g.addEdge(E2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddEdge2() {
		g.addEdge(new DirectedEdge<String>(ONE, FIVE));
	}

	@Test
	public void testAddVertex() {
		assertTrue(g.addVertex(FIVE));
		assertFalse(g.addVertex(ONE));
	}

	@Test
	public void testAreNeighbors() {
		assertTrue(g.areNeighbors(ONE, TWO));
		assertTrue(g.areNeighbors(THREE, ONE));
		assertFalse(g.areNeighbors(ONE, FOUR));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAreNeighbors2() {
		g.areNeighbors(ONE, FIVE);
	}

	@Test
	public void testClear() {
		g.clear();
		assertFalse(g.containsVertex(ONE));
		assertTrue(g.order() == 0);
		assertTrue(g.size() == 0);
	}

	@Test
	public void testContainsEdge() {
		assertTrue(g.containsEdge(E1));
		assertFalse(g.containsEdge(E2));
	}

	@Test
	public void testContainsVertex() {
		assertTrue(g.containsVertex(ONE));
		assertFalse(g.containsVertex(FIVE));
	}

	@Test
	public void testDegree() {
		assertTrue(g.degree(ONE) == 3);
		assertTrue(g.degree(FOUR) == 0);
	}

	@Test
	public void testEdges() {
		Set<Graph.Edge<String>> edges = g.edges();
		assertTrue(edges.contains(E1));
		edges.add(E2);
		assertTrue(g.containsEdge(E2));
		Set<Graph.Edge<String>> ec = new HashSet<Graph.Edge<String>>();
		ec.add(E1);
		ec.add(E2);
		assertTrue(edges.containsAll(ec));
		ec.add(new DirectedEdge<String>(ONE, FOUR));
		assertFalse(edges.containsAll(ec));

		Set<Graph.Edge<String>> toAdd = new HashSet<Graph.Edge<String>>();
		toAdd.add(new DirectedEdge<String>(ONE, FOUR));
		toAdd.add(new DirectedEdge<String>(TWO, FOUR));
		toAdd.add(E1);
		edges.addAll(toAdd);
		assertTrue(g.areNeighbors(ONE, FOUR));

		Iterator<Graph.Edge<String>> it = edges.iterator();
		Graph.Edge<String> e = null;
		for (int i = 0; i < g.size(); i++) {
			assertTrue(it.hasNext());
			e = it.next();
			assertTrue(g.containsEdge(e));
		}
		assertFalse(it.hasNext());
		assertFalse(edges.isEmpty());
		edges.clear();
		assertTrue(edges.isEmpty());
		assertTrue(g.size() == 0);
		assertTrue(g.degree(ONE) == 0);
		assertTrue(edges.isEmpty());
	}

	private void clearVertices(Iterable<String> it) {
		List<String> l = new ArrayList<String>();
		Iterables.fillList(l, it);
		g.removeAllVertices(l);
	}

	private void clearEdges(Iterable<Graph.Edge<String>> it) {
		List<Graph.Edge<String>> l = new ArrayList<Graph.Edge<String>>();
		Iterables.fillList(l, it);
		g.removeAllEdges(l);
	}

	@Test
	public void testIncidentEdgesV() {
		clearEdges(g.incidentEdges(TWO));
		assertTrue(g.degree(TWO) == 0);
	}

	@Test
	public void testIncidentEdgesVV() {
		clearEdges(g.incidentEdges(ONE, TWO));
		assertFalse(g.areNeighbors(ONE, TWO));
	}

	@Test
	public void testIncomingEdgesV() {
		clearEdges(g.incomingEdges(ONE));
		assertTrue(g.indegree(ONE) == 0);
	}

	@Test
	public void testIndegree() {
		assertTrue(g.indegree(ONE) == 1);
		assertTrue(g.indegree(TWO) == 3);
		assertTrue(g.indegree(THREE) == 1);
		assertTrue(g.indegree(FOUR) == 0);
	}

	@Test
	public void testIsEmpty() {
		g.vertices().clear();
		assertTrue(g.isEmpty());
	}

	@Test
	public void testNeighbors() {
		for (String neighbor : Iterables.fillList(g.neighbors(TWO)))
			clearEdges(g.incidentEdges(TWO, neighbor));
		assertTrue(g.degree(TWO) == 0);
	}

	@Test
	public void testOrder() {
		assertTrue(g.order() == 4);
	}

	@Test
	public void testOutdegree() {
		assertTrue(g.outdegree(ONE) == 2);
		assertTrue(g.outdegree(TWO) == 2);
		assertTrue(g.outdegree(THREE) == 1);
		assertTrue(g.outdegree(FOUR) == 0);
	}

	@Test
	public void testOutgoingEdgesV() {
		clearEdges(g.outgoingEdges(ONE));
		assertTrue(g.outdegree(ONE) == 0);
	}

	@Test
	public void testOutgoingEdgesVV() {
		clearEdges(g.outgoingEdges(ONE, TWO));
		assertFalse(g.areNeighbors(ONE, TWO));
	}

	@Test
	public void testPredecessors() {
		for (String pred : Iterables.fillList(g.predecessors(TWO)))
			clearEdges(g.outgoingEdges(pred, TWO));
		assertTrue(g.indegree(TWO) == 0);
	}

	@Test
	public void testRemoveAllEdges() {
		List<Graph.Edge<String>> ce = new ArrayList<Graph.Edge<String>>();
		Iterables.fillList(ce, g.incidentEdges(TWO));
		g.removeAllEdges(ce);
		assertTrue(g.degree(TWO) == 0);
	}

	@Test
	public void testRemoveAllVertices() {
		Collection<String> cv = new ArrayList<String>(Arrays.asList(ONE, THREE,
				FOUR));
		g.removeAllVertices(cv);
		assertTrue(g.order() == 1);
	}

	@Test
	public void testRemoveEdge() {
		g.removeEdge(E1);
		assertFalse(g.areNeighbors(ONE, TWO));
		assertFalse(g.containsEdge(E1));
	}

	@Test
	public void testRemoveVertex() {
		g.removeVertex(ONE);
		assertFalse(g.containsVertex(ONE));
		assertTrue(g.order() == 3);
		assertTrue(g.size() == 2);
	}

	@Test
	public void testSize() {
		assertTrue(g.size() == 5);
	}

	@Test
	public void testSuccessors() {
		clearVertices(g.successors(ONE));
		assertTrue(g.outdegree(ONE) == 0);
	}

	@Test
	public void testVertices() {
		g.vertices().remove(TWO);
		assertFalse(g.containsVertex(TWO));
		assertTrue(g.order() == 3);
		assertTrue(g.size() == 1);
	}

	@Test
	public void testToString() {
		MultiGraph<String, Graph.Edge<String>> g2 = new MultiGraph<String, Graph.Edge<String>>();
		g2.addVertex("one");
		g2.addVertex("two");
		g2.addEdge(new DirectedEdge<String>("one", "two"));
		String s = "2 vertices + 1 edges" + "\n" + "two : " + "\n"
				+ "one : (one, two) " + "\n";
		assertEquals(g2.toString(), s);
	}

	@Test
	public void testEquals() {

		MultiGraph<String, Graph.Edge<String>> g2 = new MultiGraph<String, Graph.Edge<String>>();
		DirectedEdge<String> myEdge = new DirectedEdge<String>("one", "two");
		g2.addVertex("one");
		g2.addVertex("two");
		g2.addEdge(myEdge);
		assertEquals(g2, g2);

		MultiGraph<String, Graph.Edge<String>> g3 = new MultiGraph<String, Graph.Edge<String>>();
		g3.addVertex("one");
		g3.addVertex("two");
		g3.addEdge(myEdge);

		assertEquals(g2, g3);
	}

	@Test
	public void testPartialGraph() {
		assertTrue(g.equals(g.partialGraph(g.edges())));
	}

	@Test
	public void testInducedSubgraph() {
		assertTrue(g.equals(g.inducedSubgraph(g.vertices())));
	}

	@Test
	public void testSubgraph() {
		assertTrue(g.equals(g.subgraph(g.vertices(), g.edges())));
	}
}
