package dsa.dwaymerge.external;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dsa.dwaymerge.internal.DWayMerge;
import dsa.streams.interfaces.MSInputStream;
import dsa.streams.interfaces.MSOutputStream;
import dsa.streams.interfaces.StreamUtil;

public class ExternalDWayMergeSort {
	
	public static final String STREAM_QUEUE_FILE = "Queue.data";
	public static final String STREAM_PREFFIX = "Stream_";
	public static final String FILE_SUFFIX = ".data";
	public static int mainMemorySize;
	public static int streamsToMerge;
	public static String path;

	public static int writeQueuePos = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if( args.length < 3 ) {
			System.out.println("java ExternalDWayMergeSort.java [file_name] [main_memory_size] [streams_to_merge]" );
			return;
		}
		
		String path = args[0];
		mainMemorySize = Integer.parseInt( args[1] ); //M
		streamsToMerge = Integer.parseInt( args[2] ); //d
		
		
		
		StreamUtil.createRandomFile(path);
		
		
		
		int fileSize = StreamUtil.getFileSize( path ); //N
		int streamsNumber = (int) Math.ceil( (double)fileSize / mainMemorySize ) ; //N/M 
		boolean queueInDisk = streamsNumber >= mainMemorySize;
		
		//2.1
		int queueSize = splitFile( path, streamsNumber, queueInDisk  );
		
		//2.2
		for( int i = 0; i < streamsNumber; i++ ) {
			StreamUtil.readFile("Stream_"+i+".data");
		}
		
		StreamUtil.readFile(STREAM_QUEUE_FILE);
		
		int streamSorted = readStreamReferencesInQueue( queueSize );
	
		StreamUtil.readFile(STREAM_PREFFIX+streamSorted+FILE_SUFFIX);
		
	}
	
	public static int readStreamReferencesInQueue( int queueSize ) {
		
		MSInputStream queue = StreamUtil.getInputStream();
		MSOutputStream queueWrite = StreamUtil.getOutputStream();

		try {

			queue.open(STREAM_QUEUE_FILE);
			queueWrite.create(STREAM_QUEUE_FILE, true);
			int currentQueueIndex = 0;
			int newElementsInQueue = 0;
			
			while (!queue.end_of_stream()){
	
				List< MSInputStream > streams  = new ArrayList < MSInputStream >();
							
				int elementsInQueue = 0;
				
				while ( currentQueueIndex < queueSize && elementsInQueue < streamsToMerge ){
	
					MSInputStream newS = StreamUtil.getInputStream();
					int index = queue.read_next();
					newS.open(STREAM_PREFFIX+index+FILE_SUFFIX);
					streams.add(newS);
					elementsInQueue++;
					currentQueueIndex++;
				}
	
				DWayMerge.mergeStreams(streams, STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
				
				StreamUtil.readFile(STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
				
				
				queueWrite.write(writeQueuePos);
				writeQueuePos++;
				newElementsInQueue++;
				
				StreamUtil.readFile(STREAM_QUEUE_FILE);
				
				
					
				if( currentQueueIndex == queueSize ) {

					if( newElementsInQueue == 1 ) {
						return writeQueuePos-1;
					}
					
					queueSize = newElementsInQueue;
					newElementsInQueue = 0;
					currentQueueIndex = 0;
				}
				
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static int splitFile( String path, int streamsNumber,  boolean queueInDisk ) {
		
		MSInputStream is = StreamUtil.getInputStream();
		MSOutputStream stremReferencesOS = StreamUtil.getOutputStream();
		
		try {
			
			is.open(path);
			
			if( queueInDisk ) {
				stremReferencesOS.create( STREAM_QUEUE_FILE, false );
			}
			
			for( writeQueuePos = 0; writeQueuePos < streamsNumber; writeQueuePos++ ) {

				readStream( is, writeQueuePos );
				
				if( queueInDisk ) {
					stremReferencesOS.write( writeQueuePos );
				}
				
			}
			
			if( queueInDisk ) {
				stremReferencesOS.close();
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return writeQueuePos;
	}
	
	
	public static void readStream( MSInputStream is,  int streamNumber ) {
		
		
		int memory[] = new int[ mainMemorySize ];
		int count = 0;
		
		MSOutputStream os = StreamUtil.getOutputStream();
		
		try {
			
			os.create("Stream_"+streamNumber+".data", false);
			
			while( !is.end_of_stream() ) {
				
				memory[count++] = is.read_next();
				
				if( count == mainMemorySize ) {
					
					Arrays.sort( memory );
					
					break;
				}
				
			}
			
			for( int i = 0; i < count; i++ ) {
				os.write(memory[i]);
			}
			
			os.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
