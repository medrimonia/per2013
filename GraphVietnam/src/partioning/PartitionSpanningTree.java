/**
 * 
 */
package partioning;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.NVList;

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
		tree.addVertex(vertex);
		for(Edge<Vertex> edge : supergraph().incidentEdges(vertex))
			tree.addEdge(edge);
		super.addEdge(null);
		return true;
		//TODO DFS to switch all elements
	}
	@Override
	public boolean removeVertex(Vertex vertex) {
		throw new UnsupportedOperationException();
		//TODO
	}



	public PartitionSpanningTree cutOff(Vertex root, PartitionSpanningTree newTree){
		return new PartitionSpanningTree(supergraph(), tree, root);
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

public void addAllVertex(Set<Vertex> nEW) {
	for(Vertex v : nEW){
		addVertex(v);
	}
	
}


}
