package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Event;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Sobre extends JDialog {
	public Sobre() {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/logo.png")));
		getContentPane().setBackground(new Color(240, 240, 255));
		getContentPane().setForeground(new Color(255, 0, 0));
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().setLayout(null);
		
		JLabel titulo = new JLabel("Sobre o software");
		titulo.setFont(new Font("Yu Gothic Medium", Font.BOLD, 14));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setBounds(0, 38, 550, 20);
		getContentPane().add(titulo);
		
		JLabel descricao1 = new JLabel("O software CoWorking trata-se de um protótipo cujo o objetivo é");
		descricao1.setHorizontalAlignment(SwingConstants.CENTER);
		descricao1.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
		descricao1.setBounds(0, 83, 550, 30);
		getContentPane().add(descricao1);
		
		JLabel descricao2 = new JLabel("possibilitar o gerenciamento de reserva de salas em um espaço colaborativo.\r\n");
		descricao2.setHorizontalAlignment(SwingConstants.CENTER);
		descricao2.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
		descricao2.setBounds(0, 106, 550, 37);
		getContentPane().add(descricao2);
		
		JLabel versao = new JLabel("Versão 1.0.0\r\n");
		versao.setHorizontalAlignment(SwingConstants.CENTER);
		versao.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
		versao.setBounds(0, 199, 550, 37);
		getContentPane().add(versao);
		
		JLabel atualizacao = new JLabel("Última atualização: 31/01/2024\r\n");
		atualizacao.setHorizontalAlignment(SwingConstants.CENTER);
		atualizacao.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
		atualizacao.setBounds(0, 217, 550, 43);
		getContentPane().add(atualizacao);
		
		JLabel imgMIT = new JLabel("");
		imgMIT.setIcon(new ImageIcon(Sobre.class.getResource("/img/mitLicense.png")));
		imgMIT.setBounds(494, 293, 56, 55);
		getContentPane().add(imgMIT);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				    dialog.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
}
