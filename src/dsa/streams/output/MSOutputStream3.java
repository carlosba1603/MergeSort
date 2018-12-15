package dsa.streams.output;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import dsa.streams.interfaces.MSOutputStream;

public class MSOutputStream3 implements MSOutputStream {
	
	private DataOutputStream dos;
	public int B = 5;
	
	@Override
	public void create(String path, boolean append) throws FileNotFoundException {
		OutputStream os = new FileOutputStream( new File( path ), append );
		BufferedOutputStream bos = new BufferedOutputStream( os, B );
		this.dos = new DataOutputStream( bos );
	}

	@Override
	public void write(Integer element) throws IOException {
		dos.writeInt( element );
	}

	@Override
	public void close() throws IOException {
		this.dos.close();
	}
	
}
