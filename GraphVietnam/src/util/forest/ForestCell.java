package util.forest;

public class ForestCell<V> {
	
	private ForestCell<V> previous;
	private ForestCell<V> next;
	
	public ForestItem<V> val;
	
	public ForestCell(){
		this(null);
	}
	
	public ForestCell(ForestItem<V> val){
		this.val = val;
		previous = null;
		next = null;
	}
	
	/** Remove the item from it's current list */
	public void isolate(){
		if (previous != null){
			previous.next = next;
		}
		if (next != null){
			next.previous = previous;
		}
	}
	
	/** Insert the current item after the specified one. */
	public void insertAfter(ForestCell<V> cell){
		this.next = cell.next;
		this.previous = cell;
		if (next != null){
			next.previous = this;
		}
		previous.next = this;
	}
	
	/** Get next element */
	public ForestCell<V> getNext(){
		return next;
	}

}
