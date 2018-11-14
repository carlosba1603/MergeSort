package dsa.streams.input;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dsa.streams.interfaces.MSInputStream;

public class MSInputStream1 implements MSInputStream {


	public DataInputStream dis;
	
	@Override
	public void open(String path) throws FileNotFoundException {

		InputStream is = new FileInputStream( new File( path ) );
        this.dis = new DataInputStream(is);
		
	}

	@Override
	public int read_next() throws IOException {
		
		return dis.readInt();
		
	}

	@Override
	public boolean end_of_stream() throws IOException {

		if( dis.available() > 0 ) {
			return false;
		}
		
		dis.close();
		
		return true;
	}

}
