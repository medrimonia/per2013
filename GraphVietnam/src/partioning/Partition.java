/**
 * 
 */
package partioning;
import graph.Graph;
import graph.Graph.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import partioning.graphutils.DepthFirstTreatment;
import partioning.graphutils.PartitionSpanningTree;
import partioning.graphutils.Vertex;

/**
 * @author ayoub 
 *
 */

public class Partition {
	/**
	 * Initialises the indexes of the graph's vertices
	 * @param graph
	 */
	private static void initGraph(Graph<Vertex, Edge<Vertex>> graph){
		Vertex root = graph.vertices().iterator().next();
		new DepthFirstTreatment().traversal(new DepthFirstTreatment.Operation() {
			int currentIndex = 0;
			@Override
			public void visit(Vertex v) {
				v.rankInGraph = currentIndex;
				currentIndex ++;
			}
		}, graph, root );
	}
	private static int sum(int[] sizes ){
		int sum = 0;
		for(Integer i : sizes)
			sum += i;
		return sum;
	}
	/**
	 * @param args
	 */
	public static void partition(int k, int[] sizes, Vertex[] roots,Graph<Vertex, Edge<Vertex>> graph) {
		assert(sizes.length == k && roots .length == k);
		initGraph(graph);
		//Initialization//
		List<PartitionSpanningTree> listTrees = new ArrayList<PartitionSpanningTree>();
		List<List<Vertex>> Tree_Node = new ArrayList<List<Vertex>>();
		int numberOfVerticesInGraph = graph.size();
		float[] weightFactors = new float[numberOfVerticesInGraph];
		for (int i = 0; i < k; i++) {
			int ni = sizes[i];
			Vertex rootOfithPartition = roots[i];
			listTrees.set(i, new PartitionSpanningTree(graph,null, rootOfithPartition));
			//subtree Ti has one vertex ai as its root//(
			Tree_Node.set(i, new ArrayList<Vertex>()); 
			Tree_Node.get(i).add(rootOfithPartition);
			weightFactors[i] = ni;
		}
		for(Vertex vertexOutTree : graph.vertices()){
			if (!arrayContain(roots,vertexOutTree)){
				Tree_Node.set(vertexOutTree.rankInGraph, new ArrayList<Vertex>());
				weightFactors[vertexOutTree.rankInGraph] = 0;
			}
		}

		//i := 1; / / i is the index of subtree Ti to be expanded//
		//Calculation//
		int i = 0;
		while(sum(sizes) < numberOfVerticesInGraph){
			Vertex rootOfithPartition = roots[i];
			Integer sizeOfithPartition = sizes[i];
			PartitionSpanningTree ithPartitionTree = listTrees.get(i);
			if(weightFactors[rootOfithPartition.rankInGraph] > 1){
				Set<Vertex> verticesNotinPartitions = new HashSet<Vertex>();
				for(Vertex v : graph.vertices()){
					if(!arrayContain(roots, v)
							|| !ithPartitionTree.containsVertex(v))
						verticesNotinPartitions.add(v);
				}
				Set<Vertex> verticesNeverBeenInPartitions = new HashSet<Vertex>();
				Set<Vertex> verticesWereInPartitions = new HashSet<Vertex>();
				for(Vertex v : verticesNotinPartitions){
					if(Tree_Node.get(i).isEmpty()){
						verticesNeverBeenInPartitions.add(v);
					}else{
						verticesWereInPartitions.add(v);
					}
				}
				if(!verticesNeverBeenInPartitions.isEmpty()){
					if(verticesNeverBeenInPartitions.size() > sizeOfithPartition-ithPartitionTree.size()){
						int j = 0;
						for (Vertex v : verticesNeverBeenInPartitions) {
							j++;
							ithPartitionTree.addVertex(v);
							if(j==sizeOfithPartition-ithPartitionTree.size()) break;
						}
						for(Vertex v : ithPartitionTree.vertices()){
							Tree_Node.get(v.rankInGraph).add(rootOfithPartition);
							weightFactors[v.rankInGraph] = 1;
						}
					}else{
						ithPartitionTree.addAllVertex(verticesNeverBeenInPartitions);
						for(Vertex v : ithPartitionTree.vertices()){
							Tree_Node.get(v.rankInGraph).add(rootOfithPartition);
							weightFactors[v.rankInGraph] = sizeOfithPartition/ithPartitionTree.size();
						}
					}
				}else{

					Set<Vertex> OLD1 = new HashSet<Vertex>();
					for(Vertex v : verticesWereInPartitions){
						if(!Tree_Node.get(v.rankInGraph).contains(rootOfithPartition)){
							OLD1.add(v);
						}
					}
					if(OLD1.isEmpty()){
						i= i % k+1;
						break;
					}
					Set<Vertex> OLD2 = new HashSet<Vertex>();
					float[] P1 = new float[weightFactors.length];
					for(Vertex v : OLD1)
						P1[v.rankInGraph] = weightFactors[v.rankInGraph];

					for(Vertex v : OLD1){
						if(weightFactors[v.rankInGraph] == arrayMin(P1)){
							OLD2.add(v);
						}
					}
					// there is no subtree adjacent to Ti whose vcrtex function P value is less than t h a t of Ti//
					boolean b = false;
					for(Vertex v : OLD2)
						if(weightFactors[v.rankInGraph] >= weightFactors[rootOfithPartition.rankInGraph]){
							b = true;
						}
					if(b){
						i= i % k+1;
						break;
					}
					int j = 0;
					for (int t = 0; t < k; t++) {
						j = (i+t) % (k+1);
						for(Vertex v : OLD2)
							if(Tree_Node.get(v.rankInGraph).contains(roots[j]))
								break;
					}
					Vertex aj = roots[j];
					Set<Vertex> OLD3 = new HashSet<Vertex>();
					for(Vertex v : OLD2){
						if(Tree_Node.get(v.rankInGraph).contains(aj))
							OLD3.add(aj);
					}
					final PartitionSpanningTree jthPartitionTree = listTrees.get(j);
					Vertex w = minVertex(OLD3, new Comparator<Vertex>() {
						@Override
						public int compare(Vertex arg0, Vertex arg1) {
							return jthPartitionTree.degree(arg0) - jthPartitionTree.degree(arg1);
						}
					});
					if(jthPartitionTree.degree(w) == 1){
						jthPartitionTree.removeVertex(w);
						ithPartitionTree.addVertex(w);
						Tree_Node.get(w.rankInGraph).add(rootOfithPartition);
					}else{
						jthPartitionTree.cutOff(w);
						Tree_Node.get(w.rankInGraph).add(rootOfithPartition);
						for(Vertex v : jthPartitionTree.vertices()){
							weightFactors[v.rankInGraph] = sizeOfithPartition/ithPartitionTree.size();
						}
						for(Vertex v : ithPartitionTree.vertices()){
							weightFactors[v.rankInGraph] = sizes[j]/jthPartitionTree.size();
						}
					}

				}

			}
			i = i % k+1;
		}


	}	


	protected static int degreeInTree(Set<Vertex> tj, Vertex arg0) {
		return 0;
	}


	private static float arrayMin(float[] p) {
		float min = p[0];
		for(float f : p){
			if(f < min)	min = f;
		}
		return min;
	}

	private static Vertex minVertex(Iterable<Vertex> it, Comparator<Vertex> c ){
		
		
		Vertex min = it.iterator().next();
		for(Vertex v : it){
			if(c.compare(v, min) < 0)min = v;
		}
		return min;
	}
	private static boolean arrayContain(Vertex[] roots, Vertex j) {

		return Arrays.binarySearch(roots, j) == -1;
	}
	//TODO SELECT PREDICATE
}
