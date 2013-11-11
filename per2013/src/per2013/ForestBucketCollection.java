package per2013;


public class ForestBucketCollection {
	
	/** Accessing with the rValue **/
	private ForestCell[] buckets;
	/** Accessing from the vertex id **/
	private ForestCell[] items;
	private int maxVal;
	private int nbItems;
	
	public ForestBucketCollection(int nbVertices){
		maxVal = 0;
		buckets = new ForestCell[nbVertices];
		items = new ForestCell[nbVertices];
		for (int i = 0; i < nbVertices; i++){
			buckets[i] = new ForestCell();// Using watchers
			items[i] = null;
		}
		nbItems = 0;
	}
	
	private void updateMax(){
		while (buckets[maxVal].getNext() == null && maxVal >= 0){
			maxVal--;
		}
		
	}
	
	/** Remove the Forest item with the highest rValue and remove it from the collection */
	public ForestItem pollMaxVertex(){
		updateMax();
		// Removing item from both, buckets and items
		ForestCell i = buckets[maxVal].getNext();
		items[i.val.vertexId] = null;
		i.isolate();
		nbItems--;
		return i.val;
	}
	
	public void increment(int vertexId){
		// Getting the element and removing it from the buckets
		ForestCell i = items[vertexId];
		i.isolate();
		// Incrementing the rValue
		i.val.rValue++;
		if (i.val.rValue > maxVal) maxVal = i.val.rValue;
		// Replacing in buckets
		i.insertAfter(buckets[i.val.rValue]);
	}
	
	public void place(ForestItem val){
		ForestCell i = new ForestCell(val);
		i.insertAfter(buckets[val.rValue]);
		items[val.vertexId] = i;
		nbItems += 1;
	}
	
	public boolean isEmpty(){
		return nbItems == 0;
	}
	
	public int size(){
		return nbItems;
	}

}
