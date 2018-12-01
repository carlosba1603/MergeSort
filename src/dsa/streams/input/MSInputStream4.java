package dsa.streams.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import dsa.streams.interfaces.MSInputStream;

public class MSInputStream4 implements MSInputStream  {
	
	public int B;
	public MappedByteBuffer mappedBuffer;
	public int position = 0;
	public long lenght = 0;
	
	public FileChannel inChannel;
	
	public void open(String path) throws IOException {
		
		RandomAccessFile is = new RandomAccessFile(path, "r"); //solo lectura
		
		lenght = is.length();
		inChannel = is.getChannel();
		
		mappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, Math.min( B*4, lenght ) ); //Se mapea la regi�n al canal creado
		
	}
	
	public int read_next() throws IOException {
		
		int intToReturn = 0;
		
		int remaining = mappedBuffer.remaining();
		if( remaining >= 4 ) {
			intToReturn = mappedBuffer.getInt();
		
		} else {
			mappedBuffer.clear(); //Clear buffer to map a new B portion or take the last part of the file (might be less than B)
			
			mappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, Math.min( B*4, lenght-position ) );
			intToReturn = mappedBuffer.getInt();
			
		}
		
		position += 4;
		
		return intToReturn;
	}


	public boolean end_of_stream() {

		if (position == lenght) {
			return true;
		}
		
		return false;
	}
	

}
