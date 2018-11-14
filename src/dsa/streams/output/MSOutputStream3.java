package dsa.streams.output;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream3 implements MSOutputStream {
	
	private DataOutputStream dos;
	private byte buffer[];
	private int count;
	
	@Override
	public void create(String path) throws FileNotFoundException {

		this.dos = new DataOutputStream( new FileOutputStream( path ) );
		this.buffer = new byte[B];
		this.count = 0;
		
	}

	@Override
	public void write(Integer element) throws IOException {
		
		buffer[count++] = (byte) ((element & 0xFF000000) >> 24);
		buffer[count++] = (byte) ((element & 0x00FF0000) >> 16);
		buffer[count++] = (byte) ((element & 0x0000FF00) >> 8);
		buffer[count++] = (byte) ((element & 0x000000FF) >> 0);
		
		if( count > B ) {
			dos.write( buffer );
			buffer = new byte[B];
			count = 0;
		}
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		dos.write(buffer, 0, count);
		this.count = 0;
		this.dos.close();
	}


}
