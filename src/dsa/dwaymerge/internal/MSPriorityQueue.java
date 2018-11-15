package dsa.dwaymerge.internal;

public class MSPriorityQueue {
	
	private HeapNode heap[];
	private int heapSize;
	
	
	public MSPriorityQueue ( HeapNode nodesArray[] ) { 
	    
		this.heap = nodesArray;
		this.heapSize = nodesArray.length;
		
	    int parent = (heapSize - 1)/2; 
	    
	    while ( parent >= 0 ) {
	    	
	        heapify( parent ); 
	        
	        parent--; 
	        
	    } 
	} 
	
    public HeapNode getRoot() {
    	return heap[0];
    }
	
	public void heapify( int parent ) { 
		
	    int left = 2 * parent  + 1; 
	    int right = 2 * parent  + 2;
	    
	    int smallest = parent;
	    
	    if ( left < heapSize && heap[ left ].element < heap[ parent ].element )
	        smallest = left; 
	    if ( right < heapSize && heap[ right ].element < heap[ smallest ].element) 
	        smallest = right; 
	    if ( smallest != parent ){ 
	    	
	        swap( parent , smallest ); 
	        heapify( smallest ); 
	        
	    } 
	    
	} 
	
	public void swap( int x, int y ){ 
		HeapNode temp = heap[x];
		heap[x] = heap[y];
		heap[y] = temp; 
	} 
	
}
