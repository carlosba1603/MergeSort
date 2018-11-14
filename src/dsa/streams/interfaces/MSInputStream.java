package dsa.streams.interfaces;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface MSInputStream {
	
	public static final int B = 5*4;
	
	public void open( String path ) throws FileNotFoundException;
	
	public int read_next() throws IOException;
	
	public boolean end_of_stream() throws IOException; 
	
}
