package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;

public class Sobre extends JDialog {
	public Sobre() {
		getContentPane().setForeground(new Color(255, 0, 0));
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().setLayout(null);
		
		JLabel titulo = new JLabel("Sobre o software");
		titulo.setBounds(74, 0, 160, 20);
		getContentPane().add(titulo);
	}
	

	public static void main(String[] args) {
	

	}

}
