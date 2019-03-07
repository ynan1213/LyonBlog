package GUI;
import java.awt.*;
import java.awt.event.*;


public class TestActionEvent {
	public static void main(String[] args){
		Frame f = new Frame("Test");
		Button b = new Button("Press me!");
		Monitor m = new Monitor();
		b.addActionListener(m);
		f.add(b,BorderLayout.CENTER);
		//f.pack();
		f.setVisible(true);
	}
}


class Monitor implements ActionListener{
	public void actionPerformed(ActionEvent e){
		System.out.println("a button has been pressed");
	}
}