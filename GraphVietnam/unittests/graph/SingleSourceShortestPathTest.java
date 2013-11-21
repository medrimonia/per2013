package graph;

import static org.junit.Assert.assertTrue;
import graph.Graph.Edge;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.Graphs;
import util.Graphs.ShortestPaths;

public class SingleSourceShortestPathTest {
	
	static final String _1 = "1";
	static final String _2 = "2";
	static final String _3 = "3";
	static final String _4 = "4";
	static final String _5 = "5";
	static final String _6 = "6";
	
	static Graph<String, Graph.Edge<String>> g;
	static Map<Graph.Edge<String>, Double> w;
	
	@Before
	public void setUp() throws Exception {
		String[] vertices1 = {_1, _2, _3, _4, _5, _6 };
		String[][][] edges1 = {
								{{_1, _2}, {"3"}},{{_1, _3}, {"9"}},
								{{_2, _4}, {"7"}},{{_2, _5}, {"2"}},
								{{_3, _2}, {"1"}},{{_3, _4}, {"1"}},
								{{_4, _5}, {"3"}},
								{{_5, _3}, {"5"}},{{_5, _6}, {"1"}},
								{{_6, _4}, {"2"}}
							};
		g = new MultiGraph<String, Graph.Edge<String>>();
		w = new HashMap<Graph.Edge<String>, Double>();
		for (String v : vertices1) g.addVertex(v);
		for (String[][] e : edges1){
			Edge<String> edge = new DirectedEdge<String>(e[0][0], e[0][1]); 
			g.addEdge(edge);
			w.put(edge, Double.valueOf(e[1][0]));
		}		
	}	
	
	@Test
	public void testDijskstra() throws Exception {
		ShortestPaths<String> result = Graphs.dijskstra(g, _1,w);		
		assertTrue(result.distances.get(_3)==9);
	}
	
	@Test
	public void testBellmanFord() throws Exception {
		ShortestPaths<String> result = Graphs.bellmanFord(g, _1,w);		
		assertTrue(result.distances.get(_6)==6);
	}
}
