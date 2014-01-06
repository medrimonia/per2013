package util.forest;

public class ForestItem <V> {
	
	public ForestItem(V vertex, int rValue) {
		this.vertex = vertex;
		this.rValue = rValue;
	}
	public V vertex;
	public int rValue;
	
	public String toString(){
		return "[" + vertex + "," + rValue + "]";
	}

}
