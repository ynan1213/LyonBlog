package TCP_UDP;
import java.net.*;
import java.io.*;

public class Test1Client {
	public static void main(String[] args){
		try{
			Socket s=new Socket("127.0.0.1",6688);
			InputStream is2=s.getInputStream();
			OutputStream os2=s.getOutputStream();
			DataOutputStream out2=new DataOutputStream(os2);
			DataInputStream dis2=new DataInputStream(is2);
			out2.writeUTF("hey");
			String b=null;
			if((b=dis2.readUTF())!=null){
				System.out.println(b);
			}
			out2.close();
			dis2.close();
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}//catch(UnknownHostException es){
		//	es.printStackTrace();
		//}
	}
}
