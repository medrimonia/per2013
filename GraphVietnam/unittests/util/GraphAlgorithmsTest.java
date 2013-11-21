package util;


import static org.junit.Assert.assertTrue;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

import org.junit.Before;
import org.junit.Test;

public class GraphAlgorithmsTest {

	static Graph<Integer, Graph.Edge<Integer>> g;

	@Before
	public void setUp() throws Exception {
		g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		for (int i = 0; i < 12; i++) {
			g.addVertex(i);
		}
		int[][] edges = {{0,1}, {0,5}, {1,2}, {2,3}, {2,7},{3,4}, {4,1}, {5,6}, 
				{6, 0},{9, 10}, {10,11},{11,9}}; 
	for (int[] e : edges)
		g.addEdge(new DirectedEdge<Integer>(e[0], e[1]));
	} 

	@Test
	public void testDepthFirstSearch() {
		assertTrue(! Graphs.isConnected(Graphs.breadthFirstSearch(g, 0)));
	}

}
