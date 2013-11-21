package graph;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.RandomGraph;

public class RandomGraphTest {
	
	static Graph<String, Graph.Edge<String>> g1;
	static Map<Graph.Edge<String>,Integer> w1;
	static Graph<String, Graph.Edge<String>> g2;
	static Map<Graph.Edge<String>,Integer> w2;
	static Graph<String, Graph.Edge<String>> g3;
	static Map<Graph.Edge<String>,Integer> l3;
	static Map<Graph.Edge<String>,Integer> u3;
	
	@Before
	public void setUp() throws Exception {
		g1 = new MultiGraph<String, Graph.Edge<String>>();
		w1 = new HashMap<Graph.Edge<String>, Integer>();
		g2 = new MultiGraph<String, Graph.Edge<String>>();
		w2 = new HashMap<Graph.Edge<String>, Integer>();
		g3 = new MultiGraph<String, Graph.Edge<String>>();
		l3 = new HashMap<Graph.Edge<String>, Integer>();
		u3 = new HashMap<Graph.Edge<String>, Integer>();
	}	
	
	@Test
	public void testGraph() throws Exception {
		RandomGraph.randomGraph(g1, 10, null, w1, 9);
		assertTrue((g1.order()>9)&&g1.order()<10*10/2-10);
	}
	
	@Test
	public void testDiGraph() throws Exception {
		RandomGraph.randomDiGraph(g2, 10, null, w2, 9);
		assertTrue((g2.order()>9)&&g2.order()<10*10/2-10);
	}
	
	@Test
	public void testNetwork() throws Exception {
		RandomGraph.randomNetwork(g3, 10, null, l3, u3, 11, 99);
		assertTrue((g3.order()>9)&&g3.order()<10*10/2-10);
	}
}
