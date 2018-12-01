package dsa.streams.input;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import dsa.streams.interfaces.MSInputStream;

public class MSInputStream3 implements MSInputStream {

	public DataInputStream dis;
	public int B = 5;
	
	public void open(String path) throws FileNotFoundException {

		InputStream is = new FileInputStream( new File( path ) );
        BufferedInputStream bis = new BufferedInputStream( is, B );
        this.dis = new DataInputStream( bis );
        
	}
	
	@Override
	public int read_next() throws IOException {
		
		return dis.readInt();
		
	}

	@Override
	public boolean end_of_stream() throws IOException {
		
		int bytesAvailable = dis.available();
		
		if( bytesAvailable > 0 ) {
			return false;
		}
		
		dis.close();
		
		return true;
	}
	
	/*private int[] buffer;

	
	
	private int count;
	private boolean readEnd;
	private int numbersInBuffer;
	
	@Override
	public void open(String path ) throws FileNotFoundException {
		
		InputStream is = new FileInputStream( new File( path ) );
        this.dis = new DataInputStream( is );
        this.B = B;
        this.buffer = new int[B];
        count = B;
		
	}

	@Override
	public int read_next() throws IOException {
		// TODO Auto-generated method stub
		
		int bytesAvailable = 0;
		
		if( count == B ) {
			
			count = 0;
			numbersInBuffer = 0;
			bytesAvailable = dis.available();

		}  
		
		if( bytesAvailable > 0 &&  numbersInBuffer == 0 ) {
			
	        byte[] array = new byte[ B * 4 ];
	        numbersInBuffer = dis.read(array, 0, B * 4)/4;
			
			
			ByteBuffer byteBuffer = ByteBuffer.wrap( array );
			IntBuffer intBuffer = byteBuffer.asIntBuffer();
			intBuffer.get( buffer );
			
			bytesAvailable = dis.available();
			
			if( bytesAvailable == 0 ) {
				this.readEnd = true;
			}
			
		}
		
		return buffer[count++];
	}
	

	@Override
	public boolean end_of_stream() throws IOException {
		return readEnd && count >= numbersInBuffer;
	}*/

}
