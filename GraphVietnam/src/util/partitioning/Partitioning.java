package util.partitioning;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	 * Using double because partitionSize is generally divided by int
	 * @see rootVertices
	 */
	private double[] partitionSizes;
	/**
	 * Allows to retrieve the index of a Vertex easily
	 */
	private HashMap<V, Integer> indexMapping;
	/**
	 * array of size k
	 * a_j is in treeNode[i] if and only if vertex with index i is or has been
	 * in T[j]. Warning, treeNode[i] can be reseted through the execution
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
	
	private PrintWriter debugFile;
		
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
		this.partitionSizes = new double[k];
		indexMapping = new HashMap<V, Integer>();
		treeNode = new ArrayList<HashSet<V>>(n);
		p = new double[n];
		trees = new ArrayList<Tree<V>>(k);
		// Importing content
		for (int i = 0; i < k; i ++){
			rootVertices.add(i, roots.get(i));
			this.partitionSizes[i] = partitionSizes.get(i);
		}
		initMapping();
		try {
			debugFile = new PrintWriter("debug.txt", "UTF-8");
		} catch (Exception e) {
			System.err.println("Failed to open file");
		}
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
		trees.clear();
		for (int i = 0; i < k; i++){
			trees.add(i, new Tree<V>(rootVertices.get(i)));
		}
		treeNode.clear();
		// default values
		for (int i = 0; i < g.vertices().size(); i++){
			p[i] = 0;
			treeNode.add(i, new HashSet<V>());
		}
		// roots values
		for (int i = 0; i < k; i++ ){
			V root = rootVertices.get(i);
			int rootIndex = indexMapping.get(root);
			p[rootIndex] = partitionSizes[i];
			treeNode.get(rootIndex).add(root);
		}
	}

	/** Class must have been initialized @see initialize */
	private boolean compute(){
		int lastswap = -1;
		int rootIndex = 0;
		boolean updated = false;
		while(nbVerticesUsed() < g.vertices().size()){
			debugFile.println("rootIndex : " + rootIndex);
			Integer rootId = indexMapping.get(rootVertices.get(rootIndex));
			if (p[rootId] > 1){
				Set<V> auxiliaryVertices = getAuxiliaryVertices(rootIndex);
				debugFile.println("\t" + auxiliaryVertices);
				Set<V> unknownVertices = filterUnknownVertices(auxiliaryVertices);//NEW in paper
				Set<V> knownVertices = filterKnownVertices(auxiliaryVertices);//OLD in paper
				if (!unknownVertices.isEmpty()){
					if(addNeededVertices(unknownVertices, rootIndex))
					{
						lastswap = rootIndex;
						updated = true;
					}
					else
					{
						updated = false;
					}
				}
				else{
					if(tryVerticesSwap(knownVertices, rootIndex))
					{
						lastswap = rootIndex;
						updated = true;
					}
					else
					{
						updated = false;
					}
				}
			}
			debugFile.println("\t" + trees);
			rootIndex = (rootIndex + 1) % k;
			if(lastswap == rootIndex && !updated)
			{
				return false;
			}
		}
		return true;
	}
	
	/** Algorithm must have been computed @see compute */
	private List<Set<V>> getResult(){
		List<Set<V>> result = new ArrayList<Set<V>>();
		for (int i = 0; i < k; i++){
			result.add(i, trees.get(i).vertices());
		}
		return result;
	}

	public static <V, E extends Graph.Edge<V>> List<Set<V>> calculateKPartition(
			Graph<V,E> g,
			int k,
			List<V> roots,
			List<Integer> partitionSizes){
		Partitioning<V,E> p = new Partitioning<V,E>(g, k, roots, partitionSizes);
		p.initialize();
		if(p.compute())
		{
			return p.getResult();
		}
		else
		{
			return null;
		}		
	}
	
	/** Return true if the partition is valid according to given parameters */
	public static <V, E extends Graph.Edge<V>> boolean isValidPartition(
			List<Set<V>> partition,
			Graph<V,E> g,
			int k,
			List<V> roots,
			List<Integer> partitionSizes){
		Collection<V> usedVertices = new HashSet<V>();
		// Every element should be in g.vertices and should be used only once
		for (int i = 0; i < k; i++){
			Set<V> s = partition.get(i);
			for (V v : s){
				// v in graph
				if (!g.vertices().contains(v))
					return false;
				// v used only once
				if (!usedVertices.add(v))
					return false;
			}
			//Each subgraph must have the specified size
			if (partitionSizes.get(i) != s.size())
				return false;
			//Each root should be in the corresponding subgraph
			if (!s.contains(roots.get(i)))
				return false;
		}
		return true;
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

	private boolean addNeededVertices(Set<V> unknownVertices, int rootIndex){
		double wishedSize = partitionSizes[rootIndex];
		int actualSize = trees.get(rootIndex).vertices().size();
		// Need to got through a list to shuffle
		List<V> toAdd = new ArrayList<V>(unknownVertices);
		Collections.shuffle(toAdd);
		// Removing elements if there's too much
		while(toAdd.size() > wishedSize - actualSize){
			toAdd.remove(0);
		}
		//int finalSize = actualSize + toAdd.size();
		for (V v : toAdd){
			int vertexId = indexMapping.get(v);
			addVertexToTree(v, rootIndex);
			treeNode.get(vertexId).add(rootVertices.get(rootIndex));
		}
		updatePValues(rootIndex);
		return toAdd.size() > 0;
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

	private boolean tryVerticesSwap(Set<V> knownVertices, int rootIndex) {
		Set<V> knownVertices1 = new HashSet<V>();//OLD1 in paper
		// Removing vertices that have already been once in T[rootIndex]
		V currentRoot = rootVertices.get(rootIndex);//a_i in the paper
		for (V v : knownVertices){
			int vertexId = indexMapping.get(v);
			if (!treeNode.get(vertexId).contains(currentRoot))
				knownVertices1.add(v);
		}
		// If all vertices have already been in T[rootIndex], swap is impossible
		//TODO: when entering in an infinite loop, algorithm jumps out at this condition
		if (knownVertices1.isEmpty()) return false;
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
		//V w = trees.get(j).lowestDegreeVertex(knownVertices3);
		V w = trees.get(j).lowestDescendanceVertex(knownVertices3);
		int wId = indexMapping.get(w);
		//TODO If and else seems redundant: to check
		Tree<V> Tj = trees.get(j);
		Tree<V> Ti = trees.get(rootIndex);
		if (Tj.internalDegree(w) == 1){
			Tj.remove(w);
			addVertexToTree(w, rootIndex);
			treeNode.get(wId).add(rootVertices.get(rootIndex));
			//TODO : part not in the algo!!!
			// Update T_i and T_j p values
			updatePValues(rootIndex);
			updatePValues(j);
		}
		else{
			Tree<V> tPrim = Tj.cutoff(w);
			treeNode.get(wId).add(rootVertices.get(rootIndex));
			addVertexToTree(w, rootIndex);
			for (V v : tPrim.vertices()){
				if (v.equals(w)) continue;
				int vertexId = indexMapping.get(v);
				treeNode.get(vertexId).clear();
				p[vertexId] = 0;
			}
			updatePValues(rootIndex);
			updatePValues(j);			
		}
		return true;
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
	
	private void updatePValues(int rootIndex){
		Tree<V> T = trees.get(rootIndex);
		double P = partitionSizes[rootIndex] / T.vertices().size();
		for (V v : T.vertices()){
			int vertexId = indexMapping.get(v);
			p[vertexId] = P;
		}
		
	}

}
