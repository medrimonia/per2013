package per2013;

import java.util.ArrayList;
import java.util.Random;

import com.carrotsearch.hppc.cursors.IntCursor;

import grph.Grph;
import grph.path.MatrixWrappedVPath;


public class KConnectedGenerator {

	public static void main(String[] args) {
		Grph g = new Grph();
		g.addNVertices(4);
		g.addSimpleEdge(0,1,false);
		g.addSimpleEdge(0,2,false);
		g.addSimpleEdge(1,2,false);
		g.addSimpleEdge(1,3,false);
		g.addSimpleEdge(2,3,false);
		g.display();
		
		Grph g2 = VertexUnitCapacityGraph(g);
		g2.display();
		int maxFlow = UnitCapacityFordFulkersonMaxFlow(g2, 1, 6);
		System.out.println("maxFlow " + maxFlow);
		
		int minCutSize = miniunEdgeCutSize(g);
		System.out.println("minCutSize " + minCutSize);
		
		
		Grph g3 = generateGraphOfDegreeK(50,4);
		minCutSize = miniunEdgeCutSize(g3);
		System.out.println("g3: ");
		System.out.println("minCutSize " + minCutSize);
		g3.display();
		
		
		Grph g4 = generateGraphWith2Component(50,4);
		minCutSize = miniunEdgeCutSize(g4);
		System.out.println("g4: ");
		System.out.println("minCutSize " + minCutSize);
		g4.display();
	}
	
	public static Grph generateGraphWith2Component(int sizeComponent, int KConnected)
	{
		Grph g = new Grph();
		g.addNVertices(sizeComponent*2);
		for(int i=0; i<sizeComponent; i++)
		{
			for(int j=i+1; j<sizeComponent; j++)
			{
				for(int k=0; k<2; k++)
				{
					g.addSimpleEdge(i+sizeComponent*k,j+sizeComponent*k,false);
				}
			}
		}
		
		for(int i=0; i<KConnected; i++)
		{
			g.addSimpleEdge(i,i+sizeComponent,false);
		}
		
		
		return g;
	}
	
	public static Grph generateGraphOfDegreeK(int size, int k)
	{
		Grph g = new Grph();
		g.addNVertices(size);
		Random rand = new Random();
		
		ArrayList<int[]> edgeList = new ArrayList<int[]>();
		for(int x = 0; x < size; x++)
		{
			for(int y = x+1; y < size; y++)
			{
				int[] edge = new int[2];
				edge[0] = x;
				edge[1] = y;
				edgeList.add(edge);
			}
		}
				
		for(IntCursor i : g.getVertices())
		{	
			int j = g.getVertexDegree(i.value);
			while(j < k)
			{
				int r = rand.nextInt(size-j-1);
				
				for(int[] edge : edgeList)
				{
					if(edge[0] == i.value || edge[1] == i.value)
					{
						if(r == 0)
						{
							g.addSimpleEdge(edge[0],edge[1],false);
							edgeList.remove(edge);
							j++;
							break;
						}
						else
						{
							r--;
						}
					}
				}
			}
		}
		return g;
	}
	
	
	private static int UnitCapacityFordFulkersonMaxFlow(Grph g, int s, int t)
	{
		int maxFlow =0;
		Grph residualGraph = g.clone();

		MatrixWrappedVPath res = residualGraph.getShortestPath(s, t, false);
		while(res.getVerticesAsArray() != null)
		{
			int size = res.getLength();

			for(int i = 0; i < size; i++)
			{
				residualGraph.disconnect(res.getVertexAt(i),res.getVertexAt(i+1));
				residualGraph.addSimpleEdge(res.getVertexAt(i+1),res.getVertexAt(i),true);
			}		
			
			maxFlow ++;
			res = residualGraph.getShortestPath(s, t, false);
		}
		
		return maxFlow;
	}
	
	private static Grph VertexUnitCapacityGraph(Grph g)
	{
		Grph result = new Grph();
		result.addNVertices(g.getNumberOfVertices()*2);
		
		for(IntCursor i : g.getVertices())
		{
			result.addSimpleEdge(i.value*2,i.value*2+1,true);
			for(IntCursor j : g.getVertices())
			{
				if(i.value < j.value && g.areVerticesAdjacent(i.value,j.value))
				{
					result.addSimpleEdge(i.value*2+1, j.value*2, true);
					result.addSimpleEdge(j.value*2+1, i.value*2, true);
				}
			}
		}	

		return result;
	}
	
	
	public static int miniunEdgeCutSize(Grph g)
	{
		int minCutSize = g.getNumberOfVertices();
		Grph VertexCapicityGraph = VertexUnitCapacityGraph(g);
				
		for(IntCursor i : g.getVertices())
		{
			for(IntCursor j : g.getVertices())
			{
				if(i.value < j.value && !g.areVerticesAdjacent(i.value,j.value))
				{
					
					int maxFlow = UnitCapacityFordFulkersonMaxFlow(VertexCapicityGraph,i.value*2+1,j.value*2);
					if(minCutSize > maxFlow)
						minCutSize = maxFlow;
				}
			}
			
		}
		return minCutSize;	
	}
	
	public static boolean isKConnected(Grph g, int k)
	{
		int minCutSize = miniunEdgeCutSize(g);
		return k <= minCutSize;
	}
	
}
