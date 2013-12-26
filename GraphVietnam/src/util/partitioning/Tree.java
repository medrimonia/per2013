package util.partitioning;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import graph.Graph;

/**
 * An ad-hoc class that will be used only to apply the partitioning algorithm
 * That's why this class is not useable outside of the package
 */
class Tree<V> {
	
	private V root;
	/** fathers.get(i) = father of i, fathers.get(root) = null */
	private HashMap<V,V> fathers;
	private HashMap<V,Set<V>> childs;

	public Tree(V root) {
		this.root = root;
		fathers = new HashMap<V,V>();
		fathers.put(root, null);
		childs = new HashMap<V,Set<V>>();
		childs.put(root, new HashSet<V>());
	}

	public Set<V> vertices() {
		return childs.keySet();
	}
	
	public void addChild(V father, V child){
		childs.get(father).add(child);
		childs.put(child, new HashSet<V>());
		fathers.put(child, father);
	}
	
	/** Insert the given subTree as a child of root */
	private void insertSubTree(V root, Tree<V> subTree){
		//TODO assert no vertices in common
		for (Entry<V,Set<V>> e : subTree.childs.entrySet()){
			childs.put(e.getKey(), e.getValue());
		}
		for (Entry<V,V> e : subTree.fathers.entrySet()){
			fathers.put(e.getKey(), e.getValue());
		}
		fathers.remove(subTree.root);
		fathers.put(subTree.root, root);
		childs.get(root).add(subTree.root);
	}
	
	//TODO : watch out the complexity, not optimal at all
	/** Remove and return the subtree rooted at the given vertex
	 *  Forbidden to cut a Tree root */
	public Tree<V> cutoff(V root){
		Tree<V> finalSubTree = new Tree<V>(root);
		// Removing childs subtrees and adding it to the new tree
		for (V child : childs.get(root)){
			Tree<V> childSubTree = cutoff(child);
			finalSubTree.insertSubTree(root, childSubTree);
		}
		// Removing root from current Tree
		V father = fathers.get(root);
		childs.get(father).remove(root);
		fathers.remove(root);
		childs.remove(root);
		return finalSubTree;
	}
	
	/** Remove a vertex and all it's children from the tree */
	public void remove(V v){
		cutoff(v);
	}
	
	public int internalDegree(V v){
		int nbVertices = childs.get(v).size();
		if (fathers.get(v) != null) nbVertices++;
		return nbVertices;
	}
	
	public V lowestDegreeVertex(Set<V> vertices){
		V bestVertex = null;
		int minDegree = Integer.MAX_VALUE;
		for (V v : vertices){
			int deg = internalDegree(v);
			if (deg < minDegree){
				minDegree = deg;
				bestVertex = v;
			}
		}
		return bestVertex;
	}
	
	//TODO not nicest method but helps for debug
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		for (V v : vertices()){
			sb.append(v);
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

}
