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

/**
 * @author ayoub
 *
 */
public class Partition {
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
		/**
		if(args.length == 1){
			int k = Integer.parseInt(args[1]);
		}else{
			throw new IllegalArgumentException();
		}
		 */
		assert(sizes.length == k && roots .length == k);
		
		//Initialization//
		//Graph graph = new MultiGraph<Vertex, Edge<Vertex>>();
		List<PartitionSpanningTree> listTrees = new ArrayList<PartitionSpanningTree>();
		List<List<Vertex>> Tree_Node = new ArrayList<List<Vertex>>();
		int N = graph.size();
		float[] P = new float[N];
		for (int i = 0; i < k; i++) {
			int ni = sizes[i];
			Vertex ai = roots[i];
			listTrees.set(i, new PartitionSpanningTree(graph,null, ai));
			//subtree Ti has one vertex ai as its root//(
			Tree_Node.set(i, new ArrayList<Vertex>()); 
			Tree_Node.get(i).add(ai);
			P[i] = ni;
		}
		for(Vertex j : graph.vertices()){
			if (!arrayContain(roots,j)){
				Tree_Node.set(j.n, new ArrayList<Vertex>());
				P[j.n] = 0;
			}
		}

		//i := 1; / / i is the index of subtree Ti to be expanded//
		//Calculation//
		int i = 0;
		while(sum(sizes) < N){
			Vertex ai = roots[i];
			Integer ni = sizes[i];
			PartitionSpanningTree Ti = listTrees.get(i);
			if(P[ai.n] > 1){
				Set<Vertex> auxv = new HashSet<Vertex>();
				for(Vertex v : graph.vertices()){
					if(!arrayContain(roots, v)
							|| !Ti.containsVertex(v))
						auxv.add(v);
				}
				Set<Vertex> NEW = new HashSet<Vertex>();
				Set<Vertex> OLD = new HashSet<Vertex>();
				for(Vertex v : auxv){
					if(Tree_Node.get(i).isEmpty()){
						NEW.add(v);
					}else{
						OLD.add(v);
					}
				}
				if(!NEW.isEmpty()){
					if(NEW.size() > ni-Ti.size()){
						int j = 0;
						for (Vertex v : NEW) {
							j++;
							Ti.addVertex(v);
							if(j==ni-Ti.size()) break;
						}
						for(Vertex v : Ti.vertices()){
							Tree_Node.get(v.n).add(ai);
							P[v.n] = 1;
						}
					}else{
						Ti.addAllVertex(NEW);
						for(Vertex v : Ti.vertices()){
							Tree_Node.get(v.n).add(ai);
							P[v.n] = ni/Ti.size();
						}
					}
				}else{

					Set<Vertex> OLD1 = new HashSet<Vertex>();
					for(Vertex v : OLD){
						if(!Tree_Node.get(v.n).contains(ai)){
							OLD1.add(v);
						}
					}
					if(OLD1.isEmpty()){
						i= i % k+1;
						break;
					}
					Set<Vertex> OLD2 = new HashSet<Vertex>();
					float[] P1 = new float[P.length];
					for(Vertex v : OLD1)
						P1[v.n] = P[v.n];

					for(Vertex v : OLD1){
						if(P[v.n] == arrayMin(P1)){
							OLD2.add(v);
						}
					}
					// there is no subtree adjacent to Ti whose vcrtex function P value is less than t h a t of Ti//
					boolean b = false;
					for(Vertex v : OLD2)
						if(P[v.n] >= P[ai.n]){
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
							if(Tree_Node.get(v.n).contains(roots[j]))
								break;
					}
					Vertex aj = roots[j];
					Set<Vertex> OLD3 = new HashSet<Vertex>();
					for(Vertex v : OLD2){
						if(Tree_Node.get(v.n).contains(aj))
							OLD3.add(aj);
					}
					final PartitionSpanningTree Tj = listTrees.get(j);
					Vertex w = minVertex(OLD3, new Comparator<Vertex>() {
						@Override
						public int compare(Vertex arg0, Vertex arg1) {
							return Tj.degree(arg0) - Tj.degree(arg1);
						}
					});
					if(Tj.degree(w) == 1){
						Tj.removeVertex(w);
						Ti.addVertex(w);
						Tree_Node.get(w.n).add(ai);
					}else{
						Tj.cutOff(w, Ti);
						Tree_Node.get(w.n).add(ai);
						for(Vertex v : Tj.vertices()){
							P[v.n] = ni/Ti.size();
						}
						for(Vertex v : Ti.vertices()){
							P[v.n] = sizes[j]/Tj.size();
						}
					}

				}

			}
			i = i % k+1;
		}


	}	
	private static Collection<? extends Vertex> cutOff(Set<Vertex> tj, Vertex w) {
		// TODO Auto-generated method stub
		return null;
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
