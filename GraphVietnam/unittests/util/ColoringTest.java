package util;

import static org.junit.Assert.assertTrue;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ColoringTest {

	static Graph<Integer, Graph.Edge<Integer>> g;
	static int N = 4;

	@Before
	public void setUp() throws Exception {
		// Complete graph with N vertices
		g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		for (int i = 0; i < N; i++) {
			g.addVertex(i);
		}
		for (int i = 0; i < N - 1; i++)
			for (int j = i + 1; j < N; j++)
				g.addEdge(new DirectedEdge<Integer>(i, j));
	}

	@Test
	public void testComputeImproperColoring() {
		Integer[] colors = new Integer[N];
		for (int i = 0; i < N; i++)
			colors[i] = i;
		Map<Integer, Integer> coloring = Coloring.computeImproperColoring(g,
				colors);
		for (int i = 0; i < N - 1; i++)
			for (int j = i + 1; j < N; j++)
				assertTrue(coloring.get(i) != coloring.get(j));
	}

	@Test
	public void testComputeColoring() {
		Map<Integer, Integer> coloring = Coloring.computeColoring(g);
		for (int i = 0; i < N - 1; i++)
			for (int j = i + 1; j < N; j++)
				assertTrue(coloring.get(i) != coloring.get(j));
		for (int i = 0; i < N; i++)
			assertTrue(coloring.get(i) < N);
	}

}
