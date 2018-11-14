package dsa.streams.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MSOutputStream {
	
	public static final int B = 1024 * 4;

	public void create( String path ) throws FileNotFoundException ;
	
	public void write( Integer element ) throws IOException;
	
	public void close() throws IOException;
	
}
