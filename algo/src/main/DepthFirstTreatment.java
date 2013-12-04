package main;

import graph.Graph;

public class DepthFirstTreatment {

	public DepthFirstTreatment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void traversal (Operation op, Graph graph, Vertex root) {
		
	}
	public interface Operation {
		public abstract void visit(Vertex v);
	}
}
