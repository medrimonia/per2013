import graph.Graph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import util.Graphs;
import util.Graphs2;

public class TestComplexity {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileWriter f = new FileWriter(args[0]);
		for (int i = 10; i < 1000; i += 10) {
			Graph<Integer, Graph.Edge<Integer>> g = Graphs2
					.randomDirectedGraph(i, 0.1);
			Date d1 = new Date();
			Graphs.breadthFirstSearch(g, 0);
			Date d2 = new Date();
			f.write(Long.toString(d2.getTime() - d1.getTime()));
			f.write(";");
			if (i % 100 == 0) 
				System.out.println(i);
		}
		f.close();
		System.out.println("The End !");
	}

}
