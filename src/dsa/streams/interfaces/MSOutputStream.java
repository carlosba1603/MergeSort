package dsa.streams.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MSOutputStream {

	public void create( String path ) throws FileNotFoundException ;
	
	public void write( int element ) throws IOException;
	
	public void close() throws IOException;
	
}
