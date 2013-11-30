import graph.Graph;
import util.connectivity.KConnectivity;

import java.util.Random;

public class KconnectivityTest {
	static int maxkConnexity = 8;
	static int minkConnexity = 2;
	static Random rand = new Random();
	
	public static void main(String[] args)
	{
		testGenTwoComponent();
		testGenBaseOnDegree();
	}

	static int maxComponentSize = 20;
	static int minComponentSize = 10;
	
	/**
	 * Test for the genTwoComponent function
	 */
	public static void testGenTwoComponent()
	{
		int componentSize = rand.nextInt(maxComponentSize - minComponentSize) + minComponentSize;
		int k = rand.nextInt(maxkConnexity - minkConnexity) + minkConnexity;
		
		System.out.println("Generate a " + k + "-connected graph with a componen size of " + componentSize);
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genTwoComponent(componentSize, k);
		System.out.println("The graph is " + KConnectivity.connectivityLevel(g) + "-connected");
		System.out.println("Is the graph " + k + "-connected : " + KConnectivity.isKConnected(g, k));
		System.out.println("Is the graph " + (k-1) + "-connected : " + KConnectivity.isKConnected(g, k-1));
		System.out.println("Is the graph " + (k+1) + "-connected : " + KConnectivity.isKConnected(g, k+1));
	}
	
	static int maxSize = 30;
	static int minSize = 20;
	
	/**
	 * Test for the genBaseOnDegree function
	 */
	public static void testGenBaseOnDegree()
	{
		int size = rand.nextInt(maxSize - minSize) + minSize;
		int k = rand.nextInt(maxkConnexity - minkConnexity) + minkConnexity;
		
		System.out.println("Generate a " + k + "-connected graph with a componen size of " + size);
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genBaseOnDegree(size, k);
		System.out.println("The graph is " + KConnectivity.connectivityLevel(g) + "-connected");
		System.out.println("Is the graph " + k + "-connected : " + KConnectivity.isKConnected(g, k));
		System.out.println("Is the graph " + (k-1) + "-connected : " + KConnectivity.isKConnected(g, k-1));
		System.out.println("Is the graph " + (k+1) + "-connected : " + KConnectivity.isKConnected(g, k+1));
		
	}

}
