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
	
	public static final String STEAM_QUEUE_FILE = "StreamQueue.data";
	public static final String STREAM_PREFFIX = "Stream_";
	public static final String STREAM_SUFFIX = ".data";
	public static int mainMemorySize;
	public static int streamsToMerge;
	public static String path;
	public static int queueIndex;
	public static int writeQueuePos = 0;
	public static int readQueuePos = 0;
	
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
		splitFile( path, streamsNumber, queueInDisk  );
		
		//2.2
		for( int i = 0; i < streamsNumber; i++ ) {
			System.out.println("\n == Stream_"+i+".data == \n");
			StreamUtil.readFile("Stream_"+i+".data");
		}
		
		
	
		//Arreglo con el nombre de cada uno de los archivos
		if( queueInDisk ) {
			System.out.println("\n == Queue_.data == \n");
			StreamUtil.readFile( STEAM_QUEUE_FILE );
			MSInputStream queue = StreamUtil.getInputStream();
			MSOutputStream queueWrite = StreamUtil.getOutputStream();
			

		try {
			
			queue.open(STEAM_QUEUE_FILE);
			
		   while (!queue.end_of_stream()){

				List< MSInputStream > streams  = new ArrayList < MSInputStream >();
				
				int i = 0;
				while (i < streamsToMerge){
					MSInputStream newS = StreamUtil.getInputStream();
					newS.open(STREAM_PREFFIX+readQueuePos+STREAM_SUFFIX);
					streams.add(newS);
					i++;
					readQueuePos++;
				}
				
				//Sort
				
				DWayMerge.mergeStreams(streams, STREAM_PREFFIX+writeQueuePos+STREAM_SUFFIX);
				queueWrite.create(STEAM_QUEUE_FILE);
				queueWrite.write(writeQueuePos);
				writeQueuePos++;
			
		   }
		
		//readStreamReferences( queueInExternalMemory )
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
			
			for( writeQueuePos = 0; writeQueuePos < streamsNumber; writeQueuePos++ ) {

				readStream( is, writeQueuePos );
				
				if( queueInDisk )
					stremReferencesOS.write( writeQueuePos );
				
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
		
		
		int memory[] = new int[ mainMemorySize ];
		int count = 0;
		
		MSOutputStream os = StreamUtil.getOutputStream();
		
		try {
			
			os.create("Stream_"+streamNumber+".data");
			
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
