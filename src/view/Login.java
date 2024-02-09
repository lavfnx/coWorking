package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;

public class Login extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField inputLogin;
	private JPasswordField inputSenha;

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

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});

		JLabel lblNewLabel = new JLabel("Acessar conta");
		lblNewLabel.setBounds(157, 36, 115, 14);
		getContentPane().add(lblNewLabel);

		imgDatabase = new JLabel("");
		imgDatabase.setIcon(new ImageIcon(Login.class.getResource("/img/databaseOff.png")));
		imgDatabase.setBounds(0, 203, 58, 58);
		getContentPane().add(imgDatabase);
	}

	DAO dao = new DAO();
	private JLabel imgDatabase;

	private void statusConexaoBanco() {

		try {

			Connection conexaoBanco = dao.conectar();

			if (conexaoBanco == null) {
				// escolher a imagem
				imgDatabase.setIcon(new ImageIcon(Login.class.getResource("/img/databaseOff.png")));

			}

			else {
				// trocar a imagem se houver conexão
				imgDatabase.setIcon(new ImageIcon(Login.class.getResource("/img/databaseOn.png")));
			}
			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	private void logar() {
		String read = "select * from funcionario where login=? and senha=md5(?)";

		// Validação do login do usuário
		if (inputLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Login do usuário obrigatório!");
			inputLogin.requestFocus();
		}

		// Validação da senha do usuário
		else if (inputSenha.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Senha do usuário obrigatória!");
			inputSenha.requestFocus();
		}

		else {

			try {
				// Estabelecer a conexão
				Connection conexaoBanco = dao.conectar();

				// Preparar a execução do script SQl
				PreparedStatement executarSQL = conexaoBanco.prepareStatement(read);

				// Atribuir valores de login e senha
				// Substituir as interrogações ? ? pelo conteúdo da caixa de texto(input)
				executarSQL.setString(1, inputLogin.getText());
				executarSQL.setString(2, inputSenha.getText());

				// Executar os comandos SQL e de acordo com o resultado liberar os recursos na
				// tela
				ResultSet resultadoExecucao = executarSQL.executeQuery();

				// Validação do funcionário (autenticação)
				// resultadoExecucao.next() significa que o login e a senha existem, ou seja,
				// corespondem

				if (resultadoExecucao.next()) {

					Home home = new Home();
					home.setVisible(true);

					home.txtUsuarioLogado.setText("Usuário: " + resultadoExecucao.getString(2));
					
					// fechar a janela Login assim que a janela Home abrir
					dispose();

				}

				else {
					// Criar um alerta (pop-up) que informe que o login e/ou senha estão inválidos
					JOptionPane.showMessageDialog(null, "Login e/ou senha inválido(s)!");
					inputLogin.setText(null);
					inputSenha.setText(null);
					inputLogin.requestFocus();
				}

				conexaoBanco.close();
			}

			catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login dialog = new Login();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}