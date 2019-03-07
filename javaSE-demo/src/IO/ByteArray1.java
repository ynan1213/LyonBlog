package IO;
import java.io.*;

public class ByteArray1 {
	public static void main(String[] args) throws IOException {   
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();   
	    DataOutputStream dout = new DataOutputStream(bout);   
	    String name = "xxy";   
	    int age = 84;   
	    dout.writeUTF(name);   
	    dout.writeInt(age);   
	    byte[] buff = bout.toByteArray();   
	    ByteArrayInputStream bin = new ByteArrayInputStream(buff);   
	    DataInputStream dis = new DataInputStream(bin);   
	    String newName = dis.readUTF();   
	    int newAge = dis.readInt();   
	    System.out.println(newName + ":" + newAge);   
	}  
}
