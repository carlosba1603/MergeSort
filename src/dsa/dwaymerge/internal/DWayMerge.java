package dsa.dwaymerge.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dsa.streams.interfaces.MSInputStream;
import dsa.streams.interfaces.MSOutputStream;
import dsa.streams.interfaces.StreamUtil;
import dsa.streams.output.MSOutputStream2;

public class DWayMerge {
	
	
	public static final String MERGED_PATH = "Merged.data";

	//For testing
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StreamUtil.createTestFiles();
		
		List< MSInputStream > streams = new ArrayList<>();
		
		for( int i = 0; i < 3; i++ ) {
			
			MSInputStream is = StreamUtil.getInputStream();
			try {
				is.open( "Random_"+i+".data" );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			streams.add(is);
		}
		
		
		System.out.println( "\n ===  Screen.data === \n" );
		
		mergeStreams( streams, MERGED_PATH );
		
		
			System.out.println( "\n === Merged.data === \n" );
			StreamUtil.readFile("Merged.data");
		

	}
	
	
	public static void mergeStreams( List< MSInputStream > streams, String outputFileName ) {
		

		List<Boolean> streamsDone = new ArrayList<>();
		
		HeapNode[] heapNodes = getHeapNodeFromStream( streams, streamsDone  );
	    MSPriorityQueue queue = new MSPriorityQueue( heapNodes );
		
	    MSOutputStream mergedStream = StreamUtil.getOutputStream();
	    
	    try {
			mergedStream.create( outputFileName, false );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    for ( int i = 0; i < streams.size(); i++ ) {
	    
	    	HeapNode root = queue.getRoot(); 
	    	
	    	if( !streamsDone.get(i) ) {
	    		
	    		try {
					mergedStream.write( root.element );

				    System.out.println( root.element );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	  
		    	MSInputStream s = streams.get( root.idexOfStream );
		    	
		    	try {
					if( !s.end_of_stream() ) {
						
					    root.element = s.read_next() ; 
					    
					} else {
						root.element = Integer.MAX_VALUE ;
						streamsDone.set(i, new Boolean(true));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	queue.heapify(0);
	    	
	    	if( !getStreamsDone( streamsDone ) && i+1 == streams.size() ) {
	    		i=-1;
	    	}
	    } 
	}
	
	public static HeapNode[] getHeapNodeFromStream( List< MSInputStream > streams, List<Boolean> streamsDone ) {
		
		List<HeapNode> buffer = new ArrayList<>();
		
		for ( int i = 0; i < streams.size(); i++ ) {
		    	
		    	MSInputStream s = streams.get(i);
		    	
		    	try {
		    		
					if( !s.end_of_stream() ) {
						buffer.add( new HeapNode( s.read_next(), i ) );
						streamsDone.add(new Boolean(false) );
					} else {
						
						buffer.add( new HeapNode(Integer.MAX_VALUE, i) );
						streamsDone.add(new Boolean( true ) );
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		
		return buffer.toArray( new HeapNode[0] );
		 
	}
	
	public static boolean getStreamsDone( List<Boolean> list ) {
		
		boolean done = true;
		
		for( Boolean b : list ) {
			done = done && b;
		}
		
		return done;
		
	}
}

