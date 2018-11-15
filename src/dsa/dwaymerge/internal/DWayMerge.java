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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StreamUtil.createTestFiles();
		
		List< MSInputStream > streams = new ArrayList<>();
		
		for( int i = 0; i < 3; i++ ) {
			
			MSInputStream is = StreamUtil.getInputStream( StreamUtil.STREAM_TYPE );
			try {
				is.open( "Random_"+i+".data" );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			streams.add(is);
		}
		
		mergeStreams( streams );
		
		
		try {
			System.out.println( "\n === Merged.data === \n" );
			StreamUtil.readFile("Merged.data", StreamUtil.STREAM_TYPE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void mergeStreams( List< MSInputStream > streams ) {
		
		List<HeapNode> buffer = new ArrayList<>();
		List<Boolean> streamsDone = new ArrayList<>();
		
	    for ( int i = 0; i < streams.size(); i++ ) {
	    	
	    	MSInputStream s = streams.get(i);
	    	
	    	try {
	    		
				if( !s.end_of_stream() ) {
					buffer.add( new HeapNode( s.read_next(), i ) );
					streamsDone.add(new Boolean(false) );
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    } 
		
	    MSPriorityQueue queue = new MSPriorityQueue( buffer.toArray( new HeapNode[0] ) );
	    
	    
	    MSOutputStream mergedStream = StreamUtil.getOutputStream( StreamUtil.STREAM_TYPE );
		
	    try {
			mergedStream.create( MERGED_PATH );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    for ( int i = 0; i < streams.size(); i++ ) {
	    
	    	HeapNode root = queue.getRoot(); 
	    	
	    	if( root.valid ) {
	    		
	    		try {
					mergedStream.write( root.element );
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
						root.valid = false;
						streamsDone.set(i, true);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	queue.heapify(0);
	    	
	    	if( i+1 == streams.size() && !getStreamsDone( streamsDone ) ) {
	    		i=-1;
	    	}
	    } 
	}
	
	public static boolean getStreamsDone( List<Boolean> list ) {
		
		boolean done = true;
		
		for( Boolean b : list ) {
			done = done && b;
		}
		
		return done;
		
	}
}

