import graph.Graph;
import util.connectivity.KConnectivity;

import java.util.HashMap;
import java.util.Random;

public class GraphGenerationTest {
	static int maxkConnexity = 7;
	static int minkConnexity = 2;
	static Random rand = new Random();
	
	//TODO: Test for simple and undirected, issues found!
	
	public static void main(String[] args)
	{
		boolean assertsEnabled = false;
		assert assertsEnabled = true;
		if (!assertsEnabled){
			System.err.println("Assertions needs to be activated to run the tests (-ea)");
			return;
		}
		testGenTwoComponent();
		testGenNComponent();
		testGenBaseOnDegree();
	}

	//TODO parametrized and place into graphs
	public static boolean hasLoop(Graph<Integer, Graph.Edge<Integer>> g){
		for (Graph.Edge<Integer> e : g.edges()){
			if (e.source().equals(e.target()))
				return true;
		}
		return false;
	}

	// Check if g has multiple edges or loops
	public static boolean isSimple(Graph<Integer, Graph.Edge<Integer>> g){
		int [][] nbEdges = new int [g.vertices().size()][g.vertices().size()];
		for (int i = 0; i < nbEdges.length; i++)
			for (int j = i; j < nbEdges.length; j++)
				nbEdges[i][j] = 0;
		for (Graph.Edge<Integer> e : g.edges()){
			int src = e.source();
			int dst = e.target();
			nbEdges[src][dst]++;
			nbEdges[dst][src]++;
		}
		for (int i = 0; i < nbEdges.length; i++)
			for (int j = i; j < nbEdges.length; j++)
				if (nbEdges[i][j] > 1)
					return false;
		return true;
	}

	public static void testGraph(Graph<Integer, Graph.Edge<Integer>> g, int expectedConnexity){
		assert(isSimple(g));
		assert(KConnectivity.connectivityLevel(g) == expectedConnexity);
	}

	static int maxComponentSize = 15;
	static int minComponentSize = 10;
	
	/**
	 * Test for the genTwoComponent function
	 */
	public static void testGenTwoComponent()
	{
		int componentSize = rand.nextInt(maxComponentSize - minComponentSize) + minComponentSize;
		int k = rand.nextInt(maxkConnexity - minkConnexity) + minkConnexity;
		
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genTwoComponent(componentSize, k);
		testGraph(g, k);
		System.out.println("Gen 2 component : ok");
	}
	
	static int maxComponentNb = 5;
	static int minComponentNb = 3;
	
	public static void testGenNComponent()
	{
		int componentSize = rand.nextInt(maxComponentSize - minComponentSize) + minComponentSize;
		int k = rand.nextInt(maxkConnexity - minkConnexity) + minkConnexity;
		int n = rand.nextInt(maxComponentNb - minComponentNb) + minComponentNb;
		
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genNComponent(componentSize, n, k);
		testGraph(g, k);
		System.out.println("Gen N component : ok");
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
		
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genBaseOnDegree(size, k);
		testGraph(g, k);
		System.out.println("Gen based on degree : ok");
	}

}
