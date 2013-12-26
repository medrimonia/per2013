package partioning.graphutils;

public class Vertex {
	public int rankInGraph;
	public boolean visited = false;
	@Override
	public boolean equals(Object v){
		return super.equals(v) || 
				(v instanceof Vertex 
						&& ((Vertex)v).rankInGraph == rankInGraph); 
	}
}