package view;

import javax.swing.JDialog;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;
import net.proteanit.sql.DbUtils;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Cursor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

public class Salas extends JDialog {
	private JTextField inputOcup;

	public JButton imgCreate;
	public JButton imgUpdate;
	public JButton imgDelete;

	public Salas() {
		setTitle("Salas");
		setResizable(false);
		setBounds(new Rectangle(300, 100, 614, 403));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);

		JLabel tipoSala = new JLabel("Categoria:");
		tipoSala.setBounds(34, 44, 81, 14);
		getContentPane().add(tipoSala);

		JLabel codSala = new JLabel("Código:");
		codSala.setBounds(41, 196, 41, 14);
		getContentPane().add(codSala);

		JLabel andarSala = new JLabel("Andar:");
		andarSala.setBounds(320, 196, 39, 14);
		getContentPane().add(andarSala);

		JLabel ocupSala = new JLabel("Ocupação máxima:");
		ocupSala.setBounds(263, 259, 119, 14);
		getContentPane().add(ocupSala);

		JLabel numSala = new JLabel("Número:");
		numSala.setBounds(34, 259, 65, 14);
		getContentPane().add(numSala);

		inputOcup = new JTextField();
		inputOcup.setBounds(365, 256, 178, 20);
		getContentPane().add(inputOcup);
		inputOcup.setColumns(10);

		inputOcup.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				buscarSalaNaTabela();
			}
		});

		imgCreate = new JButton("");
		imgCreate.setBackground(new Color(240, 240, 240));
		imgCreate.setBorder(null);
		imgCreate.setBorderPainted(false);
		imgCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgCreate.setIcon(new ImageIcon(Salas.class.getResource("/img/create.png")));
		imgCreate.setBounds(339, 290, 65, 54);
		getContentPane().add(imgCreate);

		imgCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarSala();
			}
		});

		imgUpdate = new JButton("");
		imgUpdate.setBackground(new Color(240, 240, 240));
		imgUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		imgUpdate.setBorderPainted(false);
		imgUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgUpdate.setIcon(new ImageIcon(Salas.class.getResource("/img/update.png")));
		imgUpdate.setBounds(414, 290, 65, 54);
		getContentPane().add(imgUpdate);

		imgUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atualizarSala();
			}
		});

		imgDelete = new JButton("");
		imgDelete.setBackground(new Color(240, 240, 240));
		imgDelete.setBorderPainted(false);
		imgDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgDelete.setIcon(new ImageIcon(Salas.class.getResource("/img/delete.png")));
		imgDelete.setBounds(489, 290, 65, 54);
		getContentPane().add(imgDelete);

		imgDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// deletarSala();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(114, 62, 375, 60);
		getContentPane().add(scrollPane);

		tblSalas = new JTable();
		scrollPane.setViewportView(tblSalas);

		JButton btnPesquisar = new JButton("");
		btnPesquisar.setBackground(new Color(240, 240, 240));
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setIcon(new ImageIcon(Salas.class.getResource("/img/search.png")));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBuscarSala();
			}
		});
		btnPesquisar.setBounds(197, 142, 27, 23);
		getContentPane().add(btnPesquisar);

		inputID = new JTextField();
		inputID.setEnabled(false);
		inputID.setBounds(79, 145, 108, 20);
		getContentPane().add(inputID);
		inputID.setColumns(10);

		IDSala = new JLabel("ID:");
		IDSala.setBounds(47, 148, 46, 14);
		getContentPane().add(IDSala);

		inputCategoria = new JComboBox();
		inputCategoria.setModel(new DefaultComboBoxModel(new String[] { "", "Sala de reunião", "Sala de conferência",
				"Espaço de eventos", "Escritório privado" }));
		inputCategoria.setBounds(114, 40, 375, 22);
		getContentPane().add(inputCategoria);
		
		inputCategoria.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				buscarSalaNaTabela();
			}
		});

		inputCod = new JComboBox();
		inputCod.setModel(new DefaultComboBoxModel(new String[] { "", "REU", "CONF", "EVENT", "PRIV" }));
		inputCod.setBounds(84, 192, 127, 22);
		getContentPane().add(inputCod);

		inputNum = new JTextField();
		inputNum.setColumns(10);
		inputNum.setBounds(84, 256, 127, 20);
		getContentPane().add(inputNum);

		inputAndar = new JComboBox();
		inputAndar.setModel(new DefaultComboBoxModel(new String[] {"", "Subsolo", "Térreo", "1º Andar", "2º Andar", "3º Andar"}));
		inputAndar.setBounds(365, 191, 127, 22);
		getContentPane().add(inputAndar);

		tblSalas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setarCaixasTexto();
			}
		});
	}

	// Criar um objeto da classe DAO para estabelecer conexão com banco
	DAO dao = new DAO();
	private JTable tblSalas;
	private JTextField inputID;
	private JLabel IDSala;
	private JComboBox inputCod;
	private JTextField inputNum;
	private JComboBox inputCategoria;
	private JComboBox inputAndar;

	private void adicionarSala() {
		String create = "insert into salas (andarSala, numeroSala, tipoSala, codigoSala, ocupacaoSala) values (?, ?, ?, ?, ?);";

		// Validação da categoria(tipo) da sala
		if (inputCategoria.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Categoria da sala obrigatória!");
			inputCategoria.requestFocus();
		}

		// Validação do código da sala
		else if (inputCod.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Código da sala obrigatório!");
			inputCod.requestFocus();
		}

		// Validaçaõ do andar da sala
		else if (inputAndar.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Andar da sala obrigatório!");
			inputAndar.requestFocus();
		}

		// Validação do número da sala
		else if (inputNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Número da sala obrigatório!");
			inputNum.requestFocus();

		}

		// Validação do número da sala
		else if (inputOcup.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ocupação máxima obrigatório!");
			inputOcup.requestFocus();
		}

		else {

			try {
				// Estabelecer a conexão
				Connection conexaoBanco = dao.conectar();

				// Preparar a execusão do script SQL
				PreparedStatement executarSQL = conexaoBanco.prepareStatement(create);

				// Substituir os pontos de interrogação pelo conteúdo das caixas de texto
				// (inputs)
				executarSQL.setString(1, inputAndar.getSelectedItem().toString());
				executarSQL.setString(2, inputNum.getText());
				executarSQL.setString(3, inputCategoria.getSelectedItem().toString());
				executarSQL.setString(4, inputCod.getSelectedItem().toString());
				executarSQL.setString(5, inputOcup.getText());

				// Executar os comandos SQL e inserir a sala no banco de dados
				executarSQL.executeUpdate();

				JOptionPane.showMessageDialog(null, "Sala cadastrada com sucesso!");

				limparCampos();

				conexaoBanco.close();
			}

			catch (SQLIntegrityConstraintViolationException error) {
				JOptionPane.showMessageDialog(null, "Sala já cadastrada.");
			}

			catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	   private void limparCampos() {
		inputCategoria.setSelectedItem("");
		inputCod.setSelectedItem("");
		inputAndar.setSelectedItem("");
		inputNum.setText(null);
		inputOcup.setText(null);

		inputCategoria.requestFocus();
	}

	private void buscarSalaNaTabela() {
		String readTabela = "select tipoSala as Categoria, andarSala as Andar, numeroSala as Número where idSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();
			// Preparar a execução dos comandos SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readTabela);
			
			// Substituir o ? pelo conteúdo da caixa de texto
			executarSQL.setString(1, inputCategoria.getSelectedItem().toString());
			
			// Executar o comando SQL
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			// Exibir o resultado na tabela, utilização da biblioteca rs2xml para "popular"
			// a tabela
			tblSalas.setModel(DbUtils.resultSetToTableModel(resultadoExecucao));
			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setarCaixasTexto() {
		// Criar uma variável para receber a linha da tabela
		int setarLinha = tblSalas.getSelectedRow();  
		inputNum.setText(tblSalas.getModel().getValueAt(setarLinha, 2).toString());
	}

	// Criar método para buscar funcionário pelo botão pesquisar

	private void btnBuscarSala() {
		String readBtn = "select tipoSala as Categoria, andarSala as Andar, numeroSala as Número where idSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução do comando SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readBtn);

			// Substituir o ponto de interrogação pelo conteúdo da caixa de texto (numero da sala)
			executarSQL.setString(1, inputNum.getText());

			// Executar o comando SQL e exebir oresultado no formulário funcionário (todos
			// os seus dados)
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			if (resultadoExecucao.next()) {
				// Preencher os campos do formulário
				inputAndar.setSelectedItem(resultadoExecucao.getString(1));
				inputCod.setSelectedItem(resultadoExecucao.getString(2));
				inputOcup.setText(resultadoExecucao.getString(3));
			}
		     	conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	private void atualizarSala() {
		String updateBtn = "update salas set andarSala = ?, numeroSala = ?, tipoSala  = ?, codigoSala = ?, ocupacaoSala = ?  where idSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução do comando SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(updateBtn);

			// Substituir
			executarSQL.setString(1, inputAndar.getSelectedItem().toString());
			executarSQL.setString(2, inputNum.getText());
			executarSQL.setString(3, inputCategoria.getSelectedItem().toString());
			executarSQL.setString(4, inputCod.getSelectedItem().toString());
			executarSQL.setString(5, inputOcup.getText());

			executarSQL.executeUpdate();

			conexaoBanco.close();

			JOptionPane.showMessageDialog(null, "Sala editada com sucesso!");

			limparCampos();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void deletarSala() {
		String updateBtn = "delete from salas where idSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução do comando SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(updateBtn);

			// Substituir

			executarSQL.setString(1, inputID.getText());

			executarSQL.executeUpdate();

			conexaoBanco.close();

			JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");

			limparCampos();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Salas dialog = new Salas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}