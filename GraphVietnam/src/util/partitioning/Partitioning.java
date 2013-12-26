package util.partitioning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private ArrayList<Tree<V>> trees;
	
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
		trees = new ArrayList<Tree<V>>(k);
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

	private void compute(){
		int rootIndex = 0;
		while(nbVerticesUsed() < g.vertices().size()){
			if (p[rootIndex] > 1){
				Set<V> auxiliaryVertices = getAuxiliaryVertices(rootIndex);
				Set<V> unknownVertices = filterUnknownVertices(auxiliaryVertices);//NEW in paper
				Set<V> knownVertices = filterKnownVertices(auxiliaryVertices);//OLD in paper
				if (!unknownVertices.isEmpty()){
					addNeededVertices(unknownVertices, rootIndex);
				}
				else{
					tryVerticesSwap(knownVertices, rootIndex);
				}
			}
			rootIndex = (rootIndex + 1) % k;
		}
	}

	private int nbVerticesUsed(){
		int total = 0;
		for (Tree<V> t : trees){
			total += t.vertices().size();
		}
		return total;
	}
	
	private Set<V> getAuxiliaryVertices(int rootIndex){
		// Union of V(T[rootIndex) and rootVertices is forbidden
		HashSet<V> forbidden = new HashSet<V>();
		for (V v : trees.get(rootIndex).vertices()){
			forbidden.add(v);
		}
		for (V v : rootVertices){
			forbidden.add(v);
		}
		// Computing neighborhood
		HashSet<V> neighborhood = new HashSet<V>();
		for (V v : trees.get(rootIndex).vertices()){
			for (V neighbor : g.neighbors(v)){
				neighborhood.add(neighbor);
			}
		}
		// Removing forbidden from neighborhood
		for (V v : forbidden){
			neighborhood.remove(v);
		}
		return neighborhood;
	}
	
	private Set<V> filterUnknownVertices(Collection<V> vertices){
		Set<V> filtered = new HashSet<V>();
		for (V v : vertices){
			int vIndex = indexMapping.get(v);
			if (treeNode.get(vIndex).isEmpty())
				filtered.add(v);
		}
		return filtered;
	}
	
	private Set<V> filterKnownVertices(Collection<V> vertices){
		Set<V> filtered = new HashSet<V>();
		for (V v : vertices){
			int vIndex = indexMapping.get(v);
			if (!treeNode.get(vIndex).isEmpty())
				filtered.add(v);
		}
		return filtered;
	}

	private void addNeededVertices(Set<V> unknownVertices, int rootIndex){
		int wishedSize = partitionSizes[rootIndex];
		int actualSize = trees.get(rootIndex).vertices().size();
		// Need to got through a list to shuffle
		List<V> toAdd = new ArrayList<V>(unknownVertices);
		Collections.shuffle(toAdd);
		// Removing elements if there's too much
		while(toAdd.size() > wishedSize - actualSize){
			toAdd.remove(0);
		}
		int finalSize = actualSize + toAdd.size();
		for (V v : toAdd){
			int vertexId = indexMapping.get(v);
			addVertexToTree(v, rootIndex);
			treeNode.get(vertexId).add(rootVertices.get(rootIndex));
			p[vertexId] = wishedSize / (double) finalSize;
		}
		
	}
	
	private void addVertexToTree(V v, int rootIndex) {
		//Father must be connected to v
		V father = null;
		for (V treeVertex : trees.get(rootIndex).vertices()){
			if (g.areNeighbors(v, treeVertex)){
				father = treeVertex;
				break;
			}
		}
		trees.get(rootIndex).addChild(father, v);
	}

	private void tryVerticesSwap(Set<V> knownVertices, int rootIndex) {
		Set<V> knownVertices1 = new HashSet<V>();//OLD1 in paper
		// Removing vertices that have already been once in T[rootIndex]
		V currentRoot = rootVertices.get(rootIndex);//a_i in the paper
		for (V v : knownVertices){
			int vertexId = indexMapping.get(v);
			if (!treeNode.get(vertexId).contains(currentRoot))
				knownVertices1.add(v);
		}
		// If all vertices have already been in T[rootIndex], swap is impossible
		if (knownVertices1.isEmpty()) return;
		Set<V> knownVertices2 = filterMinPVertices(knownVertices1);//OLD2
		//TODO: here, is it a_j in Tree_node[v] or v in T_j??? 
		int j = 0;
		Set<V> knownVertices3 = new HashSet<V>();
		for (int t = 1; t < k; t++){
			j = (rootIndex + t) % k;
			for (V v : knownVertices2){
				if (trees.get(j).vertices().contains(v)){
					knownVertices3.add(v);
				}
			}
			if (!knownVertices3.isEmpty()) break;
		}
	}
	
	private Set<V> filterMinPVertices(Set<V> knownVertices){
		Set<V> result = new HashSet<V>();
		double minP = Double.MAX_VALUE;
		for (V v: knownVertices){
			int vertexId = indexMapping.get(v);
			if (p[vertexId] == minP){
				result.add(v);
			}
			if (p[vertexId] < minP){
				result.clear();
				minP = p[vertexId];
				result.add(v);
			}
		}
		return result;
	}

}
