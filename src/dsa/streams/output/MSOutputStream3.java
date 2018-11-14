package dsa.streams.output;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream3 implements MSOutputStream {


	private final int B = 1024;
	
	private DataOutputStream dos;
	private int buffer[];
	private int count;
	
	@Override
	public void create(String path) throws FileNotFoundException {
		// TODO Auto-generated method stub
		this.dos = new DataOutputStream( new FileOutputStream( path, true ) );
		this.buffer = new int[B];
		this.count = 0;
		
	}

	@Override
	public void write(int element) {
		// TODO Auto-generated method stub
		buffer[count] = element;
		count++;
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.dos.close();
	}


}
