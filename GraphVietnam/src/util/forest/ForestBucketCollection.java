package util.forest;

import java.util.HashMap;
import java.util.Set;


public class ForestBucketCollection<V> {
	
	/** Accessing with the rValue **/
	private ForestCell<V>[] buckets;
	/** Accessing from the vertex **/
	private HashMap<V, ForestCell<V>> items;
	private int maxVal;
	
	
	public ForestBucketCollection(Set<V> vertices, int nbEdges){
		maxVal = 0;
		// Temporary variable in order to avoid warning
		@SuppressWarnings("unchecked")
		ForestCell<V>[] newBucket =(ForestCell<V>[])new ForestCell[nbEdges];
		buckets = newBucket;
		items = new HashMap<V,ForestCell<V>>();
		for (int i = 0; i < nbEdges; i++){
			buckets[i] = new ForestCell<V>();// Using watchers
		}
	}
	
	private void updateMax(){
		while (buckets[maxVal].getNext() == null && maxVal >= 0){
			maxVal--;
		}
		
	}
	
	/** Remove the Forest item with the highest rValue and remove it from the collection */
	public ForestItem<V> pollMaxVertex(){
		updateMax();
		// Removing item from both, buckets and items
		ForestCell<V> c = buckets[maxVal].getNext();
		items.remove(c.val.vertex);
		c.isolate();
		return c.val;
	}
	
	public void increment(V vertex){
		// Getting the element and removing it from the buckets
		ForestCell<V> c = items.get(vertex);
		c.isolate();
		// Incrementing the rValue
		c.val.rValue++;
		if (c.val.rValue > maxVal) maxVal = c.val.rValue;
		// Replacing in buckets
		c.insertAfter(buckets[c.val.rValue]);
	}
	
	public void place(ForestItem<V> val){
		ForestCell<V> c = new ForestCell<V>(val);
		c.insertAfter(buckets[val.rValue]);
		items.put(val.vertex, c);
	}
	
	public boolean isEmpty(){
		return items.isEmpty();
	}
	
	public int size(){
		return items.size();
	}

}
