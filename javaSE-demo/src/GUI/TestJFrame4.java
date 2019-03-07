package GUI;
import java.awt.*;
import javax.swing.*;

public class TestJFrame4 extends JFrame{
	JSplitPane cf;						//拆分窗口 
	JList lbk;							//列表框 
	JLabel bq;
	
	public static void main(String[] args){
		new TestJFrame4();
	}
	
	public TestJFrame4(){
	
		String[] s1={"软件开发","游戏开发","平面设计","动画制作","室内设计"};
		lbk=new JList(s1);												//列表框赋值
		
		bq=new JLabel(new ImageIcon("image/访问控制权限.png"));			//右边标签显示图片
		
		cf=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lbk,bq);			//参数列表框表示：左右拆分
		cf.setOneTouchExpandable(true);									//增加箭头
		
		add(cf);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("信息框");
		this.setSize(640,480);
		setLocation(150,150);
		setVisible(true);
		
	}
}
