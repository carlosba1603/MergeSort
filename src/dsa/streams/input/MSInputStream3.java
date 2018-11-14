package dsa.streams.input;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dsa.streams.interfaces.MSInputStream;

public class MSInputStream3 implements MSInputStream {

	public DataInputStream dis;
	private byte buffer[];
	
	private int count;
	private boolean readEnd;
	private int bytesInBuffer;
	
	@Override
	public void open(String path) throws FileNotFoundException {
		
		InputStream is = new FileInputStream( new File( path ) );
        this.dis = new DataInputStream( is );
        this.buffer = new byte[B];
        count = B;
		
	}

	@Override
	public int read_next() throws IOException {
		// TODO Auto-generated method stub
		int number = 0;
		int bytesAvailable = 0;
		
		if( count == B ) {
			
			count = 0;
			bytesInBuffer = 0;
			bytesAvailable = dis.available();

		}  
		
		if( bytesAvailable > 0 &&  bytesInBuffer == 0 ) {
			dis.read(buffer, 0, B);
			bytesInBuffer = Math.min( bytesAvailable, B );
			
			bytesAvailable = dis.available();
			if( bytesAvailable == 0 ) {
				this.readEnd = true;
			}
			
		}
		
		number = buffer[count++] << 24 | (buffer[count++] & 0xFF) << 16 | (buffer[count++] & 0xFF) << 8 | (buffer[count++] & 0xFF);
		
		
		return number;
	}

	@Override
	public boolean end_of_stream() throws IOException {
		return readEnd && count >= bytesInBuffer;
	}

}
