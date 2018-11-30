package dsa.streams.interfaces;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface MSInputStream {
	
	public void open( String path ) throws IOException;
	
	public int read_next() throws IOException;
	
	public boolean end_of_stream() throws IOException; 
	
}
