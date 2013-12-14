package partioning.graphutils;

import graph.Graph;
import graph.Graph.Edge;

public class DepthFirstTreatment {

	public DepthFirstTreatment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void traversal (Operation op, Graph<Vertex,Edge<Vertex>> graph, Vertex root) {
		
	}
	public interface Operation {
		public abstract void visit(Vertex v);
	}
}
