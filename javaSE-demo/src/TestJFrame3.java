
import java.awt.*;
import javax.swing.*;

public class TestJFrame3 extends JFrame{
	JPanel mb1,mb2;
	JLabel bq1,bq2;
	JComboBox xlk; 						//������    
	JList lbk;							//�б�� 
	JScrollPane gdt;					//������������б�� 
	
	public static void main(String[] args){
		new TestJFrame3();
	}
	
	public TestJFrame3(){
		mb1=new JPanel();
		mb2=new JPanel();
		
		bq1=new JLabel("����");
		bq2=new JLabel("ѧ��");
		
		String[] s1={"�人","����","ͨɽ","����"};
		xlk =new JComboBox(s1);								//������ֵ
		
		String[] s2={"����","����","����","˶ʿ","��ʿ"};
		lbk=new JList(s2);									//�б��ֵ
		lbk.setVisibleRowCount(3);							//������ʾ3��ѡ��
		gdt=new JScrollPane(lbk);							//���ӹ�����
		
		setLayout(new GridLayout(2,1));
		
		mb1.add(bq1);mb1.add(xlk);
		mb2.add(bq2);mb2.add(gdt);	//mb2��ֵ���ù�����
		
		add(mb1);
		add(mb2);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("��Ϣ��");
		setSize(300,250);
		setLocation(150,150);
		setVisible(true);
		
	}
}
