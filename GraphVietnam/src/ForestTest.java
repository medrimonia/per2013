

import util.forest.Forest;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

public class ForestTest {

	public static void main(String[] args){
		/* Graph generation needed
		int k = 3;
		Graph g = KConnectedGenerator.generateGraphOfDegreeK(15, k);
		g.display();
		Forest f = new Forest(g);
		System.out.println("Analysis done");
		f.printE(g, k+ 4);
		*/
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
