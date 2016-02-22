package main;

import java.awt.Color;

import javax.swing.JFrame;

import ui.GamePanel;

public class Test {

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("test");
		frame.setSize(615, 480);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new GamePanel());
		frame.setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
      		
	}
}
