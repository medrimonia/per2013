package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import graph.DirectedEdge;
import graph.Graph;
import graph.InducedSubgraph;
import graph.MultiGraph;
import graph.PartialGraph;
import graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import collections.Iterables;
import collections.Iterators;

public class RootedSpanningTreeImplTest {

	static Graph<Integer, Graph.Edge<Integer>> g;
	static RootedSpanningTreeImpl<Integer, Graph.Edge<Integer>> rst;
	// edge of the tree
	static Graph.Edge<Integer> e02 = new DirectedEdge<Integer>(0, 2);
	// co-edge
	static Graph.Edge<Integer> e23 = new DirectedEdge<Integer>(2, 3);

	@Before
	public void setUp() throws Exception {
		g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		int[][] edges = new int[][] { { 0, 3 }, { 1, 4 }, { 3, 4 }, { 2, 4 },
				{ 0, 5 }, { 1, 6 } };
		for (int i = 0; i < 7; i++)
			g.addVertex(i);
		for (int[] e : edges)
			g.addEdge(new DirectedEdge<Integer>(e[0], e[1]));
		g.addEdge(e23);
		g.addEdge(e02);
		rst = new RootedSpanningTreeImpl<Integer, Graph.Edge<Integer>>(g,
				Graphs.breadthFirstSearch(g, 0), 0);
	}

	@Test
	public void testIsRoot() {
		assertTrue(rst.isRoot(0));
	}

	@Test
	public void testCoedges() {
		assertTrue(Iterables.size(rst.coedges()) == g.size() - g.order() + 1);
	}

	@Test
	public void testConeighbors() {
		assertTrue(Iterables.size(rst.coneighbors(3)) == g.degree(3)
				- rst.degree(3));
	}

	@Test
	public void testIsCoedge() {
		assertTrue(rst.isCoedge(e23));
	}

	@Test
	public void testLevelVertices() {
		assertTrue(Iterators.hasElements(rst.levelVertices(0), 0));
		assertTrue(Iterators.hasElements(rst.levelVertices(1), 2, 3, 5));
		assertTrue(Iterators.hasElements(rst.levelVertices(2), 4));
		assertTrue(Iterators.hasElements(rst.levelVertices(3), 1));
		assertTrue(Iterators.hasElements(rst.levelVertices(4), 6));
	}

	@Test
	public void testNLevels() {
		assertTrue(rst.nLevels() == 5);
	}

	@Test
	public void testVertexLevel() {
		int[] levels = { 0, 3, 1, 1, 2, 1, 4 };
		for (int v = 0; v < 7; v++) {
			assertTrue(rst.vertexLevel(v) == levels[v]);
		}
	}

	@Test
	public void testChildren() {
		assertTrue(Iterables.hasElements(rst.children(0), 2, 3, 5));
		assertTrue(Iterables.hasElements(rst.children(4), 1));
		assertTrue(Iterables.hasElements(rst.children(6)));
	}

	@Test
	public void testFather() {
		assertTrue(rst.father(0) == null);
		assertTrue(rst.father(1) == 4);
		assertTrue(rst.father(2) == 0);
		assertTrue(rst.father(3) == 0);
		assertTrue(rst.father(4) == 2 || rst.father(4) == 3);
		assertTrue(rst.father(5) == 0);
		assertTrue(rst.father(6) == 1);
	}

	@Test
	public void testNChildren() {
		assertTrue(rst.nChildren(0) == 3);
		assertTrue(rst.nChildren(1) == 1);
		assertTrue(rst.nChildren(2) + rst.nChildren(3) == 1);
		assertTrue(rst.nChildren(4) == 1);
		assertTrue(rst.nChildren(5) == 0);
		assertTrue(rst.nChildren(6) == 0);
	}

	@Test
	public void testRoot() {
		assertTrue(rst.root() == 0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdge() {
		rst.addEdge(new DirectedEdge<Integer>(4, 5));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddVertex() {
		rst.addVertex(7);
	}

	@Test
	public void testAreNeighbors() {
		assertTrue(rst.areNeighbors(0, 2));
		assertTrue(rst.areNeighbors(1, 4));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear() {
		rst.clear();
	}

	@Test
	public void testContainsEdge() {
		assertTrue(rst.containsEdge(e02));
		assertFalse(rst.containsEdge(e23));
	}

	@Test
	public void testContainsVertex() {
		for (int i = 0; i < 7; i++)
			assertTrue(rst.containsVertex(i));
		assertFalse(rst.containsVertex(7));
	}

	@Test
	public void testDegree() {
		assertTrue(rst.degree(0) == 3);
		assertTrue(rst.degree(1) == 2);
		assertTrue(rst.degree(2) + rst.degree(3) == 3);
		assertTrue(rst.degree(4) == 2);
		assertTrue(rst.degree(5) == 1);
		assertTrue(rst.degree(6) == 1);
	}

	@Test
	public void testEdges() {
		assertTrue(rst.edges().size() == 6);
		assertTrue(rst.edges().contains(e02));

	}

	@Test
	public void testIncidentEdgesV() {
		assertTrue(Iterables.size(rst.incidentEdges(0)) == 3);
		assertTrue(Iterables.size(rst.incidentEdges(1)) == 2);
	}

	@Test
	public void testIncidentEdgesVV() {
		assertTrue(Iterables.size(rst.incidentEdges(1, 4)) == 1);
		assertTrue(Iterables.size(rst.incidentEdges(1, 5)) == 0);
		assertTrue(Iterables.size(rst.incidentEdges(2, 3)) == 0);
	}

	@Test
	public void testIncomingEdges() {
		assertTrue(Iterables.hasElements(rst.incomingEdges(0)));
		assertTrue(Iterables.hasElements(rst.incomingEdges(2), e02));
	}

	@Test
	public void testIndegree() {
		assertTrue(rst.indegree(0) == 0);
		for (int i = 1; i < 7; i++)
			assertTrue(rst.indegree(i) == 1);
	}

	@Test
	public void testInducedSubgraph() {
		Set<Integer> s = new HashSet<Integer>();
		s.add(2);
		s.add(4);
		s.add(3);
		s.add(6);
		InducedSubgraph<Integer, Graph.Edge<Integer>> is = rst
				.inducedSubgraph(s);
		assertTrue(is.order() == 4);
		assertTrue(is.size() == 1);
	}

	@Test
	public void testIsEmpty() {
		assertFalse(rst.isEmpty());
	}

	@Test
	public void testNeighbors() {
		assertTrue(Iterables.hasElements(rst.neighbors(0), 2, 3, 5));
		assertTrue(Iterables.hasElements(rst.neighbors(1), 4, 6));
		assertTrue(Iterables.hasElements(rst.neighbors(5), 0));
	}

	@Test
	public void testOrder() {
		assertTrue(rst.order() == 7);
	}

	@Test
	public void testOutdegree() {
		assertTrue(rst.outdegree(0) == 3);
		assertTrue(rst.outdegree(1) == 1);
		assertTrue(rst.outdegree(2) + rst.outdegree(3) == 1);
		assertTrue(rst.outdegree(4) == 1);
		assertTrue(rst.outdegree(5) == 0);
		assertTrue(rst.outdegree(6) == 0);
	}

	@Test
	public void testOutgoingEdgesV() {
		assertTrue(Iterables.size(rst.outgoingEdges(0)) == 3);
		assertTrue(Iterables.size(rst.outgoingEdges(4)) == 1);
		assertTrue(Iterables.size(rst.outgoingEdges(6)) == 0);
	}

	@Test
	public void testOutgoingEdgesVV() {
		assertTrue(Iterables.hasElements(rst.outgoingEdges(0, 2), e02));
	}

	@Test
	public void testPartialGraph() {
		PartialGraph<Integer, Graph.Edge<Integer>> pg = rst.partialGraph(rst
				.edges());
		assertTrue(rst.equals(pg));
	}

	@Test
	public void testPredecessors() {
		assertTrue(Iterables.hasElements(rst.predecessors(0)));
		assertTrue(Iterables.hasElements(rst.predecessors(1), 4));
		assertTrue(Iterables.hasElements(rst.predecessors(2), 0));
		assertTrue(Iterables.hasElements(rst.predecessors(3), 0));
		assertTrue(Iterables.hasElements(rst.predecessors(5), 0));
		assertTrue(Iterables.hasElements(rst.predecessors(6), 1));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAllEdges() {
		rst.removeAllEdges(rst.edges());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAllVertices() {
		rst.removeAllVertices(rst.vertices());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveEdge() {
		rst.removeEdge(e02);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveVertex() {
		rst.removeVertex(0);
	}

	@Test
	public void testSize() {
		assertTrue(rst.size() == 6);
	}

	@Test
	public void testSubgraph() {
		Subgraph<Integer, Graph.Edge<Integer>> sg = rst.subgraph(
				rst.vertices(), rst.edges());
		assertTrue(rst.equals(sg));
	}

	@Test
	public void testSuccessors() {
		assertTrue(Iterables.hasElements(rst.successors(0), 2, 3, 5));
		assertTrue(Iterables.hasElements(rst.successors(1), 6));
		assertTrue(Iterables.hasElements(rst.successors(4), 1));
		assertTrue(Iterables.hasElements(rst.successors(5)));
		assertTrue(Iterables.hasElements(rst.successors(6)));
	}

	@Test
	public void testVertices() {
		assertTrue(rst.vertices().size() == 7);
		for (int i = 0; i < 7; i++)
			assertTrue(rst.vertices().contains(i));
	}

	@Test
	public void testSupergraph() {
		assertTrue(rst.supergraph() == g);
	}

	@Test
	public void testChangeTreeAndRoot() {
		rst.changeTreeAndRoot(Graphs.breadthFirstSearch(g, 2), 2);
		assertTrue(rst.nLevels() == 4);
	}
	
	public static <V, E extends Graph.Edge<V>> void printRootedSpanningTree(RootedSpanningTree<V, E> rst) {
		System.out.println(rst.supergraph());
		System.out.println("nb levels = " + rst.nLevels() + " root = "
				+ rst.root());
		for (V v : rst.vertices())
			System.out.println("father of vertex " + v + ": " + rst.father(v));
		for (int l = 0; l < rst.nLevels(); l++) {
			System.out.print("level " + l + " = ");
			Iterators.print(rst.levelVertices(l), " ");
		}
		System.out.println();
		for (V v : rst.vertices()) {
			System.out.print("children of vertex " + v + ": ");
			Iterables.print(rst.children(v), " ");
		}
		System.out.println();
	}

}
