package GUI;
import java.awt.*;
import java.awt.event.*;


public class TestActionEvent1 {
	public static void main(String[] args){
		Frame f = new Frame("Test");
		Button b1 = new Button("start");
		Button b2 = new Button("stop");
		Monitor2 m = new Monitor2();
		b2.setActionCommand("game over");
		b1.addActionListener(m);
		b2.addActionListener(m);
		f.add(b1,"North");
		f.add(b2,"South");
		f.pack();
		f.setVisible(true);
		
	}
}


class Monitor2 implements ActionListener{
	public void actionPerformed (ActionEvent e){
		System.out.println("a button has been pressed,the relative info is:"+e.getActionCommand());
	}
}