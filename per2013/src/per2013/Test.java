package per2013;

import cnrs.grph.set.IntSet;
import cnrs.grph.set.ListSet;
import grph.Grph;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Grph g = new Grph();
		g.addNVertices(3);
		g.addSimpleEdge(0,1,false);
		g.addSimpleEdge(0,2,false);
		g.addSimpleEdge(1,2,false);
		g.display();
		IntSet[] partition = new IntSet[2];
		partition[0] = new ListSet();
		partition[1] = new ListSet();
		partition[0].add(0);
		partition[1].add(1);
		partition[1].add(2);
		int[] wishedVertices = {0,1};
		int[] wishedSize = {1,2}; 
		boolean isPartition = Utils.isKPartition(g, 2, wishedVertices, wishedSize, partition);
		System.out.print("Is k-partition ? ");
		System.out.println(isPartition);
	}

}
