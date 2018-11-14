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


public class Streams {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		int streamType = 2;
		
		try {		
			
			//createRandomFile( streamType );
			
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
	
	public static void readRandomFile( int streamType ) throws IOException {
		
		MSInputStream is = getInputStream( streamType );
		
		is.open( "Random.data" );
		
		while( !is.end_of_stream() ) {
			
			int number = is.read_next();
			
			System.out.println( number );
			
		}
		
		
		
	}
	
	
	public static void createRandomFile( int streamType ) throws IOException {
		
		MSOutputStream os = new MSOutputStream1();
		
		os.create( "Random.data" );
		
		for( int i = 0; i < 10; i++) {
			
			int randomNum = ThreadLocalRandom.current().nextInt(0, 10);//Integer.MAX_VALUE);
			
			os.write( randomNum );
			
		}
		
		os.close();
		
	}

}
