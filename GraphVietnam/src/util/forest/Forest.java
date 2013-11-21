package util.forest;

/** This class implement the algorithm described in the article of
 * Hiroshi Nagamochi and Toshihide Ibaraki called :
 * "A linear-time algorithm for finding a sparse k-connected
 *  spanning subgraph of a k-connected graph"
 * @author ludovic
 */

import graph.Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Forest<V, E extends Graph.Edge<V>>  {
	
	private HashMap<E, Boolean> edgeIsScanned;
	
	private HashMap<V, ForestItem<V>> items;
	
	private Set<E> edges[];
	
	private ForestBucketCollection<V> unscannedNodes;
	
	public Forest(Graph<V,E> g){
		edgeIsScanned = new HashMap<E, Boolean>();
		@SuppressWarnings("unchecked")
		Set<E> newEdges[] = new HashSet[g.edges().size()];
		edges = newEdges;
		for (int i = 0; i < g.edges().size(); i++)
			edges[i] = new HashSet<E>();
		for (E e : g.edges()){
			edgeIsScanned.put(e, false);
		}
		unscannedNodes = new ForestBucketCollection<V>(g.vertices());
		items = new HashMap<V, ForestItem<V>>();
		for (V vertex : g.vertices()){
			ForestItem<V> item = new ForestItem<V>(vertex, 0);
			items.put(vertex, item);
			unscannedNodes.place(item);
		}
		run(g);
	}
	
	private Set<E> incidentUnscannedEdges(Graph<V,E> g, V vertex){
		Set<E> result = new HashSet<E>();
		for (E e : g.incidentEdges(vertex)){
			if (!edgeIsScanned.get(e)){
				result.add(e);
			}
		}
		return result;
	}

	private void run(Graph<V,E> g){
		while (!unscannedNodes.isEmpty()){
			ForestItem<V> x = unscannedNodes.pollMaxVertex();
			for (E edge : incidentUnscannedEdges(g, x.vertex)){
				ForestItem<V> y = items.get(edge.getOpposite(x.vertex));
				edges[y.rValue + 1].add(edge);
				if (x.rValue == y.rValue) x.rValue++;
				unscannedNodes.increment(y.vertex);
				edgeIsScanned.remove(edge);
				edgeIsScanned.put(edge, true);
			}
		}
	}
	
	public void printE(Graph<V,E> g, int connectivity){
		for (int i = 0; i < connectivity; i++){
			System.out.println("E("+i+") : ");
			System.out.println("\t" + edges[i]);
		}
	}
}

