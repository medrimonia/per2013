package util;

import graph.Graph;

@SuppressWarnings("serial")
public class NegativeEdgeException extends Exception {
	private Graph.Edge<?> e;
	private double weight;
	
	public NegativeEdgeException(Graph.Edge<?> e, double weight) {
		this.e = e;
		this.weight = weight;
	}
	
	public Graph.Edge<?> edge() {
		return e;
	}
	
	public double weight() {
		return weight;
	}
	
	public String getMessage() {
		return "Edge " + e + " with a negative weight " + weight; 
	}
}
