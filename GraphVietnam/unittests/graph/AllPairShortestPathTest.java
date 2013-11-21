package graph;

import static org.junit.Assert.assertTrue;
import graph.Graph.Edge;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.Graphs;
import util.Graphs.ShortestPathsMatrices;

public class AllPairShortestPathTest {
	
	static final String A = "A";
	static final String B = "B";
	static final String C = "C";
	static final String D = "D";
	
	static Graph<String, Graph.Edge<String>> g;
	static Map<Graph.Edge<String>, Double> w;
	
	@Before
	public void setUp() throws Exception {
		String[] vertices3 = {A,B,C,D};
		String[][][] edges3 = {
								{{A, B}, {"1"}},{{A, D}, {"4"}},
								{{B, A}, {"2"}},{{B, C}, {"-2"}},
								{{C, A}, {"3"}},
								{{D, B}, {"-5"}},{{D, C}, {"-1"}}
							};
		w	= new HashMap<Graph.Edge<String>, Double>();
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices3) g.addVertex(v);
		for (String[][] e : edges3){
			Edge<String> edge = new DirectedEdge<String>(e[0][0], e[0][1]); 
			g.addEdge(edge);
			w.put(edge, Double.valueOf(e[1][0]));
		}
	}	
	
	@Test
	public void testFloydWarshall() throws Exception {
		ShortestPathsMatrices<String> result = Graphs.floydWarshall(g, w);
		assertTrue(result.distances.get(D).get(B)==-5);
		assertTrue(result.distances.get(D).get(C)==-7);
	}
	
}
