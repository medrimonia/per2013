package util.connectivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import util.Graphs;
import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

public class KConnectivity {
	
	/**
	 * 	Generate a k-connected graph with two complete components linked by k edge
	 * 	@param sizeComponent The size of two components
	 * 	@param k The connectivity of the graph
	 *  @return The k-connected generated graph
	 * */
	static public Graph<Integer, Graph.Edge<Integer>> genTwoComponent(int sizeComponent, int k)
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
	 * 	Generate a k-connected graph with n complete components linked by k edge
	 * 	@param sizeComponent The components size
	 *  @param n The number of component
	 * 	@param k The connectivity of the graph
	 *  @return The k-connected generated graph
	 * */
	static public Graph<Integer, Graph.Edge<Integer>> genNComponent(int sizeComponent, int n, int k)
	{
		Graph<Integer, Graph.Edge<Integer>> g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		int size = sizeComponent*n;
		
		/*Adding the vertices */
		for (int i = 0; i < size; i++)
			g.addVertex(i);		
		
		/*Generate the two complete components*/
		for (int i = 0; i < sizeComponent; i++)
			for (int j = i + 1; j < sizeComponent; j++)
			{
				for(int z = 0; z < n; z++)
				{
					int u = i+sizeComponent*z;
					int v = j+sizeComponent*z;
					g.addEdge(new DirectedEdge<Integer>(u,v));
					//g.addEdge(new DirectedEdge<Integer>(v,u));
				}
			}
		
		/*Linking the n components*/
		for(int z = 0; z < n - 1; z++)
		{
			for (int i=0; i<k; i++)
			{
				int u = i+sizeComponent*z;
				int v = i+(sizeComponent) * (z+1);
				g.addEdge(new DirectedEdge<Integer>(u,v));
				//g.addEdge(new DirectedEdge<Integer>(v,u));
			}
		}
		
		return g;
	}
	
	
	/**
	 * 	Generate a k-connected graph
	 * 	@param size The graph size
	 * 	@param k The connectivity of the graph
	 *  @return The k-connected generated graph
	 * */
	static public Graph<Integer, Graph.Edge<Integer>> genBaseOnDegree(int size, int k)
	{
		Graph<Integer, Graph.Edge<Integer>> g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		
		/*Adding the vertices */
		for (int i = 0; i < size; i++)
			g.addVertex(i);
		
		Random rand = new Random();
		
		ArrayList<int[]> edgeList = new ArrayList<int[]>();
		for(int x = 0; x < size; x++)
		{
			for(int y = x+1; y < size; y++)
			{
				int[] edge = new int[2];
				edge[0] = x;
				edge[1] = y;
				edgeList.add(edge);
			}
		}
		
		/*Generate the two complete components*/
		for (int i = 0; i < size; i++)
		{
			int j = g.degree(i);
			while(j < k)
			{
				int r = rand.nextInt(size-j-1);
				
				for(int[] edge : edgeList)
				{
					if(edge[0] == i || edge[1] == i)
					{
						if(r == 0)
						{
							g.addEdge(new DirectedEdge<Integer>(edge[0],edge[1]));
							edgeList.remove(edge);
							j++;
							break;
						}
						else
						{
							r--;
						}
					}
				}
			}
			
		}
		
		return g;
	}
	
	
	/**
	 * Is the graph given is parameter is k-connected
	 * @param g The graph to test
	 * @param k The k-connectivity to test
	 * @return true if the graph connectivity is greater than k else false
	 */
	static public <V, E extends Graph.Edge<V>> boolean isKConnected(Graph<V, E> g, int k)
	{
		Set<V> minCutSet = Graphs.minimumCutset(g);
		return minCutSet.size() >= k;
	}
	
	
	/**
	 * Compute the level of connectivity of the graph
	 * @param g The graph to compute on
	 * @return The level of connectivity
	 */
	static public <V, E extends Graph.Edge<V>> int connectivityLevel(Graph<V, E> g)
	{
		Set<V> minCutSet = Graphs.minimumCutset(g);
		return minCutSet.size();
	}
	
}
