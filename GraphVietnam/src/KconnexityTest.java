import graph.Graph;
import util.connexity.KConnexity;

public class KconnexityTest {
	public static void main(String[] args){
		Graph<Integer, Graph.Edge<Integer>> g = KConnexity.generateKConnectedGraphWithTwoComponent(20, 5);
		System.out.println("The graph is " + KConnexity.connexityLevel(g) + "-connected");
	}

}
