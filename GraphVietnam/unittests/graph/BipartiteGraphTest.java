package graph;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import util.Graphs;

public class BipartiteGraphTest {
	
	static final String s1 = "s1";
	static final String s2 = "s2";
	static final String s3 = "s3";
	static final String s4 = "s4";
	static final String s5 = "s5";
	static final String t1 = "t1";
	static final String t2 = "t2";
	static final String t3 = "t3";
	static final String t4 = "t4";
	
	static Graph<String, Graph.Edge<String>> g;
	
	@Before
	public void setUp() throws Exception {
		String[] v7 = { s1,s2,s3,s4,s5,t1,t2,t3,t4};
		String[][] e7 = { {s1,t1},{s2,t1},{s2,t3},{s3,t2},{s3,t3},{s3,t4},{s4,t3},{s5,t3}};
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : v7)
			g.addVertex(v);
		for (String[] edge : e7)
			g.addEdge(new DirectedEdge<String>(edge[0], edge[1]));
	}	
	
	@Test
	public void test() throws Exception {
		Set<Graph.Edge<String>> result = Graphs.maximumBipartiteMatching(g, new String[]{ s1,s2,s3,s4,s5}, new String[]{ t1,t2,t3,t4});
		assertTrue(result.size()==3);
	}
}
