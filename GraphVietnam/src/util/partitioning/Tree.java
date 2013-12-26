package util.partitioning;

import java.util.Collection;
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

	public Tree() {
		root = null;
		fathers = new HashMap<V,V>();
		childs = new HashMap<V,Set<V>>();
	}

	public Tree(V root) {
		this();
		this.root = root;
		fathers.put(root, null);
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
	
	/** Return all the descendants of root in the tree, root not included */
	private Collection<V> descendants(V root){
		Collection<V> result = new HashSet<V>();
		for (V child : childs.get(root)){
			result.addAll(descendants(child));
			result.add(child);
		}
		return result;
	}
	
	/** Remove and return the subtree rooted at the given vertex
	 *  Forbidden to cut a Tree root */
	public Tree<V> cutoff(V root){
		Tree<V> finalSubTree = new Tree<V>();
		// Removing childs subtrees and adding it to the new tree
		finalSubTree.root = root;
		finalSubTree.childs.put(root, childs.get(root));
		finalSubTree.fathers.put(root, null);
		for (V d : descendants(root)){
			finalSubTree.childs.put(d, childs.get(d));
			finalSubTree.fathers.put(d, fathers.get(d));
			childs.remove(d);
			fathers.remove(d);
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
