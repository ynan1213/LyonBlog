
import java.awt.*;
import javax.swing.*;

public class TestJFrame4 extends JFrame{
	JSplitPane cf;						//��ִ��� 
	JList lbk;							//�б�� 
	JLabel bq;
	
	public static void main(String[] args){
		new TestJFrame4();
	}
	
	public TestJFrame4(){
	
		String[] s1={"�������","��Ϸ����","ƽ�����","��������","�������"};
		lbk=new JList(s1);												//�б��ֵ
		
		bq=new JLabel(new ImageIcon("image/���ʿ���Ȩ��.png"));			//�ұ߱�ǩ��ʾͼƬ
		
		cf=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lbk,bq);			//�����б���ʾ�����Ҳ��
		cf.setOneTouchExpandable(true);									//���Ӽ�ͷ
		
		add(cf);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("��Ϣ��");
		this.setSize(640,480);
		setLocation(150,150);
		setVisible(true);
		
	}
}
