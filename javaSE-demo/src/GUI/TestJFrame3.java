package GUI;
import java.awt.*;
import javax.swing.*;

public class TestJFrame3 extends JFrame{
	JPanel mb1,mb2;
	JLabel bq1,bq2;
	JComboBox xlk; 						//下拉框    
	JList lbk;							//列表框 
	JScrollPane gdt;					//滚动条（配合列表框） 
	
	public static void main(String[] args){
		new TestJFrame3();
	}
	
	public TestJFrame3(){
		mb1=new JPanel();
		mb2=new JPanel();
		
		bq1=new JLabel("籍贯");
		bq2=new JLabel("学历");
		
		String[] s1={"武汉","咸宁","通山","北京"};
		xlk =new JComboBox(s1);								//下拉框赋值
		
		String[] s2={"初中","高中","本科","硕士","博士"};
		lbk=new JList(s2);									//列表框赋值
		lbk.setVisibleRowCount(3);							//单次显示3个选项
		gdt=new JScrollPane(lbk);							//增加滚动条
		
		setLayout(new GridLayout(2,1));
		
		mb1.add(bq1);mb1.add(xlk);
		mb2.add(bq2);mb2.add(gdt);	//mb2赋值须用滚动条
		
		add(mb1);
		add(mb2);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("信息框");
		setSize(300,250);
		setLocation(150,150);
		setVisible(true);
		
	}
}
