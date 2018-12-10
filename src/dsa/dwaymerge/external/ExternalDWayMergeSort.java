package dsa.dwaymerge.external;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dsa.dwaymerge.internal.DWayMerge;
import dsa.streams.input.MSInputStream1;
import dsa.streams.interfaces.MSInputStream;
import dsa.streams.interfaces.MSOutputStream;
import dsa.streams.interfaces.StreamUtil;
import dsa.streams.output.MSOutputStream1;

public class ExternalDWayMergeSort {
	
	public static final String STREAM_QUEUE_FILE = "Data/Queue.data";
	public static final String STREAM_PREFFIX = "Data/Stream_";
	public static final String FILE_SUFFIX = ".data";
	public static int mainMemorySize;
	public static int dStreamsToMerge;
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
		dStreamsToMerge = Integer.parseInt( args[2] ); //d
			
		StreamUtil.createRandomFile(path, 1_000);
		
		//StreamUtil.readFile(path);
		
		int fileSize = StreamUtil.getFileSize( path ); //N
		int streamsNumber = (int) Math.ceil( (double)fileSize / mainMemorySize ) ; //N/M 
		boolean queueInDisk = streamsNumber >= mainMemorySize;
		
		//2.1
		int queueSize = splitFile( path, streamsNumber, queueInDisk  );
		
		
		int streamSorted = 0;
		
		if( queueInDisk )
			streamSorted = readStreamReferencesInQueue( queueSize );
		else {
			
			List<MSInputStream> queueInMemory = new ArrayList<>();
			
			for( int index = 0; index < queueSize; index++ ) {
				MSInputStream is = StreamUtil.getInputStream();
				try {
					is.open(STREAM_PREFFIX+index+FILE_SUFFIX);
					queueInMemory.add( is );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				
			}
			
			streamSorted = readStreamReferencesInQueue( queueInMemory, queueSize );
		}
		
		StreamUtil.readFile(STREAM_PREFFIX+streamSorted+FILE_SUFFIX);
		
	}
	
	public static int readStreamReferencesInQueue( List<MSInputStream> queue, int queueSize ) {
		
	
			int currentQueueIndex = 0;
			int newElementsInQueue = 0;
			
			while ( !queue.isEmpty() ){
	
				List< MSInputStream > streams  = new ArrayList < MSInputStream >();
							
				int elementsInQueue = 0;
				
				while ( currentQueueIndex < queueSize && elementsInQueue < dStreamsToMerge ){
	
					streams.add( queue.get( 0 ) );
					queue.remove(0);
					
					elementsInQueue++;
					currentQueueIndex++;
				}
	
				DWayMerge.mergeStreams(streams, STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
				//StreamUtil.readFile(STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
				
				MSInputStream newS = StreamUtil.getInputStream();
				try {
					newS.open(STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
					queue.add( newS );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				writeQueuePos++;
				newElementsInQueue++;
					
				if( currentQueueIndex == queueSize ) {

					if( newElementsInQueue == 1 ) {
						return writeQueuePos-1;
					}
					
					queueSize = newElementsInQueue;
					newElementsInQueue = 0;
					currentQueueIndex = 0;
				}
				
			} 
		
		return -1;
	}
	
	public static int readStreamReferencesInQueue( int queueSize ) {
		
		MSInputStream queue = new MSInputStream1();
		MSOutputStream queueWrite = new MSOutputStream1();
		

		try {

			queue.open(STREAM_QUEUE_FILE);
			queueWrite.create(STREAM_QUEUE_FILE, true);
			int currentQueueIndex = 0;
			int newElementsInQueue = 0;
			
			while (!queue.end_of_stream()){
	
				List< MSInputStream > streams  = new ArrayList < MSInputStream >();
							
				int elementsInQueue = 0;
				
				while ( currentQueueIndex < queueSize && elementsInQueue < dStreamsToMerge ){
	
					MSInputStream newS = StreamUtil.getInputStream();
					int index = queue.read_next();
					newS.open(STREAM_PREFFIX+index+FILE_SUFFIX);
					streams.add(newS);
					elementsInQueue++;
					currentQueueIndex++;
				}
	
				DWayMerge.mergeStreams(streams, STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
				
				//StreamUtil.readFile(STREAM_PREFFIX+writeQueuePos+FILE_SUFFIX);
				
				
				queueWrite.write(writeQueuePos);
				writeQueuePos++;
				newElementsInQueue++;
				
				//StreamUtil.readFile(STREAM_QUEUE_FILE);
				
				
					
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
			
			os.create(STREAM_PREFFIX+streamNumber+FILE_SUFFIX, false);
			
			while( !is.end_of_stream() ) {
				
				memory[count++] = is.read_next();
				
				if( count == mainMemorySize )
					break;
				
				
			}
			
			if( count < mainMemorySize )
				memory = Arrays.copyOfRange(memory, 0, count);
			
			Arrays.sort( memory );
			
			for( int i = 0; i < count; i++ ) {
				os.write(memory[i]);
			}
			
			os.close();
			
			//StreamUtil.readFile(STREAM_PREFFIX+streamNumber+FILE_SUFFIX);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
