package graph;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import util.Graphs;
import util.Graphs.BipartitionResult;

public class BipartitionBiconnectedGraphTest {
	
	static final String s1 = "s1";
	static final String a1 = "a1";
	static final String a2 = "a2";
	static final String a3 = "a3";
	static final String a4 = "a4";
	static final String a5 = "a5";
	static final String s2 = "s2";
	static final String b1 = "b1";
	static final String b2 = "b2";
	static final String b3 = "b3";
	static final String b4 = "b4";
	
	static Graph<String, Graph.Edge<String>> g;
	
	@Before
	public void setUp() throws Exception {
		String[] vertices = { s1,s2,a1,a2,a3,a4,a5,b1,b2,b3,b4 };
		String[][] edges = { {s1, a1},{a1, a2},{a2, a3},{a3, a5},{a4, a1},{a4, a2},{a4, a3},
							{s2, b1},{s2, b4},{b2, b1},{b2, s2},{b3, s2},{b3, b4},
							{s1, b1},{b1, a1},{b2, a4},{b3, a4},{a3, b3},{b3, a5},{a5, b4}
							};
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] edge : edges)
			g.addEdge(new DirectedEdge<String>(edge[0], edge[1]));
	}	
	
	@Test
	public void test() throws Exception {
		BipartitionResult<String, Graph.Edge<String>> result = Graphs.part2(g, null, s1, s2, 6, 5);
		assertTrue(result.v1.order()==6);
		assertTrue(result.v2.order()==5);
	}
}
