package TCP_UDP;
import java.io.*;
import java.net.*;

public class TestChatClient {
	public static void main(String[] args){
		Socket s= null;
		String line = null;
		try{
			s= new Socket("127.0.0.1",1213);
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			line = br.readLine();
			while(!line.equals("bye")){
				pw.println(line);
				pw.flush();
				
				System.out.println("客户端："+line);
				System.out.println("服务器："+br1.readLine());
			}
			br1.close();
			pw.close();
			br.close();
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
