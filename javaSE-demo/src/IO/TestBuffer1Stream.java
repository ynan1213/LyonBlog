package IO;
import java.io.*;

public class TestBuffer1Stream {
	public static void main(String[] args) throws Exception{
		BufferedInputStream bis=new BufferedInputStream(new FileInputStream("e:\\19e6e2ea8d203f6374504c678f73ff95.mp4"));
		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("d:\\java.mp4"));
		byte[] b=new byte[2048]; 
		int i=0;
		i=bis.read(b);   //read(byte[] b) 从输入流中读取一定数量的字节，并将其存储在缓冲区数组 b 中。
		while(i!=-1){
			bos.write(b,0,i);
			i=bis.read(b);
		}
		bos.flush();
		bos.close();
		bis.close();
	}
}
