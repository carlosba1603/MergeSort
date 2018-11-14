package dsa.streams.output;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream1 implements MSOutputStream{

	private DataOutputStream dos;
	
	@Override
	public void create(String path) throws FileNotFoundException {

		this.dos = new DataOutputStream( new FileOutputStream( path, true ) );
	}

	@Override
	public void write(int element) throws IOException {

		dos.writeInt( element );
	}

	@Override
	public void close() throws IOException {

		this.dos.close();
		
	}

}
