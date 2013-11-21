package graph;

import static org.junit.Assert.assertTrue;
import graph.Graph.Edge;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import util.Graphs;

public class MinimumSpanningTreeTest {
	
	static final String a = "a";
	static final String b = "b";
	static final String c = "c";
	static final String d = "d";
	static final String e = "e";
	static final String f = "f";
	static final String g = "g";
	static final String h = "h";
	static final String i = "i";
	
	static Graph<String, Graph.Edge<String>> gp;
	static Map<Graph.Edge<String>, Integer> w;
	
	@Before
	public void setUp() throws Exception {
		String[] v8 = {a,b,c,d,e,f,g,h,i};
		String[][][] e8 = {
								{{a, b}, {"4"}},{{a, h}, {"8"}},
								{{b, c}, {"8"}},{{b, h}, {"11"}},
								{{c, d}, {"7"}},{{c, f}, {"4"}},{{c, i}, {"2"}},
								{{d, e}, {"9"}},{{d, f}, {"14"}},
								{{e, f}, {"10"}},
								{{f, g}, {"2"}},
								{{g, i}, {"6"}},{{g, h}, {"1"}},
								{{h, i}, {"7"}},
							};
		w	= new HashMap<Graph.Edge<String>, Integer>();
		gp = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : v8) gp.addVertex(v);
		for (String[][] e1 : e8){
			Edge<String> ee = new DirectedEdge<String>(e1[0][0], e1[0][1]); 
			gp.addEdge(ee);
			w.put(ee, Integer.valueOf(e1[1][0]));
		}
	}	
	
	@Test
	public void testKruskal() throws Exception {
		Graph<String, Graph.Edge<String>> tree = Graphs.kruskal(gp, w);
		assertTrue(tree.size()==8);
	}
	
	@Test
	public void testPrim() throws Exception {
		Graph<String, Graph.Edge<String>> tree = Graphs.prim(gp, w, a);
		assertTrue(tree.size()==8);
	}

}
