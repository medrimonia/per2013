

import util.forest.Forest;
import util.connectivity.KConnectivity;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;
import graph.PartialGraph;

public class ForestTest {
	
	public static void testGraph(Graph<Integer, Graph.Edge<Integer>> g, int expectedConnexity)
	{
		Forest<Integer, Graph.Edge<Integer>> f = new Forest<Integer, Graph.Edge<Integer>>(g);
		System.out.println("Analysis done");
		System.out.println("Initial edges : " + g.edges().size());
		for (int i = 1; i < expectedConnexity; i++){
			PartialGraph<Integer, Graph.Edge<Integer>> sg = g.partialGraph(f.filteredEdges(i));
			int connectivity = KConnectivity.connectivityLevel(sg);
			System.out.println("Connectivity : " + connectivity + "(expected : " + i + ")");
			System.out.println("\t Edges number : " + sg.edges().size());
		}
	}

	public static void main(String[] args){
		int k = 5;
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genNComponent(10, 8, k);
		testGraph(g, k + 1);
		int nbVertices = 5; 
		Graph<Integer, Graph.Edge<Integer>> g2 = new MultiGraph<Integer, Graph.Edge<Integer>>();
		for (int i = 0; i < nbVertices; i++)
			g2.addVertex(i);		
		for (int i = 0; i < nbVertices; i++)
			for (int j = i + 1; j < nbVertices; j++)
				g2.addEdge(new DirectedEdge<Integer>(i,j));
		testGraph(g2, 4);
	}
}
