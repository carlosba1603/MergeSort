package dsa.streams.output;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import dsa.streams.input.MSInputStream4;
import dsa.streams.interfaces.MSOutputStream;
import dsa.streams.interfaces.StreamUtil;

public class MSOutputStream4 implements MSOutputStream {

	public int B;
	
	public MappedByteBuffer mappedBuffer;
	public RandomAccessFile is;
	public int position = 0;
	public FileChannel inChannel;

	@Override
	public void create( String path, boolean append ) throws IOException {
		
		File file = new File(path);
		file.delete();
		
		is = new RandomAccessFile(path, "rw");
		inChannel = is.getChannel();
		
		mappedBuffer = inChannel.map(FileChannel.MapMode.READ_WRITE, position, B*4); //Se mapea la regi�n al canal creado
		
	}

	@Override
	public void write(Integer element) throws IOException {
		// TODO Auto-generated method stub
		
		
		
		int remaining = mappedBuffer.remaining();
		if( remaining >= 4 ) {
			
			mappedBuffer.putInt(element);
			
		} else {
			
			mappedBuffer.clear(); //Clear buffer to map a new B portion or take the last part of the file (might be less than B)
			mappedBuffer = inChannel.map(FileChannel.MapMode.READ_WRITE, position, B*4);
			mappedBuffer.putInt(element);
		
			
		}
		
		position += 4;
		

		
	}

	@Override
	public void close() throws IOException {
		
			mappedBuffer.clear();
			inChannel.close();
		
	}

}
