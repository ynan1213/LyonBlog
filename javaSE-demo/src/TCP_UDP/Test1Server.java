package TCP_UDP;
import java.net.*;
import java.io.*;


public class Test1Server {
	public static void main(String[] args){
		try{
			ServerSocket ss=new ServerSocket(6688);
			Socket s=ss.accept();
			InputStream is1=s.getInputStream();
			OutputStream os1=s.getOutputStream();
			DataOutputStream out1=new DataOutputStream(os1);
			DataInputStream in1=new DataInputStream(is1);
			String a=null;
			if((a=in1.readUTF())!=null){
				System.out.println(a);
				System.out.println("From:"+s.getInetAddress());
				System.out.println("Port:"+s.getPort());
			}
			out1.writeUTF("Hello world");
			out1.close();
			in1.close();
			is1.close();
			os1.close();
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
