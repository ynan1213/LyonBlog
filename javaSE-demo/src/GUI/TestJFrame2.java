package GUI;
import java.awt.*;
import javax.swing.*;

public class TestJFrame2 extends JFrame{
	JPanel mb1,mb2,mb3,mb4,mb5;
	JButton an1,an2;
	JLabel bq1,bq2,bq3,bq4;
	JTextField wbk; 					//文本框    
	JPasswordField mmk;					//密码框
	JCheckBox fxk1,fxk2,fxk3;			//复选框
	JRadioButton dxk1,dxk2;				//单选框
	ButtonGroup dxz;					//单选组
	
	public static void main(String[] args){
		new TestJFrame2();
	}
	
	public TestJFrame2(){
		mb1=new JPanel();
		mb2=new JPanel();
		mb3=new JPanel();
		mb4=new JPanel();
		mb5=new JPanel();
		
		an1=new JButton("登录");
		an2=new JButton("取消");
		
		bq1=new JLabel("用户名");
		bq2=new JLabel("密   码");
		bq3=new JLabel("特长");
		bq4=new JLabel("性别");
		
		fxk1=new JCheckBox("音乐");
		fxk2=new JCheckBox("体育");
		fxk3=new JCheckBox("书法");
		
		dxk1=new JRadioButton("男");
		dxk2=new JRadioButton("女");
		dxz =new ButtonGroup();
		
		wbk=new JTextField(10);
		mmk=new JPasswordField(10);
		
		setLayout(new GridLayout(5,1));
		
		mb1.add(bq1);mb1.add(wbk);
		mb2.add(bq2);mb2.add(mmk);
		mb3.add(an1);mb3.add(an2);
		mb4.add(bq3);mb4.add(fxk1);mb4.add(fxk2);mb4.add(fxk3);
		mb5.add(bq4);mb5.add(dxk1);mb5.add(dxk2);
		dxz.add(dxk1);dxz.add(dxk2);
		
		add(mb1);
		add(mb2);
		add(mb3);
		add(mb4);
		add(mb5);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("登录框");
		setSize(300,250);
		setLocation(150,150);
		//pack();
		setVisible(true);
		
	}
}
