import graph.DirectedEdge;
import graph.Graph;
import graph.Graph.Edge;
import graph.MultiGraph;

import java.util.HashMap;
import java.util.Map;

import util.Graphs;

public class GraphsTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final String ONE = "one";
		final String TWO = "two";
		final String THREE = "three";
		final String FOUR = "four";
//		final String A = "A";
//		final String B = "B";
//		
//		final String a = "a";
//		final String b = "b";
//		final String c = "c";
//		final String d = "d";
//		final String e = "e";
//		final String f = "f";
//		final String g = "g";
//		final String h = "h";
//		final String i = "i";
//		
//		
//		System.out.println("case1");
//		String[] vertices = { ONE, TWO, A, B };
//		String[][] edges = { { A, ONE }, { A, TWO },
//							{ ONE, B }, {TWO, B },
//							{ONE, TWO}
//							};
//		
//		System.out.println(Graphs.minimumCutset(createGraph(vertices, edges)));
//		
//		System.out.println();
//		System.out.println("case1-edge");
//		System.out.println(Graphs.minimumEdgeCutset(createGraph(vertices, edges)));
//			
//		System.out.println();
//		System.out.println("case2");
//		
//		String[] v2 = { a,b,c,d };
//		String[][] e2 = { { a,c }, { c,b }, {b,d}};
//		
//		System.out.println(Graphs.minimumCutset(createGraph(v2, e2)));
//		System.out.println();
//		System.out.println("case2-edge");
//		System.out.println(Graphs.minimumEdgeCutset(createGraph(v2, e2)));
//		
//		System.out.println();
//		System.out.println("case3");
//		
//		String[] v3 = { a,b,c,d,e,f };
//		String[][] e3 = { { a,b }, { a,c }, {b,c},
//							{ d,e }, { d,f }, {e,f},
//							{c , d}
//							};
//		
//		System.out.println(Graphs.minimumCutset(createGraph(v3, e3)));
//		System.out.println();
//		System.out.println("case3-edge");
//		System.out.println(Graphs.minimumEdgeCutset(createGraph(v3, e3)));
//		
//		System.out.println();
//		System.out.println("case4");
//		
//		String[] v4 = { a,b,c,d,e,f,g,h };
//		String[][] e4 = { {a,b},{a,c},{b,d},{b,e},{c,e},{d,e},{d,f},{e,g},{f,g},{f,h}
//							};
//		
//		System.out.println(Graphs.minimumCutset(createGraph(v4, e4)));
//		System.out.println();
//		System.out.println("case4-edge");
//		System.out.println(Graphs.minimumEdgeCutset(createGraph(v4, e4)));
//		
//		System.out.println();
//		System.out.println("case5");
		final String SOURCE = "source";
		final String SINK = "sink";
//		
		String[] v5 = { SOURCE, SINK, ONE, TWO, THREE, FOUR};
		String[][] e5 = { {SOURCE,ONE},{SOURCE,TWO},
				{ONE,TWO},{ONE,THREE},{ONE,FOUR},
				{TWO,FOUR},
				{THREE,SINK},{FOUR,THREE},{FOUR,SINK},{SINK,SOURCE}};
		
//		System.out.println(Graphs.minimumCutset(createGraph(v5, e5)));
//		System.out.println();
//		System.out.println("case5-edge");
//		System.out.println(Graphs.minimumEdgeCutset(createGraph(v5, e5)));
//		
//		System.out.println();
//		System.out.println("case6");
//		System.out.println(Graphs.minimumDigraphCutset(createGraph(v5, e5)));
//		System.out.println();
//		System.out.println("case6-edge");
		System.out.println(Graphs.minimumDigraphEdgeCutset(createGraph(v5, e5)));
//		
//		
//		final String s1 = "s1";
//		final String s2 = "s2";
//		final String s3 = "s3";
//		final String s4 = "s4";
//		final String s5 = "s5";
//		final String t1 = "t1";
//		final String t2 = "t2";
//		final String t3 = "t3";
//		final String t4 = "t4";
//		
//		String[] v7 = { s1,s2,s3,s4,s5,t1,t2,t3,t4};
//		String[][] e7 = { {s1,t1},{s2,t1},{s2,t3},{s3,t2},{s3,t3},{s3,t4},{s4,t3},{s5,t3}};
//		
//		System.out.println();
//		System.out.println("case7");
//		System.out.println(Graphs.maximumBipartiteMatching(createGraph(v7, e7), new String[]{ s1,s2,s3,s4,s5}, new String[]{ t1,t2,t3,t4}));
//		System.out.println();
//		
//		
//		String[] v8 = {a,b,c,d,e,f,g,h,i};
//		String[][][] e8 = {
//								{{a, b}, {"4"}},{{a, h}, {"8"}},
//								{{b, c}, {"8"}},{{b, h}, {"11"}},
//								{{c, d}, {"7"}},{{c, f}, {"4"}},{{c, i}, {"2"}},
//								{{d, e}, {"9"}},{{d, f}, {"14"}},
//								{{e, f}, {"10"}},
//								{{f, g}, {"2"}},
//								{{g, i}, {"6"}},{{g, h}, {"1"}},
//								{{h, i}, {"7"}},
//							};
//		Map<Graph.Edge<String>, Integer> w8	= new HashMap<Graph.Edge<String>, Integer>();
//		Graph<String, Graph.Edge<String>> g8 = new MultiGraph<String, Graph.Edge<String>>();
//		for (String v : v8) g8.addVertex(v);
//		for (String[][] e1 : e8){
//			Edge<String> ee = new DirectedEdge<String>(e1[0][0], e1[0][1]); 
//			g8.addEdge(ee);
//			w8.put(ee, Integer.valueOf(e1[1][0]));
//		}
//		System.out.println(Graphs.kruskal(g8, w8));
//		
//		System.out.println();
//		System.out.println(Graphs.prim(g8, w8, a));
//		System.out.println();
//		
		
//		String[] v9 = {a,b,c,d,e,f,g};
//		String[][][] e9 = {
//								{{a, b}, {"7"}},{{a, d}, {"5"}},{{d, b}, {"9"}},
//								{{b, c}, {"8"}},{{b, e}, {"7"}},{{e, c}, {"5"}},
//								{{d, e}, {"15"}},{{d, f}, {"6"}},{{e, f}, {"8"}},
//								{{g, f}, {"11"}},{{g, e}, {"9"}},
//							};
//		Map<Graph.Edge<String>, Integer> w9	= new HashMap<Graph.Edge<String>, Integer>();
//		Graph<String, Graph.Edge<String>> g9 = new MultiGraph<String, Graph.Edge<String>>();
//		for (String v : v9) g9.addVertex(v);
//		for (String[][] e1 : e9){
//			Edge<String> ee = new DirectedEdge<String>(e1[0][0], e1[0][1]); 
//			g9.addEdge(ee);
//			w9.put(ee, Integer.valueOf(e1[1][0]));
//		}
//		
//		System.out.println(Graphs.prim(g9, w9, d));
		
	}
	
	private static Graph<String, Graph.Edge<String>> createGraph(String[] vertices, String[][] edges){
		Graph<String, Graph.Edge<String>> g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] edge : edges)
			g.addEdge(new DirectedEdge<String>(edge[0], edge[1]));
		System.out.println(g);
		return g;
	}

}
