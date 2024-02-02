package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.DAO;

import javax.swing.JPasswordField;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.ImageIcon;

public class Login extends JDialog {
	public Login() {
		addWindowListener(new WindowAdapter() { 
			public void windowActivated(WindowEvent e) {
				statusConexaoBanco();
			}
		});
		
		setTitle("Login");
		setResizable(false);
		setBounds(new Rectangle(0, 0, 442, 300));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);
		
		JLabel txtLogin = new JLabel("Login");
		txtLogin.setBounds(99, 103, 40, 14);
		getContentPane().add(txtLogin);
		
		JLabel txtSenha = new JLabel("Senha");
		txtSenha.setBounds(99, 131, 40, 14);
		getContentPane().add(txtSenha);
		
		inputLogin = new JTextField();
		inputLogin.setBounds(141, 100, 86, 20);
		getContentPane().add(inputLogin);
		inputLogin.setColumns(10);
		
		inputSenha = new JPasswordField();
		inputSenha.setBounds(141, 128, 86, 20);
		getContentPane().add(inputSenha);
		
		JButton btnLogin = new JButton("Entrar");
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setBounds(149, 181, 89, 23);
		getContentPane().add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("Acessar conta");
		lblNewLabel.setBounds(157, 36, 115, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel imgDatabase = new JLabel("");
		imgDatabase.setIcon(new ImageIcon(Login.class.getResource("/img/databaseOff.png")));
		imgDatabase.setBounds(0, 203, 58, 58);
		getContentPane().add(imgDatabase);
	}

	DAO dao = new DAO();
	
	private void statusConexaoBanco() {
		try {
			Connection conexaoBanco = dao.conectar();
			
			if (conexaoBanco == null) {
				//escolher a imagem 
				imgDatabase.setIcon(new ImageIcon (Login.class.getResource("/img/databaseOff.png")));
				
			}

			else
				//trocar a imagem se houver conexão
				imgDatabase.setIcon(new ImageIcon (Login.class.getResource("/img/databaseOn.png")));
		}
		conexaoBanco.close();
	}
	
	  catch (Exception e) {
		System.out.println(e);
	}
	
}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inputLogin;
	private JPasswordField inputSenha;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login dialog = new Login();
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