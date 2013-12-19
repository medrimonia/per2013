/**
 * 
 */
package partioning.graphutils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import util.DepthFirstSearch;
import util.RootedSpanningTreeImpl;
import graph.Graph;
import graph.Graph.Edge;
import graph.PartialGraph;
/**
 * @author ayoub
 *
 */
public class PartitionSpanningTree extends RootedSpanningTreeImpl<Vertex, Edge<Vertex>> {
	/**
	 * Non thread - safe
	 */
	protected PartialGraph< Vertex, Edge<Vertex>> tree;
	private Map<Vertex,Integer> degrees;

	public PartitionSpanningTree(Graph<Vertex, graph.Graph.Edge<Vertex>> graph,
			PartialGraph<Vertex, graph.Graph.Edge<Vertex>> tree, Vertex root) {
		super(graph, tree, root);
		this.tree = tree;
	}

	@Override
	public boolean addVertex(Vertex vertex) {
		return tree.addVertex(vertex);
	}
	@Override
	public boolean removeVertex(Vertex vertex) {
		return tree.removeVertex(vertex);
	}

	@Override
	public boolean addEdge(Edge<Vertex> edge){
		return tree.addEdge(edge);
	}
	@Override
	public boolean removeEdge(Edge<Vertex> edge){
		return tree.removeEdge(edge);
	}
	 


	public void cutOff(Vertex root){
		new DepthFirstTreatment().traversal(new DepthFirstTreatment.Operation() {
			@Override
			public void visit(Vertex v) {
				removeVertex(v);				
			}
		}, this, root);
}

public int degree (Vertex x){
	if(degrees.containsKey(x))
		return degrees.get(x);
	int i = 0;
	Iterator<Vertex> levelVertices = levelVertices(vertexLevel(x));
	while(levelVertices.hasNext()){
		degrees.put(levelVertices.next(),i++);
	}
	return degrees.get(x);
}

public void addAllVertex(Set<Vertex> newSetofVetices) {
	for(Vertex v : newSetofVetices){
		addVertex(v);
	}
	
}


}
