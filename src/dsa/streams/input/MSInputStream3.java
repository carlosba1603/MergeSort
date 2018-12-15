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
	
	@Override
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

}
