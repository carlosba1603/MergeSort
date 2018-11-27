package dsa.streams.output;

import java.io.DataOutputStream;
import java.io.IOException;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream4 implements MSOutputStream {

	private DataOutputStream dos;
	
	@Override
	public void create(String path, boolean append) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(Integer element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.dos.close();
	}

}
