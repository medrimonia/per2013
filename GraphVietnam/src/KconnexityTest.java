import graph.Graph;
import util.connexity.KConnexity;
import java.util.Random;

public class KconnexityTest {
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
	
	public static void testGenTwoComponent()
	{
		int componentSize = rand.nextInt(maxComponentSize - minComponentSize) + minComponentSize;
		int k = rand.nextInt(maxkConnexity - minkConnexity) + minkConnexity;
		
		System.out.println("Generate a " + k + "-connected graph with a componen size of " + componentSize);
		Graph<Integer, Graph.Edge<Integer>> g = KConnexity.genTwoComponent(componentSize, k);
		System.out.println("The graph is " + KConnexity.connectivityLevel(g) + "-connected");
		System.out.println("Is the graph " + k + "-connected : " + KConnexity.isKConnected(g, k));
		System.out.println("Is the graph " + (k-1) + "-connected : " + KConnexity.isKConnected(g, k-1));
		System.out.println("Is the graph " + (k+1) + "-connected : " + KConnexity.isKConnected(g, k+1));
	}
	
	static int maxSize = 30;
	static int minSize = 20;
	
	public static void testGenBaseOnDegree()
	{
		int size = rand.nextInt(maxSize - minSize) + minSize;
		int k = rand.nextInt(maxkConnexity - minkConnexity) + minkConnexity;
		
		System.out.println("Generate a " + k + "-connected graph with a componen size of " + size);
		Graph<Integer, Graph.Edge<Integer>> g = KConnexity.genBaseOnDegree(size, k);
		System.out.println("The graph is " + KConnexity.connectivityLevel(g) + "-connected");
		System.out.println("Is the graph " + k + "-connected : " + KConnexity.isKConnected(g, k));
		System.out.println("Is the graph " + (k-1) + "-connected : " + KConnexity.isKConnected(g, k-1));
		System.out.println("Is the graph " + (k+1) + "-connected : " + KConnexity.isKConnected(g, k+1));
		
	}

}
