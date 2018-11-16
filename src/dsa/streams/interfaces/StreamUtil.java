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
	
	public static final int STREAM_TYPE = 3;
	
	public static void main(String[] args) {
		createRandomFile( "Random.data" );
		readFile("Random.data");
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
	
	public static MSInputStream getInputStream( int streamType ) {
	
		switch( streamType ) {
			case 1:
				return new MSInputStream1();
			
			case 2:
				return new MSInputStream2();
				
			case 3:
				return new MSInputStream3();
			
			case 4:
				return new MSInputStream4();
				
			default:
				return new MSInputStream1();
		}	
		
	}
	
	public static MSOutputStream getOutputStream( int streamType ) {
		
		switch( streamType ) {
			case 1:
				return new MSOutputStream1();
			
			case 2:
				return new MSOutputStream2();
				
			case 3:
				return new MSOutputStream3();
			
			case 4:
				return new MSOutputStream4();
				
			default:
				return new MSOutputStream1();
		}	
		
	}
	
	public static void readFile( String path ) {
		
		MSInputStream is = getInputStream( STREAM_TYPE );
		
		try {
			is.open( path );
		} catch (FileNotFoundException e) {
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
		
		MSOutputStream os = getOutputStream( STREAM_TYPE );
		
		os.create( path );
		
		
		for( int i = 0; i < numbers.length; i++) {
			
			os.write( numbers[i] );
			
		}
		
		
		os.close();
		
	}
	
	
	public static void createRandomFile( String path ){
		
		MSOutputStream os = getOutputStream( STREAM_TYPE );
		
		try {
			os.create( path );
			
			for( int i = 0; i < 37; i++) {
				
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
		
		MSInputStream is = StreamUtil.getInputStream( STREAM_TYPE );
		
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
