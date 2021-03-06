import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import util.connectivity.KConnectivity;
import util.partitioning.Partitioning;


public class KPartitionTest {
	
	public static void main(String[] args){
		figure2Test();
		int nbTests = 1000;
		for (int i = 1; i <= nbTests; i++){
			if (!randomTest()){
				System.err.println("A test failed!!!");
				break;
			}
			System.out.println(i + "/" + nbTests);
		}
	}
	
	/** Warning, since execution is not determinist, getting once the
	 *  right result is not enough to conclude.
	 */
	public static void figure2Test(){
		Graph<Integer, Graph.Edge<Integer>> g = figure2Graph();
		int k = 3;
		Integer roots[] = {4,2,3};
		Integer partitionSizes[] = {2,2,3};
		List<Set<Integer>> p;
		p = Partitioning.calculateKPartition(g, k,
				new ArrayList<Integer>(Arrays.asList(roots)), 
				new ArrayList<Integer>(Arrays.asList(partitionSizes)));
		System.out.println(p);
		
	}

	/** return the graph presented in the figure 2 of the paper, numbered like this
	 *    --0--
	 *   /  |  \
	 *  /   |   \
	 * 5    |    1
	 * | \  |  / |
	 * |    6    |
	 * | /  |  \ |
	 * 4    |    2
	 *  \   |   /
	 *   \  |  /
	 *    --3--
	 * 
	 * */
	public static Graph<Integer, Graph.Edge<Integer>> figure2Graph(){
		Graph<Integer, Graph.Edge<Integer>> g = new MultiGraph<Integer, Graph.Edge<Integer>>();
		for (int i = 0; i < 7; i++)
			g.addVertex(i);
		for (int i = 0; i < 5; i++){
			g.addEdge(new DirectedEdge<Integer>(i,i+1));
			g.addEdge(new DirectedEdge<Integer>(i+1,i));
		}
		g.addEdge(new DirectedEdge<Integer>(0,5));
		g.addEdge(new DirectedEdge<Integer>(5,0));
		for (int i = 0; i < 6; i++){
			g.addEdge(new DirectedEdge<Integer>(i,6));
			g.addEdge(new DirectedEdge<Integer>(6,i));
		}
		return g;
	}

	public static boolean randomTest(){
		int k = 2;
		Graph<Integer, Graph.Edge<Integer>> g = KConnectivity.genNComponent(4, 3, k);
		List<Integer> vertices = new ArrayList<Integer>(g.vertices());
		Collections.shuffle(vertices);
		List<Integer> roots = new ArrayList<Integer>();
		List<Integer> partitionSizes = new ArrayList<Integer>();
		int nbVerticesRemaining = vertices.size();
		for (int i = 0; i < k; i++){
			roots.add(vertices.get(i));
			int availableVertices = nbVerticesRemaining + i - k - 1;
			int pSize = new Random().nextInt(availableVertices) +  1;
			if (i == k -1) pSize = nbVerticesRemaining;
			partitionSizes.add(pSize);
			nbVerticesRemaining -= pSize;
		}
		List<Set<Integer>> p;
		p = Partitioning.calculateKPartition(g, k, roots, partitionSizes);
		if(p != null)
		{
			return Partitioning.isValidPartition(p, g, k, roots, partitionSizes);
		}
		else
		{
			return false;
		}
	}
}
