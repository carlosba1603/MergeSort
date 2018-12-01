package dsa.streams.interfaces;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import dsa.streams.input.MSInputStream1;
import dsa.streams.input.MSInputStream2;
import dsa.streams.input.MSInputStream3;
import dsa.streams.input.MSInputStream4;
import dsa.streams.output.MSOutputStream1;
import dsa.streams.output.MSOutputStream2;
import dsa.streams.output.MSOutputStream3;
import dsa.streams.output.MSOutputStream4;


public class StreamUtil {
	
	public static int STREAM_TYPE;
	public static String ioType;
	public static int B;
	public static final String FILE_PREFIX = "Data/S_";
	public static final String FILE_SUFFIX = ".data";
	
	public static void main(String[] args) {
		
		if( args.length < 4 ) {
			System.out.println("java StreamUtil.java [streams_number] [number_of_io] [stream_type] [r/w] " );
			return;
		}
		
		int streamsNumber = Integer.parseInt( args[0] );
		long iosNumber = Long.parseLong( args[1] );
		STREAM_TYPE = Integer.parseInt( args[2] );
		ioType = args[3];
		
		if( STREAM_TYPE  == 3 || STREAM_TYPE  == 4 ) {
			if( args.length < 5 ) {
				System.out.println("java StreamUtil.java [streams_number] [number_of_io] [stream_type] [r/w] [buffer_size] " );
			}
			B = Integer.parseInt( args[4] );
		}
		
		if( ioType.equals("w") )
			writeStreams( streamsNumber, iosNumber );
		else
			readStreams( streamsNumber );
		
		System.out.println("Finished");
		
	}

	public static void createTestFiles() {
			try {		
			

			int numbers[][] = { { 1,56,23,45,7,38,41,46,40,42,52,67,89,90,22,34,12,67,56,93,18,29,44,46,52,66,71,82,87,89,4,2,6,5,1,9,3},
								{18,29,44,46,52,66,71,82,87,89,4,2,6,5,1,9,3 },
								{ 1, 5, 8, 100 } };
			
			
			for( int i = 0; i < 3; i++ ) {
				createFileWithIntegers( "Random_"+i+".data", numbers[i] );
			}
			
		
			
			for( int i = 0; i < 3; i++ ) {
				
				System.out.println( "\n === Random_"+i+".data === \n" );
				readFile( "Random_"+i+".data" );
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static MSInputStream getInputStream() {
	
		switch( STREAM_TYPE ) {
			case 1:
				return new MSInputStream1();
			
			case 2:
				return new MSInputStream2();
				
			case 3:
				MSInputStream3 is3 = new MSInputStream3();
				is3.B = B;
				
				return is3;
			
			case 4:
				MSInputStream4 is4 = new MSInputStream4();
				is4.B = B;
				return is4;
				
				
			default:
				return new MSInputStream1();
		}	
		
	}
	
	public static MSOutputStream getOutputStream() {
		
		switch( STREAM_TYPE ) {
			case 1:
				return new MSOutputStream1();
			
			case 2:
				return new MSOutputStream2();
				
			case 3:
				MSOutputStream3 os3 = new MSOutputStream3();
				os3.B = B;
				
				return os3;
			
			case 4:
				MSOutputStream4 os4 = new MSOutputStream4();
				os4.B = B;
				return os4;
				
				
			default:
				return new MSOutputStream1();
		}	
		
	}
	
	public static void writeOneElementFile( String path ) {
		
		MSOutputStream os = getOutputStream();
		
		try {
			
			os.create( path, false );

			int randomNum = ThreadLocalRandom.current().nextInt( Integer.MIN_VALUE, Integer.MAX_VALUE );//Integer.MAX_VALUE);
			
			os.write( randomNum );
			
			
			os.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void readStreams( int k ) {
		Set<MSInputStream> streams = new HashSet<>();
		
		for( int i = 0; i < k; i++ ) {
			MSInputStream is = getInputStream();
			try {
				is.open(FILE_PREFIX+i+FILE_SUFFIX);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			streams.add(is);
		}
		
		for( MSInputStream is : streams ) {
			
			
			try {
				while( !is.end_of_stream() ) {
					int x = is.read_next();
					//System.out.println( x );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public static void writeStreams( int k, long n ) {
		
		List<MSOutputStream> streams = new ArrayList<>();
		
		for( int i = 0; i < k; i++ ) {
			MSOutputStream os = getOutputStream();
			
			try {
				os.create("Data/S_"+i+".data", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			streams.add(os);
		}
		
		for( MSOutputStream os : streams ) {
		
			
			try {
				for( int i = 0; i < n; i++ ) {
					int randomNum = ThreadLocalRandom.current().nextInt( Integer.MIN_VALUE, Integer.MAX_VALUE );//Integer.MAX_VALUE);
					os.write( randomNum );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for( MSOutputStream os : streams ) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void readFile( String path ) {
		
		MSInputStream is = getInputStream();
		
		
		System.out.println("\n === "+path+" === \n");
		try {
			is.open( path );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while( !is.end_of_stream() ) {
				
				int number = is.read_next();
				
				System.out.println( number );
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void createFileWithIntegers( String path, int numbers[] ) throws IOException {
		
		MSOutputStream os = getOutputStream();
		
		os.create( path, false );
		
		
		for( int i = 0; i < numbers.length; i++) {
			
			os.write( numbers[i] );
			
		}
		
		
		os.close();
		
	}
	
	
	public static void createRandomFile( String path ){
		
		MSOutputStream os = getOutputStream();
		
		try {
			os.create( path, false );
			
			for( int i = 0; i < 57; i++) {
				
				int randomNum = ThreadLocalRandom.current().nextInt(0, 100);//Integer.MAX_VALUE);
				
				//System.out.println( randomNum );
				
				os.write( randomNum );
				
			}
			
			os.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static int getFileSize( String path ) {
		int file_size = 0;
		
		MSInputStream is = StreamUtil.getInputStream();
		
		try {
			
			is.open(path);
			
			while( !is.end_of_stream() ) {
				is.read_next();
				file_size++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file_size;
	}

}
