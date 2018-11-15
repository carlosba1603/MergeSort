package dsa.dwaymerge.internal;

public class HeapNode {
	
	public int element; 
    public int idexOfStream;
    public boolean valid;

	public HeapNode( int element, int idexOfStream ) {
    	this.element = element;
    	this.idexOfStream = idexOfStream;
    	this.valid = true;
    }
	
}
