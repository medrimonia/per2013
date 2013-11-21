package util;

import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

import java.util.Random;

public class Graphs2 {

	public static Graph<Integer, Graph.Edge<Integer>> randomDirectedGraph(
			int n, double p) {
		if (p < 0 || p > 1)
			throw new IllegalArgumentException("The probability " + p
					+ "is not between 0 and 1");
		Graph<Integer, Graph.Edge<Integer>> g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		Random rnd = new Random();
		for (int i = 0; i < n; i++) {
			g.addVertex(i);
		}
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (rnd.nextDouble() < p) {
					g.addEdge(new DirectedEdge<Integer>(i, j));
				}
			}
		return g;
	}
}
