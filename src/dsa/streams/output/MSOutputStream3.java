package dsa.streams.output;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream3 implements MSOutputStream {
	
	private DataOutputStream dos;
	private int buffer[];
	private int count;
	

	public int B = 5;

	
	@Override
	public void create(String path, boolean append) throws FileNotFoundException {

		this.dos = new DataOutputStream( new FileOutputStream( path, append ) );
		this.buffer = new int[B];
		this.count = 0;
		
	}

	@Override
	public void write(Integer element) throws IOException {
		
		if( count == B ) {
			
			writeBufferInStream();
			
			buffer = new int[B];
			count = 0;
		}
		
		buffer[count++] = element;
		
		
	}

	@Override
	public void close() throws IOException {
		
		writeBufferInStream();
		
		this.count = 0;
		this.dos.close();
	}

	private void writeBufferInStream() throws IOException {
		
		ByteBuffer byteBuffer = ByteBuffer.allocate( count * 4 );        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put( Arrays.copyOfRange(buffer, 0, count) );

        byte[] array = byteBuffer.array();

		dos.write( array );
		
	}
	
}
