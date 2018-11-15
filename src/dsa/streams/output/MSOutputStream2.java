package dsa.streams.output;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream2 implements MSOutputStream {

	private DataOutputStream dos;
	
	@Override
	public void create(String path) throws FileNotFoundException {

		OutputStream os = new FileOutputStream( new File( path ) );
		BufferedOutputStream bos = new BufferedOutputStream( os );
		this.dos = new DataOutputStream( bos );
		
	}

	@Override
	public void write(Integer element) throws IOException {
		dos.writeInt( element );
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.dos.close();
	}

}
