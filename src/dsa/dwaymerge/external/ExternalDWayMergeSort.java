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
	
	public static final String STEAM_QUEUE_FILE = "StreamQueue.data";
	public static int mainMemorySize;
	public static int streamsToMerge;
	public static String path;
	public static int queueIndex;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if( args.length < 3 ) {
			System.out.println("java ExternalDWayMergeSort.java [file_name] [main_memory_size] [streams_to_merge]" );
			return;
		}
		
		String path = args[0];
		mainMemorySize = Integer.parseInt( args[1] );
		streamsToMerge = Integer.parseInt( args[2] );
		
		
		
		StreamUtil.createRandomFile(path);
		
		
		
		int fileSize = StreamUtil.getFileSize( path );
		int streamsNumber = (int) Math.ceil( (double)fileSize / mainMemorySize );
		boolean queueInDisk = streamsNumber >= mainMemorySize;
		
		splitFile( path, streamsNumber, queueInDisk  );
		
		
		for( int i = 0; i < streamsNumber; i++ ) {
			System.out.println("\n == Stream_"+i+".data == \n");
			StreamUtil.readFile("Stream_"+i+".data");
		}
		
		if( queueInDisk ) {
			System.out.println("\n == Queue_.data == \n");
			StreamUtil.readFile( STEAM_QUEUE_FILE );
		}
		
		//readStreamReferences( queueInExternalMemory )
		
	}
	
	public static void readStreamReferences( boolean queueInDisk ) {
		
		
		
	}
	
	public static void splitFile( String path, int streamsNumber,  boolean queueInDisk ) {
		
		MSInputStream is = StreamUtil.getInputStream();
		MSOutputStream stremReferencesOS = StreamUtil.getOutputStream();
		
		try {
			
			is.open(path);
			
			if( queueInDisk ) {
				stremReferencesOS.create( STEAM_QUEUE_FILE );
			}
			
			for( int i = 0; i < streamsNumber; i++ ) {

				readStream( is, i );
				
				if( queueInDisk )
					stremReferencesOS.write( i );
				
			}
			
			if( queueInDisk )
				stremReferencesOS.close();;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	
	public static void readStream( MSInputStream is,  int streamNumber ) {
		
		int array[] = new int[ mainMemorySize ];
		int count = 0;
		
		MSOutputStream os = StreamUtil.getOutputStream();
		
		try {
			
			os.create("Stream_"+streamNumber+".data");
			
			while( !is.end_of_stream() ) {
				
				array[count++] = is.read_next();
				
				if( count == mainMemorySize ) {
					
					Arrays.sort( array );
					
					break;
				}
				
			}
			
			for( int i = 0; i < count; i++ ) {
				os.write(array[i]);
			}
			
			os.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
