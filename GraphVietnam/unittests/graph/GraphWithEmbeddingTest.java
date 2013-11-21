package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GraphWithEmbeddingTest {

	static GraphWithEmbedding<String, Graph.Edge<String>> eg;
	static Graph<String, Graph.Edge<String>> g;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";
	static Graph.Edge<String> E1;// (ONE, TWO)
	static Graph.Edge<String> E2;// (TWO, THREE)

	@Before
	public void setUp() throws Exception {
		// Graph ({1, 2, 3, 4}, {(1,2), (1,3), (2,1), (2,2), (3,2)})

		String[] vertices = { ONE, TWO, THREE, FOUR };
		String[][] edges = { { ONE, THREE }, { TWO, ONE }, { TWO, TWO },
				{ THREE, TWO } };
		eg = new GraphWithEmbedding<String, Graph.Edge<String>>();
		g = eg.graph();
		for (String v : vertices)
			g.addVertex(v);
		E1 = new DirectedEdge<String>(ONE, TWO);
		E2 = new DirectedEdge<String>(TWO, THREE);
		g.addEdge(E1);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
	}

	@Test
	public void testGraphWithEmbedding() {
		assertNotNull(new GraphWithEmbedding<String, Graph.Edge<String>>());
	}

	@Test
	public void testGraph() {
		Graph<String, Graph.Edge<String>> mygraph = eg.graph();
		assertNotNull(mygraph);
		mygraph.removeAllVertices(mygraph.vertices());
		assertEquals(mygraph.vertices().size(), 0);
		assertEquals(mygraph.edges().size(), 0);
	}

	@Test
	public void testIncidentEdges() {
		List<Graph.Edge<String>> ie = eg.incidentEdges(ONE);
		assertEquals(3, ie.size());
		ArrayList<Graph.Edge<String>> expectedOrder = new ArrayList<Graph.Edge<String>>();
		expectedOrder.add(E1);
		expectedOrder.add(new DirectedEdge<String>(ONE, THREE));
		expectedOrder.add(new DirectedEdge<String>(TWO, ONE));
		for (int i = 0; i < ie.size(); i++)
			assertEquals(ie.get(i).toString(), expectedOrder.get(i).toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncidentEdges2() {
		eg.incidentEdges("toto");
	}

	@Test
	public void testSwapIncidentEdges() {
		List<Graph.Edge<String>> expectedIe = new ArrayList<Graph.Edge<String>>(
				eg.incidentEdges(ONE));
		eg.swapIncidentEdges(ONE, 1, 2);
		assertEquals(eg.incidentEdges(ONE).get(2).toString(), expectedIe.get(1)
				.toString());
		assertEquals(eg.incidentEdges(ONE).get(1).toString(), expectedIe.get(2)
				.toString());

	}

	@Test
	public void testRemoveVertex() {

		assertTrue(g.addVertex("six"));
		assertTrue(g.removeVertex("six"));
		Set<String> v = g.vertices();
		assertFalse(v.contains(FIVE));

	}

	@Test
	public void testRemoveEdge() {

		assertTrue(g.addVertex("six"));
		assertTrue(g.addVertex("seven"));
		Graph.Edge<String> e = new DirectedEdge<String>("six", "seven");
		assertTrue(g.addEdge(e));
		assertTrue(g.removeEdge(e));
		Set<Graph.Edge<String>> edges = g.edges();
		assertFalse(edges.contains(e));

	}

}
