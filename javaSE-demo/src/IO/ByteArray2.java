package IO;
import java.io.*;

public class ByteArray2 {
	public static void main(String[] args) throws Exception{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
		long aa=1234567890;
		dos.writeLong(aa);
		
		byte[] b= baos.toByteArray();
		ByteArrayInputStream bais=new ByteArrayInputStream(b);
		DataInputStream dis=new DataInputStream(bais);
		long ss=dis.readLong();
		
		System.out.println("ss:"+ss);
		
	}
}
