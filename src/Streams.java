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


public class Streams {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		try {
			
			
			
			
			
			
		    
			for( int i = 0; i < 100; i++) {
				
				int randomNum = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
				System.out.println(randomNum);
				
				
			}
			
			
			System.out.println("================================================");
			
			InputStream is = new FileInputStream( new File("Random.data" ) );
			DataInputStream ds = new DataInputStream(is);
	        
			
			
			while(ds.available()>0) {
				int x = ds.readInt();
		        System.out.println(x);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	/*
	 * Planned to be used for the approach number 3 but we need to verify we don't need to do the buffering tasks manually
	 * 
	 * */
	public static DataOutputStream create( String path, int bufferSizeB ) throws FileNotFoundException {
		
		OutputStream os = new FileOutputStream( new File( path ) );
		DataOutputStream dos = null;
		

		BufferedOutputStream bos = new BufferedOutputStream( os, bufferSizeB );
		dos = new DataOutputStream( bos );
		
		
		return dos;
	}
	
	public static DataOutputStream create( String path, boolean buffered ) throws FileNotFoundException {
	
		OutputStream os = new FileOutputStream( new File( path ) );
		DataOutputStream dos = null;
		
		
		if( buffered ) {
			BufferedOutputStream bos = new BufferedOutputStream( os );
			dos = new DataOutputStream( bos );
		} else {
			dos = new DataOutputStream( os );
		}
		
		return dos;
	}
	
	public static DataOutputStream create( String path ) throws FileNotFoundException {
		return new DataOutputStream( new FileOutputStream( path ) );
	}
	
	public static void close( DataOutputStream dataOut ) throws IOException {
		dataOut.close();
	}
	
	public void writeOneItemAtTime( DataOutputStream dataOut, Integer item ) throws IOException {
		dataOut.writeInt( item );	
	}

}
