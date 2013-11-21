import graph.DirectedEdge;
import graph.Graph;
import graph.MultiGraph;

public class Test {
	static Graph<String, Graph.Edge<String>> g;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static Graph.Edge<String> E1;

	public static void main(String[] args) {
		// Graph ({1, 2, 3, 4}, {(1,2), (1,3), (2,1), (2,2), (3,2)})

		String[] vertices = { ONE, TWO, THREE, FOUR };
		String[][] edges = { { ONE, TWO }, { ONE, THREE }, { TWO, ONE },
				{ TWO, TWO }, { THREE, TWO } };
		g = new MultiGraph<String, Graph.Edge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));

		System.out.println("waiting for graph :");
		System.out.print("vertices =");
		for (String s : vertices)
			System.out.print(" " + s);
		System.out.println();
		System.out.print("edges =");
		for (String[] e : edges)
			System.out.print(" (" + e[0] + "," + e[1] + ")");
		System.out.println();
		System.out.println();

		System.out.println(g);

		System.out.print("Successors of " + TWO + " :");
		for (String v : g.successors(TWO))
			System.out.print(" " + v);
		System.out.println();

		for (String v : g.successors(TWO)) {
			System.out.println("removing " + v);
			g.removeVertex(v);
		}
		System.out.println(g);
	}
}
