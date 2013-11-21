package util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class NegativeCircuitException extends Exception {

	private List<?> circuit;
	
	public NegativeCircuitException(List<?> circuit) {
		this.circuit = new ArrayList<Object>(circuit);
	}
	
	public List<?> circuit() {
		return this.circuit;
	}
}
