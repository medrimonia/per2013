package util.partitioning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import graph.Graph;

//TODO: ref to the article
//TODO: commentary: choosing to work in 0 to k-1 and not in 1 to k
public class Partitioning<V, E extends Graph.Edge<V>>{

	/**
	 * The graph we're working on
	 */
	private Graph<V,E> g;
	/**
	 * The connectivity of the graph (vertex based)
	 */
	private int k;
	/**
	 * each root correspond to a different partition
	 */
	private ArrayList<V> rootVertices;
	/**
	 * @see rootVertices
	 */
	private int[] partitionSizes;
	/**
	 * Allows to retrieve the index of a Vertex easily
	 */
	private HashMap<V, Integer> indexMapping;
	/**
	 * array of size k
	 * a_j is in treeNode[i] if and only if vertex with index i is or has been
	 * in T[j]
	 */
	private ArrayList<HashSet<V>> treeNode;
	/**
	 * Size |V(g)|:
	 * value of any vertex in T[i] = partitionSizes[i] / |T[i].nbVertices()|
	 */
	private double[] p;
	/**
	 * The trees associated to the rootVertices
	 */
	private ArrayList<Tree> trees;
	
	/**
	 * This class should only be used by static method, that's the reason of
	 * the private
	 * Order in roots and partitionSizes matter, that's why generic Collections
	 * can't be used.
	 */
	private Partitioning(Graph<V,E> g, int k,
			List<V> roots, List<Integer> partitionSizes){
		//TODO exceptions on invalid size etc...
		/* Initializing Resources */
		int n = g.vertices().size();
		this.g = g;
		this.k = k;
		rootVertices = new ArrayList<V>(k);
		this.partitionSizes = new int[k];
		indexMapping = new HashMap<V, Integer>();
		treeNode = new ArrayList<HashSet<V>>(n);
		p = new double[n];
		trees = new ArrayList<Tree>(k);
		// Importing content
		for (int i = 0; i < k; i ++){
			rootVertices.set(i, roots.get(i));
			this.partitionSizes[i] = partitionSizes.get(i);
		}
		initMapping();
		
	}
	
	private void initMapping(){
		int vertexId = 0;
		indexMapping.clear();// Just in case
		for (V vertex : g.vertices()){
			indexMapping.put(vertex, vertexId);
			vertexId++;
		}
	}

	private void initialize(){
		for (int i = 0; i < k; i++){
			trees.set(i, new Tree(rootVertices.get(i)));
		}
		// default values
		for (int i = 0; i < g.vertices().size(); i++){
			p[i] = 0;
			treeNode.set(i, new HashSet<V>());
		}
		// roots values
		for (V root : rootVertices){
			int rootIndex = indexMapping.get(root);
			p[rootIndex] = partitionSizes[rootIndex];
			treeNode.get(rootIndex).add(root);
		}
	}

}
