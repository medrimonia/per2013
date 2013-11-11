package per2013;

import grph.Grph;

import cnrs.grph.set.IntSet;

public class Utils {

	public static boolean isKPartition(
			Grph g,
			int k,
			int[] wishedVertices,
			int[] wishedSize,
			IntSet[] partition)
	{
		// |partition| = k
		if (partition.length != k)
			return false;
		if (!isPartition(g, wishedVertices, partition))
			return false;
		// Checking that each partition has the right number of vertices
		for (int i = 0; i < wishedSize.length; i++){
			if (wishedSize[i] != partition[i].size())
				return false;
		}
		return true;
	}
	
	public static boolean isPartition(
			Grph g,
			int[] wishedVertices,
			IntSet[] partition)
	{
		// ai in Vi
		for (int i = 0; i < wishedVertices.length; i++){
			if (!partition[i].contains(wishedVertices[i]))
				return false;
		}
		// G[vi] is a connected subgraph of G
		for (int i = 0; i < partition.length; i++){
			Grph subGrph = g.getSubgraphInducedByVertices(partition[i]);
			if (subGrph.getNumberOfVertices() > 1 && !subGrph.isConnected())
				return false;
		}
		// No intersection
		boolean [] found = new boolean[g.getNumberOfVertices()];
		// initializing conditions
		for (int i = 0; i < found.length; i++) found[i] = false;
		int nbVertexFound = 0;
		// using all vertices from the partitions
		for (int i = 0; i < partition.length; i++){
			for (int vertex : partition[i].toIntArray()){
				if (found[vertex]) return false;
				found[vertex] = true;
			}
			nbVertexFound += partition[i].size();
		}
		if (nbVertexFound != g.getNumberOfVertices())
			return false;
		return true;
	}
	
	public static String edgeIndexToString(Grph g, int edgeIndex){
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		for (int id : g.getVerticesIncidentTo(edgeIndex).toIntArray()){
			sb.append(id);
			sb.append(',');
		}
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}
	
	public static String edgeListToString(Grph g, IntSet list){
		StringBuffer sb = new StringBuffer();
		for (int edgeIndex : list.toIntArray()){
			sb.append(edgeIndexToString(g, edgeIndex));
		}
		return sb.toString();
	}

}