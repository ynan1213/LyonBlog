package IO;
import java.io.*;
public class TestObjectIO {
	public static void main(String[] args){
		ObjectOutputStream ous = null;
		ObjectInputStream ois = null;
		Student s1=new Student("ะกร๗",102,99.8f);
		Student s2=null;

		try{
			FileOutputStream fos=new FileOutputStream("d:\\a.txt");
			ous = new ObjectOutputStream(fos);
			ous.writeObject(s1);

			FileInputStream fis=new FileInputStream("d:\\a.txt");
			ois=new ObjectInputStream(fis);
			s2=(Student)ois.readObject();

			System.out.println(s2.name);
			System.out.println(s2.num);
			System.out.println(s2.cj);

		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				ous.close();
				ois.close();
				System.exit(-1);
			}catch(Exception e){
				System.exit(-1);
			}
		}
	}
}

class Student implements Serializable{
	String name = null;
	int num = 0;
	transient float cj = 0;

	public Student(String name, int num, float cj){
		this.name = name;
		this.num = num;
		this.cj = cj;
	}

}
