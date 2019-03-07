package TCP_UDP;
import java.net.*;
import java.io.*;

public class TestChatServer {
	public static void main(String[] args){
		ServerSocket ss = null;
		Socket s = null;
		String line = null;
		try{
			ss = new ServerSocket(1213);
			s = ss.accept();
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pw =new PrintWriter(s.getOutputStream());
			BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("客户端："+br.readLine());
			line = br1.readLine();
			while(!line.equals("bye")){
				pw.println(line);
				pw.flush();
				System.out.println("服务器:"+line);
				System.out.println("客户端："+br.readLine());
			}
			br1.close();
			pw.close();
			br.close();
			s.close();
			ss.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
