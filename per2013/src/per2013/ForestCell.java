package per2013;

public class ForestCell {
	
	private ForestCell previous;
	private ForestCell next;
	
	public ForestItem val;
	
	public ForestCell(){
		this(null);
	}
	
	public ForestCell(ForestItem val){
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
	public void insertAfter(ForestCell cell){
		this.next = cell.next;
		this.previous = cell;
		if (next != null){
			next.previous = this;
		}
		previous.next = this;
	}
	
	/** Get next element */
	public ForestCell getNext(){
		return next;
	}

}
