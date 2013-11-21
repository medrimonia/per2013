import graph.DirectedEdge;
import graph.Graph;
import graph.Graph.Edge;
import graph.MultiGraph;

import java.util.HashMap;
import java.util.Map;

import util.Graphs;
import util.NegativeCircuitException;
import util.NegativeEdgeException;

public class GraphsTest {

	/**
	 * @param args
	 * @throws NegativeEdgeException 
	 * @throws NegativeCircuitException 
	 */
	public static void main(String[] args) throws NegativeEdgeException, NegativeCircuitException {
		// TODO Auto-generated method stub
		
		final String _1 = "1";
		final String _2 = "2";
		final String _3 = "3";
		final String _4 = "4";
//		final String _5 = "5";
//		final String _6 = "6";
//		
//		String[] vertices1 = {_1, _2, _3, _4, _5, _6 };
//		String[][][] edges1 = {
//								{{_1, _2}, {"3"}},{{_1, _3}, {"9"}},
//								{{_2, _4}, {"7"}},{{_2, _5}, {"2"}},
//								{{_3, _2}, {"1"}},{{_3, _4}, {"1"}},
//								{{_4, _5}, {"3"}},
//								{{_5, _3}, {"5"}},{{_5, _6}, {"1"}},
//								{{_6, _4}, {"2"}}
//							};
//		Map<Graph.Edge<String>, Double> w1	= new HashMap<Graph.Edge<String>, Double>();
//		Graph<String, Graph.Edge<String>> g1 = new MultiGraph<String, Graph.Edge<String>>();
//		for (String v : vertices1) g1.addVertex(v);
//		for (String[][] e : edges1){
//			Edge<String> edge = new DirectedEdge<String>(e[0][0], e[0][1]); 
//			g1.addEdge(edge);
//			w1.put(edge, Double.valueOf(e[1][0]));
//		}
//		System.out.println("dijskstra");
//		System.out.println(Graphs.dijskstra(g1, _1,w1));
//		
//		final String A = "A";
//		final String B = "B";
//		final String C = "C";
//		final String D = "D";
//		final String E = "E";
//		final String F = "F";
//		
//		String[] vertices2 = {A,B,C,D,E,F};
//		String[][][] edges2 = {
//								{{A, B}, {"-2"}},{{A, F}, {"3"}},
//								{{B, C}, {"5"}},{{B, E}, {"1"}},
//								{{C, E}, {"3"}},{{C, F}, {"-3"}},{{C, D}, {"-5"}},
//								{{E, F}, {"1"}},{{E, D}, {"1"}},
//								{{F, B}, {"4"}}
//							};
//		Map<Graph.Edge<String>, Double> w2	= new HashMap<Graph.Edge<String>, Double>();
//		Graph<String, Graph.Edge<String>> g2 = new MultiGraph<String, Graph.Edge<String>>();
//		for (String v : vertices2) g2.addVertex(v);
//		for (String[][] e : edges2){
//			Edge<String> edge = new DirectedEdge<String>(e[0][0], e[0][1]); 
//			g2.addEdge(edge);
//			w2.put(edge, Double.valueOf(e[1][0]));
//		}
//		System.out.println("bellmanFord");
		//System.out.println(Graphs.bellmanFord(g2, A ,w2));
//		System.out.println(Graphs.bellmanFord(g1, _1 ,w1));
//		
//		String[] vertices3 = {A,B,C,D};
//		String[][][] edges3 = {
//								{{A, B}, {"1"}},{{A, D}, {"4"}},
//								{{B, A}, {"2"}},{{B, C}, {"-2"}},
//								{{C, A}, {"3"}},
//								{{D, B}, {"-5"}},{{D, C}, {"-1"}}
//							};
//		Map<Graph.Edge<String>, Double> w3	= new HashMap<Graph.Edge<String>, Double>();
//		Graph<String, Graph.Edge<String>> g3 = new MultiGraph<String, Graph.Edge<String>>();
//		for (String v : vertices3) g3.addVertex(v);
//		for (String[][] e : edges3){
//			Edge<String> edge = new DirectedEdge<String>(e[0][0], e[0][1]); 
//			g3.addEdge(edge);
//			w3.put(edge, Double.valueOf(e[1][0]));
//		}
//		System.out.println("floydWarshall");
//		System.out.println(Graphs.floydWarshall(g3, w3));
		
//		
		final String S = "S";
		final String T = "T";
//		
		String[] vertices4 = {_1, _2, _3, _4, S, T };
		String[][][] edges4 = {
								{{S, _1}, {"16"}},{{S, _2}, {"13"}},
								{{_1, _2}, {"10"}},{{_1, _3}, {"12"}},
								{{_2, _1}, {"4"}},{{_2, _4}, {"14"}},
								{{_3, _2}, {"9"}},{{_3, T}, {"20"}},
								{{_4, _3}, {"7"}},{{_4, T}, {"4"}}
							};
		Map<Graph.Edge<String>, Integer> c4	= new HashMap<Graph.Edge<String>, Integer>();
		Graph<String, Graph.Edge<String>> g4 = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices4) g4.addVertex(v);
		for (String[][] e : edges4){
			Edge<String> edge = new DirectedEdge<String>(e[0][0], e[0][1]); 
			g4.addEdge(edge);
			c4.put(edge, Integer.valueOf(e[1][0]));
		}
		System.out.println("fordFulkerson");
		System.out.println(Graphs.fordFulkersonV01(g4, S, T,null,c4));
		System.out.println("----------------");
		System.out.println(Graphs.fordFulkersonV02(g4, S, T,c4));
//		
//		
	}

}
