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

	public long B;
	public MappedByteBuffer MappedBuffer;
	public int position=0;
	public int numElements=0; //Cantidad de elementos a escribir
	public FileChannel inChannel;
	ArrayList<MappedByteBuffer> File = new ArrayList();
	public int lenght;
	
	public void setB(long B) {
		this.B=B;
	}
	
	public void setNumElements(int numElements) {
		this.numElements=numElements;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setLength() {
		lenght=(int) (numElements*4);
	}
	
	public ArrayList getFile() {
		return File;
	}
		
	@Override
	public void create( String path, boolean append ) throws IOException {
		// TODO Auto-generated method stub
		
		RandomAccessFile is = new RandomAccessFile(path, "rw"); 
		inChannel = is.getChannel();
		
				
		MappedBuffer = inChannel.map(FileChannel.MapMode.READ_WRITE, position, lenght); //Se mapea la regi�n al canal creado
		
		lenght=(int) new File(path).length();
		
		File.add(MappedBuffer);
		
	}

	@Override
	public void write(Integer element) throws IOException {
		// TODO Auto-generated method stub
		
		if(MappedBuffer.remaining()>=4) {
			MappedBuffer.putInt(element);
		
			
		} else {
			MappedBuffer.clear(); //Clear buffer to map a new B portion or take the last part of the file (might be less than B)
			
			long newB=0;
			
			if(lenght-position>=B) {
				//position=position+4;
				MappedBuffer = inChannel.map(FileChannel.MapMode.READ_WRITE, position, B);
				File.add(MappedBuffer);
				MappedBuffer.putInt(element);
			}
			
			else {
				newB=lenght-position;
				//position=position+4;
				MappedBuffer = inChannel.map(FileChannel.MapMode.READ_WRITE, position, newB);
				File.add(MappedBuffer);
				MappedBuffer.putInt(element);
			}
			
		}
		
		position=position+4;
		

		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
			MappedBuffer.clear();
			inChannel.close();
		
		
		
	}
	
	public boolean end_of_stream() {
		// TODO Auto-generated method stub
		if (position==lenght) {
			return true;
		}
		
		return false;
	}
	
	
	public static void main(String args[]) throws Exception {
		
		StreamUtil test = new StreamUtil(); // Se crea un objeto de la calse StreamUtil
		
		System.out.println("**********************************");
		
		MSInputStream4 msI = new MSInputStream4(); // se crea un objeto de la clase Input para probar la lectura
		
		MSOutputStream4 ms = new MSOutputStream4(); //Se crea un objeto de la clase output
		 
		ms.setB(500000); // Se tiene que llamar este m�todo para indicar el tama�o B a pasar a memoria NO PUEDE SER MAYOR QUE EL TAMA�O DEL ARCHIVO
		ms.setNumElements(200000000);
		ms.setLength(); // Se establece el tama�o del archivo
		ms.create("C:\\Users\\cmarin\\Desktop\\datos2.txt", false); //Creamos el archivo para escribir los n�meros aleatorios
		
		//Dentro del while se pueden probar las operaciones de lectura y escritura
		while(ms.end_of_stream()==false) { //El objeto de lectura se llama msI (de la clase input) y el de escritura ms (de la clase output)
			//System.out.println(msI.read_next()); // Lee los enteros y los imprime
			Integer randomNum = ThreadLocalRandom.current().nextInt( Integer.MIN_VALUE, Integer.MAX_VALUE ); // genera los n�meros a escribir
			//System.out.println("El numero aleatorio es: "+randomNum.toString());//imprime los n�meros
			ms.write(randomNum);//Escribe sobre el archivo
			
		}
		ms.close(); //Cierra los buffers y los archivos

	}
	

}
