

import java.awt.*;
import javax.swing.*;

public class TestJFrame6 extends JFrame
{
	JTextArea wby;
	JPanel mb;
	JComboBox xlk;
	JButton an;
	JTextField wbk;	
	JScrollPane gd;
	
	public static void main(String[] args) 
	{
		new TestJFrame6();
	}
	
	TestJFrame6()
	{
		wby=new JTextArea();
		mb=new JPanel();
		String[] lt={"���","�˽�","ɳɮ","С����"};
		xlk=new JComboBox(lt);
		wbk=new JTextField(10);
		an=new JButton("����");
		gd=new JScrollPane(wby);
		
		
		mb.add(xlk);   mb.add(wbk);   mb.add(an);		
		this.add(gd);   this.add(mb,BorderLayout.SOUTH);
		
		this.setTitle("���촰��");
		 this.setSize(300,200);
		 this.setIconImage((new ImageIcon("image/qq.jpg")).getImage());
		 this.setLocation(300,280);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);	
	}
}