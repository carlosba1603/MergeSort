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
	
	public long B;
	public MappedByteBuffer MappedBuffer;
	public int position=0;
	public int lenght=0;
	public FileChannel inChannel;
	
	public void setB(long B) {
		this.B=B;
	}

	public void open(String path) throws IOException {
		
			RandomAccessFile is = new RandomAccessFile(path, "r"); //solo lectura
			inChannel = is.getChannel();
			
			MappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, B); //Se mapea la regi�n al canal creado
			
			lenght=(int) new File(path).length();
			System.out.println("La Longitud inicial del archivo es: "+lenght);
			
			// TODO Auto-generated method stub
		
	}
	
	public int read_next() throws IOException {
		
		// TODO Auto-generated method stub	
		
		int IntToReturn=0;
		
		//System.out.println("Elementos en el buffer: "+MappedBuffer.remaining());
		
		if(MappedBuffer.remaining()>=4) {
			IntToReturn=MappedBuffer.getInt();
			//System.out.println("XXXXXXX");
		
		} else {
			MappedBuffer.clear(); //Clear buffer to map a new B portion or take the last part of the file (might be less than B)
			
			long newB=0;
			
			if(lenght-position>=B) {
				MappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, B);
				IntToReturn=MappedBuffer.getInt();
				//position=position+4;
			}
			
			else {
				newB=lenght-position;
				//System.out.println("Este es Longitud: "+ lenght);
				//System.out.println("Este es position: "+ position);
				//System.out.println("Este es NewB: "+ newB);
				MappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, position, newB);
				IntToReturn=MappedBuffer.getInt();
				
			}
			
		}
		
		position=position+4;
		
		return IntToReturn;
	}


	public boolean end_of_stream() {
		// TODO Auto-generated method stub
		if (position==lenght) {
			return true;
		}
		
		return false;
	}
	

}
