package graph;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import util.Graphs;

public class MinimumCutsetTest {
	
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String SOURCE = "source";
	static final String SINK = "sink";
	
	static Graph<String, Graph.Edge<String>> g;
	
	@Before
	public void setUp() throws Exception {
		String[] v5 = { SOURCE, SINK, ONE, TWO, THREE, FOUR};
		String[][] e5 = { {SOURCE,ONE},{SOURCE,TWO},
				{ONE,TWO},{ONE,THREE},{ONE,FOUR},
				{TWO,FOUR},
				{THREE,SINK},{FOUR,THREE},{FOUR,SINK},{SINK,SOURCE}};
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : v5)
			g.addVertex(v);
		for (String[] edge : e5)
			g.addEdge(new DirectedEdge<String>(edge[0], edge[1]));
	}	
	
	@Test
	public void testMinimumCutset() throws Exception {
		Set<String> result = Graphs.minimumCutset(g);
		assertTrue(result.size()==3);
	}
	
	@Test
	public void testMinimumEdgeCutset() throws Exception {
		Set<Graph.Edge<String>> result = Graphs.minimumEdgeCutset(g);
		assertTrue(result.size()==3);
	}
	
	@Test
	public void testMinimumDigraphCutset() throws Exception {
		Set<String> result = Graphs.minimumDigraphCutset(g);
		assertTrue(result.size()==1);
	}
	
	@Test
	public void testMinimumDigraphEdgeCutset() throws Exception {
		Set<Graph.Edge<String>> result = Graphs.minimumDigraphEdgeCutset(g);
		assertTrue(result.size()==1);
	}
}
