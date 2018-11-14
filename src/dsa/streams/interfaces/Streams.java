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


public class Streams {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		int streamType = 3;
		
		try {		
			
			//createRandomFile( streamType );
			createFile( streamType );
			readRandomFile( streamType );
			
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
	
	public static void readRandomFile( int streamType ) throws IOException {
		
		MSInputStream is = getInputStream( streamType );
		
		is.open( "Random.data" );
		
		while( !is.end_of_stream() ) {
			
			int number = is.read_next();
			
			System.out.println( number );
			
		}
		
		
		
	}
	
	public static void createFile( int streamType ) throws IOException {
		
		MSOutputStream os = getOutputStream(streamType);
		
		os.create( "Random.data" );
		
		int numbers[] = { 2, 10, 5, 7, 1, 9, 2, 3, Integer.MAX_VALUE, 4, 6, 8 };
		
		for( int i = 0; i < numbers.length; i++) {
			
			os.write( numbers[i] );
			
		}
		
		os.close();
		
	}
	
	
	public static void createRandomFile( int streamType ) throws IOException {
		
		MSOutputStream os = getOutputStream(streamType);
		
		os.create( "Random.data" );
		
		for( int i = 0; i < 10; i++) {
			
			int randomNum = ThreadLocalRandom.current().nextInt(0, 10);//Integer.MAX_VALUE);
			
			System.out.println( randomNum );
			
			os.write( randomNum );
			
		}
		
		os.close();
		
	}

}
