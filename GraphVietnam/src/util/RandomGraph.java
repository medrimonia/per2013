package util;

import graph.DirectedEdge;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomGraph {

	public static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void randomGraph(Graph<String, Graph.Edge<String>> g,
			Integer numberOfVertices, Integer numberOfEdges,
			Map<Graph.Edge<String>, Integer> w, int wMax) {
		Random rd = new Random();
		List<Position> list = new ArrayList<Position>();

		for (int i = 1; i <= numberOfVertices; i++) {
			for (int j = i + 1; j <= numberOfVertices; j++) {
				list.add(new Position(i, j));
			}
		}

		if (numberOfEdges == null) {
			int max = (numberOfVertices * numberOfVertices) / 2
					- numberOfVertices;
			int min = numberOfVertices - 1;
			numberOfEdges = rd.nextInt(max - min) + min;
			;
		}

		for (int i = 1; i <= numberOfVertices; i++) {
			g.addVertex(String.valueOf(i));
		}
		int number = 0;
		while (number <= numberOfEdges) {
			int rdp = rd.nextInt(list.size());
			Position p = list.get(rdp);
			Graph.Edge<String> e = new DirectedEdge<String>(
					String.valueOf(p.x), String.valueOf(p.y));
			g.addEdge(e);
			w.put(e, rd.nextInt(wMax - 1) + 1);
			++number;
			list.remove(rdp);
		}
	}

	public static void randomDiGraph(Graph<String, Graph.Edge<String>> g,
			Integer numberOfVertices, Integer numberOfEdges,
			Map<Graph.Edge<String>, Integer> w, int wMax) {
		Random rd = new Random();
		List<Position> list = new ArrayList<Position>();

		for (int i = 1; i <= numberOfVertices; i++) {
			for (int j = 1; j <= numberOfVertices; j++) {
				if (i == j)
					continue;
				list.add(new Position(i, j));
			}
		}

		if (numberOfEdges == null) {
			int max = (numberOfVertices * numberOfVertices) / 2
					- numberOfVertices;
			int min = numberOfVertices - 1;
			numberOfEdges = rd.nextInt(max - min) + min;
			;
		}

		for (int i = 1; i <= numberOfVertices; i++) {
			g.addVertex(String.valueOf(i));
		}
		int number = 0;
		while (number <= numberOfEdges) {
			int rdp = rd.nextInt(list.size());
			Position p = list.get(rdp);
			Graph.Edge<String> e = new DirectedEdge<String>(
					String.valueOf(p.x), String.valueOf(p.y));
			g.addEdge(e);
			w.put(e, rd.nextInt(wMax - 1) + 1);
			++number;
			list.remove(rdp);
		}
	}

	public static void randomNetwork(Graph<String, Graph.Edge<String>> g,
			Integer numberOfVertices, Integer numberOfEdges,
			Map<Graph.Edge<String>, Integer> l,
			Map<Graph.Edge<String>, Integer> u, int min, int max) {
		Random rd = new Random();
		List<Position> list = new ArrayList<Position>();

		for (int i = 1; i <= numberOfVertices; i++) {
			for (int j = 1; j <= numberOfVertices; j++) {
				if (i == j)
					continue;
				list.add(new Position(i, j));
			}
		}

		if (numberOfEdges == null) {
			int m1 = (numberOfVertices * numberOfVertices) / 2
					- numberOfVertices;
			int m2 = numberOfVertices - 1;
			numberOfEdges = rd.nextInt(m1 - m2) + m1;
			;
		}

		for (int i = 1; i <= numberOfVertices; i++) {
			g.addVertex(String.valueOf(i));
		}
		int number = 0;
		while (number <= numberOfEdges) {
			int rdp = rd.nextInt(list.size());
			Position p = list.get(rdp);
			Graph.Edge<String> e = new DirectedEdge<String>(
					String.valueOf(p.x), String.valueOf(p.y));
			g.addEdge(e);
			int mid = (max + min) / 2;
			l.put(e, rd.nextInt(mid - min) + min);
			u.put(e, rd.nextInt(max - mid) + mid);
			++number;
			list.remove(rdp);
		}
	}

	// public static void randomGraph(Graph<String, Graph.Edge<String>> g, int
	// n, Map<Graph.Edge<String>, Integer> w, int min, int max){
	// Integer[][] m = randomMatrixGraph(n,min,max);
	// for (int i =0;i<n;i++){
	// g.addVertex(String.valueOf(i));
	// }
	// for (int i=0; i< n; i++){
	// for (int j=i+1; j< n; j++){
	// Graph.Edge<String> e = new DirectedEdge<String>(String.valueOf(i),
	// String.valueOf(j));
	// g.addEdge(e);
	// w.put(e, m[i][j]);
	// }
	// }
	// }
	//
	// public static void randomDiGraph(Graph<String, Graph.Edge<String>> g, int
	// n, Map<Graph.Edge<String>, Integer> w, int min, int max){
	// Integer[][] m = randomMatrixDiGraph(n,min,max);
	// for (int i =0;i<n;i++){
	// g.addVertex(String.valueOf(i));
	// }
	// for (int i=0; i< n; i++){
	// for (int j=0; j< n; j++){
	// if (i==j) {
	// continue;
	// }
	// Graph.Edge<String> e = new DirectedEdge<String>(String.valueOf(i),
	// String.valueOf(j));
	// g.addEdge(e);
	// w.put(e, m[i][j]);
	// }
	// }
	// }
	//
	// public static void randomNetwork(Graph<String, Graph.Edge<String>> g, int
	// n, Map<Graph.Edge<String>, Integer> l, Map<Graph.Edge<String>, Integer>
	// u,int min, int max){
	// Bound[][] m = randomMatrixNetwork(n,min,max);
	// for (int i =0;i<n;i++){
	// g.addVertex(String.valueOf(i));
	// }
	// for (int i=0; i< n; i++){
	// for (int j=0; j< n; j++){
	// if (i==j) {
	// continue;
	// }
	// Graph.Edge<String> e = new DirectedEdge<String>(String.valueOf(i),
	// String.valueOf(j));
	// g.addEdge(e);
	// l.put(e, m[i][j].lower);
	// u.put(e, m[i][j].upper);
	// }
	// }
	// }
	//
	// public static Integer[][] randomMatrixGraph(int n, int min, int max){
	// Random rd = new Random();
	// Integer[][] result = new Integer[n][n];
	//
	// for (int i=0; i< n; i++){
	// result[i][i] = 0;
	// for (int j=i+1; j< n; j++){
	// result[i][j]=result[j][i] = rd.nextInt(max-min) + min;
	// }
	// }
	// return result;
	// }
	//
	// public static Integer[][] randomMatrixDiGraph(int n, int min, int max){
	// Random rd = new Random();
	// Integer[][] result = new Integer[n][n];
	//
	// for (int i=0; i< n; i++){
	// for (int j=0; j< n; j++){
	// if (i==j) {
	// result[i][i] = 0;
	// continue;
	// }
	// result[i][j] = rd.nextInt(max-min) + min;
	// }
	// }
	// return result;
	// }
	//
	// public static class Bound{
	// Integer lower;
	// Integer upper;
	//
	// public Bound(int l, int c){
	// lower = l;
	// upper = c;
	// }
	//
	// public String toString(){
	// return "("+lower+","+upper+")";
	// }
	// }
	//
	// public static Bound[][] randomMatrixNetwork(int n, int min, int max){
	// Random rd = new Random();
	// Bound[][] result = new Bound[n][n];
	//
	// for (int i=0; i< n; i++){
	// for (int j=0; j< n; j++){
	// if (i==j) {
	// result[i][i] = new Bound(0, 0);
	// continue;
	// }
	// int mid = (max + min)/2;
	// result[i][j] = new Bound(rd.nextInt(mid-min) + min, rd.nextInt(max-mid) +
	// mid);
	// }
	// }
	// return result;
	// }

	// public static void printString(Integer[][] arr){
	//
	// for (int i=0; i< arr.length; i++) {
	// for (int j=0; j< arr.length; j++) {
	// System.out.print(arr[i][j]+",");
	// }
	// System.out.println();
	// }
	// }

}
