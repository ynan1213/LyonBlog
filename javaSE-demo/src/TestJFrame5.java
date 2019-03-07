
import java.awt.*;
import javax.swing.*;

public class TestJFrame5 extends JFrame 
{
	JLabel bq1;  //����
	JButton an1,an2,an3;   JPanel mb1;//�ϲ�
	JTabbedPane xxk;    //�в�  ѡ�
	JPanel mb2,mb3,mb4;
	JLabel bq2,bq3,bq4,bq5;
	JTextField wbk;
	JPasswordField mmk;
	JButton an4;
	JCheckBox fxk1,fxk2;	
	
	public static void main(String[] args) 
	{
		new TestJFrame5();
	}
	
	public TestJFrame5()
	{
		bq2=new JLabel("qq����",JLabel.CENTER);     
		bq3=new JLabel("qq����",JLabel.CENTER);
		bq4=new JLabel("��������",JLabel.CENTER);
		bq4.setFont(new Font("����",Font.PLAIN,16));
		bq4.setForeground(Color.BLUE);
		bq5=new JLabel("<html><a href='www.qq.com'>�������뱣��</a>");
		bq5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		wbk=new JTextField();
		mmk=new JPasswordField();
		an4=new JButton(new ImageIcon("image/an1.jpg"));
		
		fxk1=new JCheckBox("�����½");
		fxk2=new JCheckBox("��ס����");
		
		bq1=new JLabel(new ImageIcon("image/qqq.jpg"));     //����
		mb1=new JPanel();      //�ϲ�
		an1=new JButton(new ImageIcon("image/an2.jpg"));
		an2=new JButton(new ImageIcon("image/an3.jpg"));
		an3=new JButton(new ImageIcon("image/an4.jpg"));
		
		xxk=new JTabbedPane();  //�в�
		mb2=new JPanel();  mb3=new JPanel();
		mb3.setBackground(Color.BLUE);
		mb4=new JPanel();
		mb4.setBackground(Color.GREEN);
		
		xxk.add("��ͨ�û�",mb2);   xxk.add("QQ��Ա",mb3);   xxk.add("����Ա",mb4);
		
		mb2.setLayout(new GridLayout(3,3));
		
		mb1.add(an1);  mb1.add(an2);  mb1.add(an3);
		mb2.add(bq2);  mb2.add(wbk);  mb2.add(an4);  mb2.add(bq3);  mb2.add(mmk);
		mb2.add(bq4);  mb2.add(fxk1);  mb2.add(fxk2);  mb2.add(bq5);
		
		this.add(mb1,BorderLayout.SOUTH);
		this.add(bq1,BorderLayout.NORTH);
		this.add(xxk,BorderLayout.CENTER);
		
		ImageIcon tp1=new ImageIcon("image/qq.jpg");
		this.setIconImage(tp1.getImage());
		this.setTitle("�û���¼");
		 this.setSize(340,270);
		 this.setLocation(300,280);
		 this.setResizable(false);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);	
		
		
		
		
		
		
		
		
	}
	
	
	
}
