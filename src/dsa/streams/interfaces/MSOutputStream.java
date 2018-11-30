package dsa.streams.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MSOutputStream {
	
	public void create( String path, boolean append ) throws IOException ;
	
	public void write( Integer element ) throws IOException;
	
	public void close() throws IOException;
	
}
