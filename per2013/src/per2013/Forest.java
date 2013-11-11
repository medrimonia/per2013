package per2013;

/** This class implement the algorithm described in the article of
 * Hiroshi Nagamochi and Toshihide Ibaraki called :
 * "A linear-time algorithm for finding a sparse k-connected
 *  spanning subgraph of a k-connected graph"
 * @author ludovic
 */

import java.util.ArrayList;
import java.util.List;

import cnrs.grph.set.HashIntSet;
import cnrs.grph.set.IntSet;
import grph.Grph;

public class Forest {
	
	/** Each E_i contains a set of edges identifier */
	private IntSet[] edges;
	
	private boolean[] edgeIsScanned;
	
	private ForestItem[] items;
	
	private ForestBucketCollection unscannedNodes;
	
	public Forest(Grph g){
		int nbEdges = g.getNumberOfEdges();
		int nbVertices = g.getVertices().size();
		edges = new IntSet[nbEdges];
		edgeIsScanned = new boolean[nbEdges];
		for (int i = 0; i < nbEdges; i++){
			edges[i] = new HashIntSet();
			edgeIsScanned[i] = false;
		}
		unscannedNodes = new ForestBucketCollection(nbVertices);
		for (int i = 0; i < nbVertices; i++){
			ForestItem item = new ForestItem(i, 0);
			items[i] = item;
			unscannedNodes.place(item);
		}
		run(g);
	}
	
	private List<Integer> incidentUnscannedEdges(Grph g, int vertexId){
		IntSet incidentEdges = g.getEdgesIncidentTo(vertexId);
		List<Integer> result = new ArrayList<Integer>();
		for (int n : incidentEdges.toIntArray()){
			if (!edgeIsScanned[n]){
				result.add(n);
			}
		}
		return result;
	}

	private void run(Grph g){
		while (!unscannedNodes.isEmpty()){
			ForestItem x = unscannedNodes.pollMaxVertex();
			for (int edge : incidentUnscannedEdges(g, x.vertexId)){
				ForestItem y = null;
				for (int vertexId : g.getVerticesIncidentTo(edge).toIntArray()){
					if (vertexId != x.vertexId){
						y = items[vertexId];						
					}
				}
				edges[y.rValue + 1].add(edge);
				if (x.rValue == y.rValue) x.rValue++;
				unscannedNodes.increment(y.vertexId);
				edgeIsScanned[edge] = true;
			}
		}
	}

}
