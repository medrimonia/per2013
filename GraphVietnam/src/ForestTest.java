

import util.forest.Forest;
import util.connectivity.KConnectivity;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;
import graph.PartialGraph;

public class ForestTest {

	public static void main(String[] args){
		int k = 5;
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genNComponent(20, 3, k);
		Forest<Integer, Graph.Edge<Integer>> f = new Forest<Integer, Graph.Edge<Integer>>(g);
		System.out.println("Analysis done");
		//TODO: test induced graphs
		f.printE(g, k+ 4);
		for (int i = 1; i < k; i++){
			PartialGraph<Integer, Graph.Edge<Integer>> sg = g.partialGraph(f.filteredEdges(i));
			int connectivity = KConnectivity.connectivityLevel(sg);
			System.out.println("Connectivity : " + connectivity + "(expected : " + i + ")");
		}
		int nbVertices = 5; 
		Graph<Integer, Graph.Edge<Integer>> g2 = new MultiGraph<Integer, Graph.Edge<Integer>>();
		for (int i = 0; i < nbVertices; i++)
			g2.addVertex(i);		
		for (int i = 0; i < nbVertices; i++)
			for (int j = i + 1; j < nbVertices; j++)
				g2.addEdge(new DirectedEdge<Integer>(i,j));
		Forest<Integer, Graph.Edge<Integer>> f2 = new Forest<Integer, Graph.Edge<Integer>>(g2);
		System.out.println("Analysis done");
		f2.printE(g2, 5);
	}
}
