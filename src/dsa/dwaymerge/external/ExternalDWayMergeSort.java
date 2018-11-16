package dsa.dwaymerge.external;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dsa.streams.interfaces.MSInputStream;
import dsa.streams.interfaces.MSOutputStream;
import dsa.streams.interfaces.StreamUtil;

public class ExternalDWayMergeSort {

	public static final int STREAM_TYPE = 1;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if( args.length < 3 ) {
			System.out.println("java ExternalDWayMergeSort.java [file_name] [main_memory_size] [streams_to_merge]" );
			return;
		}
		
		String path = args[0];
		int mainMemorySize = Integer.parseInt( args[1] );
		int streamsToMerge = Integer.parseInt( args[2] );
		
		int fileSize = StreamUtil.getFileSize( path );
		
		int streamsNumber = (int) Math.ceil( (double)fileSize / (double)mainMemorySize );
		
		
		readAndSortStreams( "Random.data", streamsNumber, mainMemorySize  );
		
	}
	
	public static List<MSOutputStream> readAndSortStreams( String path, int streamsNumber, int mainMemorySize ) {
		
		List<MSOutputStream> list = new ArrayList<>();
		
		MSInputStream is = StreamUtil.getInputStream( STREAM_TYPE );
		
		try {
			is.open(path);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for( int i = 0; i < streamsNumber; i++ ) {
			
			MSOutputStream os = StreamUtil.getOutputStream( STREAM_TYPE );
			
			int array[] = new int[mainMemorySize];
			int count = 0;
			
			try {
				while( !is.end_of_stream() ) {
					
					array[count++] = is.read_next();
					
					if( count == mainMemorySize ) {
						
						Arrays.sort( array );
						
						break;
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for( int j = 0; j < count; j++ ) {
				System.out.print(array[j]+" ");
			}
			System.out.println("");
			
			
		}
		
		return list;
		
	}
	
	

}
