package util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import graph.Graph;

public class DepthFirstSearch {
	public static interface DepthFirstSearchData<V> {
		public V father();

//		public int d();

		public int f();
	}

	private static class DepthFirstSearchDataImpl<V> implements
			DepthFirstSearchData<V> {

		V father;
//		int first;
		int last;
		Color color;

		@Override
		public V father() {
			return father;
		}

//		@Override
//		public int d() {
//			return first;
//		}

		@Override
		public int f() {
			return last;
		}
	}

	public static <V, E extends Graph.Edge<V>> Map<V, DepthFirstSearchData<V>> depthFirstSearch(
			Graph<V, E> g) {
		return depthFirstSearch(g, true);
	}

	public static <V, E extends Graph.Edge<V>> Map<V, DepthFirstSearchData<V>> depthFirstSearch(
			Graph<V, E> g, boolean directed) {
		Map<V, DepthFirstSearchData<V>> map = new HashMap<V, DepthFirstSearchData<V>>(
				g.order());
		for (V v : g.vertices()) {
			DepthFirstSearchDataImpl<V> data = new DepthFirstSearchDataImpl<V>();
			data.color = Color.WHITE;
			data.father = null;
			map.put(v, data);
		}
		int time = 0;
		for (V v : g.vertices()) {
			if (((DepthFirstSearchDataImpl<V>) map.get(v)).color == Color.WHITE)
				visitDFS(g, v, time, map, directed);
		}
		return map;
	}

	private static <V, E extends Graph.Edge<V>> void visitDFS(Graph<V, E> g,
			V v, int time, Map<V, DepthFirstSearchData<V>> map, boolean directed) {
		DepthFirstSearchDataImpl<V> vData = (DepthFirstSearchDataImpl<V>) map
				.get(v);
		vData.color = Color.GRAY;
		Iterable<V> neighbors = directed ? g.successors(v) : g.neighbors(v);
		for (V w : neighbors) {
			DepthFirstSearchDataImpl<V> wData = (DepthFirstSearchDataImpl<V>) map
					.get(w);
			if (wData.color == Color.WHITE) {
				wData.father = v;
				visitDFS(g, w, time, map, directed);
			}
		}
		vData.color = Color.BLACK;
		vData.last = time++;
	}
}
