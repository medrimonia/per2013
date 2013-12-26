package partioning.graphutils;

import graph.Graph;
import graph.Graph.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstTreatment {

	private List<Integer> visited;
	public DepthFirstTreatment() {
		visited = new ArrayList<Integer>();
	}
	
	private void setVisited(Vertex vertex){
		 visited.add(vertex.rankInGraph);
	}
	private boolean getVisited(Vertex vertex) {
		return visited.contains(vertex.rankInGraph);
	}
	private Vertex getUnvisitedChild(Graph<Vertex,Edge<Vertex>> graph, Vertex vertex ){
		for(Vertex v : graph.neighbors(vertex))
			if(!getVisited(v))
				return v;
		return null;		
	}
	/**
	 * @param args
	 */
	public void traversal (Operation op, Graph<Vertex,Edge<Vertex>> graph, Vertex root) {
			Stack<Vertex> stack = new Stack<Vertex>();
			stack.push(root);
			setVisited(root);
			op.visit(root);
			while(!stack.isEmpty()) {
				Vertex node = stack.peek();
				Vertex child = getUnvisitedChild(graph, node);
				if(child != null) {
					setVisited(child);
					op.visit(child);
					stack.push(child);
				}
				else {
					stack.pop();
				}
			}
		}

	
	public interface Operation {
		public abstract void visit(Vertex v);
	}
}
