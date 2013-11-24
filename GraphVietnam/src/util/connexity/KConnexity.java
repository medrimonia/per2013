package util.connexity;

import java.util.Set;

import util.Graphs;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

public class KConnexity {
	
	/**
	 * 	Generate a k-connected graph with two complete components linked by k edge
	 * 	@param sizeComponent The size of two components
	 * 	@param k The connexity of the graph
	 *  @return The k-connected generated graph
	 * */
	static public Graph<Integer, Graph.Edge<Integer>> generateKConnectedGraphWithTwoComponent(int sizeComponent, int k)
	{
		Graph<Integer, Graph.Edge<Integer>> g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		int size = sizeComponent*2;
		
		/*Adding the vertices */
		for (int i = 0; i < size; i++)
			g.addVertex(i);		
		
		/*Generate the two complete components*/
		for (int i = 0; i < sizeComponent; i++)
			for (int j = i + 1; j < sizeComponent; j++)
			{
				g.addEdge(new DirectedEdge<Integer>(i,j));
				g.addEdge(new DirectedEdge<Integer>(i+sizeComponent,j+sizeComponent));
			}
		
		/*Linking the two components*/
		for (int i=0; i<k; i++)
		{
			g.addEdge(new DirectedEdge<Integer>(i,i+sizeComponent));
		}
		
		return g;
	}
	
	/**
	 * Is the graph given is parameter is k-connected
	 * @param g The graph to test
	 * @param k The k-connexity to test
	 * @return true if the graph connexity is greater than k else false
	 */
	static public <V, E extends Graph.Edge<V>> boolean isKConnected(Graph<V, E> g, int k)
	{
		Set<V> minCutSet = Graphs.minimumCutset(g);
		return minCutSet.size() >= k;
	}
	
	static public <V, E extends Graph.Edge<V>> int connexityLevel(Graph<V, E> g)
	{
		Set<V> minCutSet = Graphs.minimumCutset(g);
		return minCutSet.size();
	}
	
}
