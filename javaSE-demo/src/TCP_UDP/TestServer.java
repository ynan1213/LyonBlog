package TCP_UDP;
import java.net.*;
import java.io.*;

public class TestServer {
	public static void main(String[] args){
		try{
			ServerSocket ss =new ServerSocket(8888);
			while(true){
				Socket s=ss.accept();
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeUTF("HELLO, "+s.getInetAddress()+" port:"+s.getPort());
				dos.close();
				s.close();
			}
		}catch(IOException e ){
			e.printStackTrace();
			System.out.println("³ö´í");
		}
	}
}
