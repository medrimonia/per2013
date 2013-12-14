package partioning;

public class Vertex {
	int n;

	@Override
	public boolean equals(Object v){
		return super.equals(v) || 
				(v instanceof Vertex 
						&& ((Vertex)v).getValue() == n); 
	}
	/**
	 * 
	 */
	public int getValue(){
		return n;
	}
	/**
	 * 
	 * @param value
	 */
	public void setValue(int value){
		n = value;
	}	
}