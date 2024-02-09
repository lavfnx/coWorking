package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Home extends JDialog {
	public JPanel panelUsuario;
	
	private static final long serialVersionUID = 1L;
	public JLabel txtData;
	public JLabel txtUsuarioLogado;

	public Home() {
		addWindowListener (new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				Date dataSistema = new Date();
				DateFormat formatadorData = DateFormat.getDateInstance(DateFormat.FULL);
				txtData.setText(formatadorData.format(dataSistema));
				
			}
		});
		
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setBounds(new Rectangle(0, 0, 445, 300));
		setResizable(false);
		setTitle("Home");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);
		
		JButton btnUser = new JButton("");
		btnUser.setBorderPainted(false);
		btnUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUser.setIcon(new ImageIcon(Home.class.getResource("/img/user.png")));
		btnUser.setBounds(23, 95, 99, 98);
		getContentPane().add(btnUser);
		
		JButton btnRoom = new JButton("");
		btnRoom.setBorderPainted(false);
		btnRoom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRoom.setIcon(new ImageIcon(Home.class.getResource("/img/room.png")));
		btnRoom.setBounds(156, 95, 105, 98);
		getContentPane().add(btnRoom);
		
		JButton btnReserve = new JButton("");
		btnReserve.setBorderPainted(false);
		btnReserve.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReserve.setIcon(new ImageIcon(Home.class.getResource("/img/reserve.png")));
		btnReserve.setBounds(299, 95, 105, 100);
		getContentPane().add(btnReserve);
		
	    panelUsuario = new JPanel();
		panelUsuario.setBounds(0, 213, 429, 37);
		getContentPane().add(panelUsuario);
		panelUsuario.setLayout(null);
		
		txtUsuarioLogado = new JLabel("");
		txtUsuarioLogado.setBounds(10, 11, 132, 14);
		panelUsuario.add(txtUsuarioLogado);
		
		txtData = new JLabel("");
		txtData.setBounds(194, 11, 225, 14);
		panelUsuario.add(txtData);
		
		
		
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Funcionarios funcionario = new Funcionarios();
				funcionario.setVisible(true);
		}
		});
		
		btnRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Salas sala = new Salas();
				sala.setVisible(true);
		}
		});
		
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reservas reserva = new Reservas();
				reserva.setVisible(true);
		}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home dialog = new Home();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}

