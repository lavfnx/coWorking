package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.DAO;
import net.proteanit.sql.DbUtils;

// Importação 
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;

import model.DAO;
import net.proteanit.sql.DbUtils;

public class Reservas extends JDialog {

	public JButton imgCreate;
	public JButton imgUpdate;
	public JButton imgDelete;

	public Reservas() {
		setTitle("Reservas");
		setResizable(false);
		setBounds(new Rectangle(300, 100, 724, 446));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		getContentPane().setLayout(null);

		JLabel tipoReserva = new JLabel("Categoria:");
		tipoReserva.setBounds(24, 29, 74, 14);
		getContentPane().add(tipoReserva);

		JLabel andarSala = new JLabel("Andar:");
		andarSala.setBounds(41, 284, 57, 14);
		getContentPane().add(andarSala);

		JLabel numReserva = new JLabel("Número:");
		numReserva.setBounds(24, 203, 74, 14);
		getContentPane().add(numReserva);

		imgCreate = new JButton("");
		imgCreate.setBackground(new Color(240, 240, 240));
		imgCreate.setBorderPainted(false);
		imgCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgCreate.setIcon(new ImageIcon(Reservas.class.getResource("/img/create.png")));
		imgCreate.setBounds(392, 342, 65, 54);
		getContentPane().add(imgCreate);

		imgCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarReserva();
			}
		});

		imgUpdate = new JButton("");
		imgUpdate.setEnabled(false);
		imgUpdate.setBackground(new Color(240, 240, 240));
		imgUpdate.setBorderPainted(false);
		imgUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgUpdate.setIcon(new ImageIcon(Reservas.class.getResource("/img/update.png")));
		imgUpdate.setBounds(488, 342, 65, 54);
		getContentPane().add(imgUpdate);

		imgUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atualizarReserva();
			}
		});

		imgDelete = new JButton("");
		imgDelete.setEnabled(false);
		imgDelete.setBackground(new Color(240, 240, 240));
		imgDelete.setBorderPainted(false);
		imgDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imgDelete.setIcon(new ImageIcon(Reservas.class.getResource("/img/delete.png")));
		imgDelete.setBounds(581, 342, 65, 54);
		getContentPane().add(imgDelete);

		imgDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// deletarReserva();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(95, 54, 516, 90);
		getContentPane().add(scrollPane);

		tblSalas = new JTable();
		scrollPane.setViewportView(tblSalas);

		JButton btnPesquisar = new JButton("");
		btnPesquisar.setEnabled(false);
		btnPesquisar.setBackground(new Color(240, 240, 240));
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setIcon(new ImageIcon(Reservas.class.getResource("/img/search.png")));
		btnPesquisar.setBounds(265, 193, 43, 33);
		getContentPane().add(btnPesquisar);

		inputID = new JTextField();
		inputID.setEnabled(false);
		inputID.setBounds(24, 160, 40, 20);
		getContentPane().add(inputID);
		inputID.setColumns(10);

		// Deixar o campo ID invisível
		inputID.setVisible(false);

		inputCategoria = new JComboBox();
		inputCategoria.setToolTipText("");
		inputCategoria.setModel(new DefaultComboBoxModel(new String[] { "", "Sala de reunião", "Sala de conferência",
				"Espaço de eventos", "Escritório privado" }));
		inputCategoria.setBounds(95, 25, 516, 22);
		getContentPane().add(inputCategoria);

		inputCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarSalaNaTabela();
			}
		});

		inputAndar = new JComboBox();
		inputAndar.setModel(
				new DefaultComboBoxModel(new String[] { "", "Subsolo", "Térreo", "1º andar", "2º andar", "3º andar" }));
		inputAndar.setBounds(95, 280, 160, 22);
		getContentPane().add(inputAndar);

		inputNum = new JTextField();
		inputNum.setBounds(95, 200, 160, 20);
		getContentPane().add(inputNum);
		inputNum.setColumns(10);

		reponsavelReserva = new JLabel("Responsável:");
		reponsavelReserva.setBounds(364, 203, 104, 14);
		getContentPane().add(reponsavelReserva);

		inputResponsavel = new JTextField();
		inputResponsavel.setBounds(442, 200, 86, 20);
		getContentPane().add(inputResponsavel);
		inputResponsavel.setColumns(10);

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBuscarSala();
			}
		});

		tblSalas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setarCaixasTexto();
				btnPesquisar.setEnabled(true);
				imgCreate.setEnabled(false);
				imgDelete.setEnabled(true);
			}
		});

		inputInicioReserva = new JDateChooser();
		inputInicioReserva.setBounds(442, 258, 137, 20);
		getContentPane().add(inputInicioReserva);

		inputFimReserva = new JDateChooser();
		inputFimReserva.setBounds(442, 300, 137, 20);
		getContentPane().add(inputFimReserva);

	} // Fim do construtor

	// Criar um objeto da classe DAO para estabelecer conexão com banco
	DAO dao = new DAO();
	private JTable tblSalas;
	private JTextField inputID;
	private JComboBox inputCategoria;
	private JComboBox inputAndar;
	private JTextField inputNum;
	private JLabel reponsavelReserva;
	private JTextField inputResponsavel;
	private JDateChooser inputInicioReserva;
	private JDateChooser inputFimReserva;

	private void adicionarReserva() {
		String create = "insert into reservas (idSala, responsavelReserva, inicioReserva, fimReserva)"
				+ " values (?, ?, ?, ?);";

		// Validação do responsável pela reserva
		if (inputResponsavel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Responsável pela reserva obrigatório!");
			inputResponsavel.requestFocus();
		}

		/*
		 * Validação início da Reserva else if (inputCod.getSelectedItem().equals("")) {
		 * JOptionPane.showMessageDialog(null, "Código da Reserva obrigatório!");
		 * inputCod.requestFocus(); }
		 * 
		 * // Validação do fim da Reserva else if
		 * (inputAndar.getSelectedItem().equals("")) {
		 * JOptionPane.showMessageDialog(null, "Andar da Reserva obrigatório!");
		 * inputAndar.requestFocus(); }
		 */

		else {

			try {
				// Estabelecer a conexão
				Connection conexaoBanco = dao.conectar();

				// Preparar a execusão do script SQL
				PreparedStatement executarSQL = conexaoBanco.prepareStatement(create);

				// Substituir os pontos de interrogação pelo conteúdo das caixas de texto
				// (inputs)
				executarSQL.setString(1, inputID.getText());
				executarSQL.setString(2, inputResponsavel.getText());

				// Formatar a data a reserva para inserção correta no banco
				SimpleDateFormat formatadorReserva = new SimpleDateFormat("YYYYMMdd");
				String dataInicioReservaFormatada = formatadorReserva.format(inputInicioReserva.getDate());
				executarSQL.setString(3, dataInicioReservaFormatada);

				SimpleDateFormat formatadorReserva2 = new SimpleDateFormat("YYYYMMdd");
				String dataFimReservaFormatada = formatadorReserva2.format(inputFimReserva.getDate());
				executarSQL.setString(4, dataFimReservaFormatada);

				// Executar os comandos SQL e inserir a Reserva no banco de dados
				executarSQL.executeUpdate();

				JOptionPane.showMessageDialog(null, "Reserva cadastrada com sucesso!");
				// limparCampos();

				conexaoBanco.close();
			}

			catch (SQLIntegrityConstraintViolationException error) {
				JOptionPane.showMessageDialog(null, "Reserva já cadastrada");
			}

			catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscarSalaNaTabela() {
		String readTabela = "select tipoSala as Categoria, andarSala as Andar, numeroSala as Número from salas where tipoSala = ?;";

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

		inputCategoria.setSelectedItem(tblSalas.getModel().getValueAt(setarLinha, 0).toString());

		// Setar o andar e o número da Reserva selecionada na linha específica da tabela
		// que o usuário clicou
		inputAndar.setSelectedItem(tblSalas.getModel().getValueAt(setarLinha, 1).toString());

		inputNum.setText(tblSalas.getModel().getValueAt(setarLinha, 2).toString());
	}

	// Criar método para buscar Reserva pelo botão Pesquisar
	private void btnBuscarSala() {
		String readBtn = "select * from salas where numeroSala = ? and andarSala = ?;";

		try {
			// Estabelecer a conexão
			Connection conexaoBanco = dao.conectar();

			// Preparar a execução do comando SQL
			PreparedStatement executarSQL = conexaoBanco.prepareStatement(readBtn);

			// Substituir o ponto de interrogação pelo conteúdo da caixa de texto (número da
			// Reserva)
			executarSQL.setString(1, inputNum.getText());
			executarSQL.setString(2, inputAndar.getSelectedItem().toString());

			// Executar o comando SQL e exibir o resultado no formulário Reservas (todos
			// os seus dados)
			ResultSet resultadoExecucao = executarSQL.executeQuery();

			if (resultadoExecucao.next()) {
				// Preencher os campos do formulário
				inputID.setText(resultadoExecucao.getString(1));

				imgUpdate.setEnabled(true);
				imgDelete.setEnabled(true);
				imgCreate.setEnabled(true);
			}

			conexaoBanco.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * private void atualizarReserva() { String update =
	 * "update Reservas set andarSala = ?, numeroSala = ?, tipoReserva = ?, codigoReserva = ?,"
	 * + " ocupacaoReserva = ? where idReserva = ?;";
	 * 
	 * // Validação da categoria (tipo) da Reserva if
	 * (inputCategoria.getSelectedItem().equals("")) {
	 * JOptionPane.showMessageDialog(null, "Categoria da Reserva obrigatória!");
	 * inputCategoria.requestFocus(); }
	 * 
	 * // Validação do código da Reserva else if
	 * (inputCod.getSelectedItem().equals("")) { JOptionPane.showMessageDialog(null,
	 * "Código da Reserva obrigatório!"); inputCod.requestFocus(); }
	 * 
	 * // Validação do andar da Reserva else if
	 * (inputAndar.getSelectedItem().equals("")) {
	 * JOptionPane.showMessageDialog(null, "Andar da Reserva obrigatório!");
	 * inputAndar.requestFocus(); }
	 * 
	 * // Validação do número da Reserva else if (inputNum.getText().isEmpty()) {
	 * JOptionPane.showMessageDialog(null, "Número da Reserva obrigatório!");
	 * inputNum.requestFocus(); }
	 * 
	 * // Validação da ocupação máxima da Reserva else if
	 * (inputOcup.getText().isEmpty()) { JOptionPane.showMessageDialog(null,
	 * "Ocupação máxima obrigatória!"); inputOcup.requestFocus(); }
	 * 
	 * else { try { // Estabelecer a conexão Connection conexaoBanco =
	 * dao.conectar();
	 * 
	 * // Preparar a execusão do script SQL PreparedStatement executarSQL =
	 * conexaoBanco.prepareStatement(update);
	 * 
	 * // Substituir os pontos de interrogação pelo conteúdo das caixas de texto //
	 * (inputs) executarSQL.setString(1, inputAndar.getSelectedItem().toString());
	 * executarSQL.setString(2, inputNum.getText()); executarSQL.setString(3,
	 * inputCategoria.getSelectedItem().toString()); executarSQL.setString(4,
	 * inputCod.getSelectedItem().toString()); executarSQL.setString(5,
	 * inputOcup.getText()); executarSQL.setString(6, inputID.getText());
	 * 
	 * // Executar os comandos SQL e atualizar a Reserva no banco de dados
	 * executarSQL.executeUpdate();
	 * 
	 * JOptionPane.showMessageDialog(null,
	 * "Dados da Reserva atualizados com sucesso!"); limparCampos();
	 * 
	 * conexaoBanco.close(); }
	 * 
	 * catch (SQLIntegrityConstraintViolationException error) {
	 * JOptionPane.showMessageDialog(null,
	 * "Ocorreu um erro. \nEsta Reserva já encontra-se cadastrada."); }
	 * 
	 * catch (Exception e) { System.out.println(e); } } }
	 * 
	 * private void deletarReserva() { String delete =
	 * "delete from Reservas where numeroSala = ? and andarSala = ?;";
	 * 
	 * try { Connection conexaoBanco = dao.conectar();
	 * 
	 * PreparedStatement executarSQL = conexaoBanco.prepareStatement(delete);
	 * 
	 * executarSQL.setString(1, inputNum.getText()); executarSQL.setString(2,
	 * inputAndar.getSelectedItem().toString());
	 * 
	 * executarSQL.executeUpdate();
	 * 
	 * JOptionPane.showMessageDialog(null, "Reserva deletada com sucesso!");
	 * 
	 * limparCampos(); ((DefaultTableModel)tblSalas.getModel()).setRowCount(0);
	 * conexaoBanco.close();
	 * 
	 * } catch (Exception e) { System.out.println(e); } }
	 * 
	 * private void limparCampos() { inputCategoria.setSelectedItem(null);
	 * inputCod.setSelectedItem(null); inputAndar.setSelectedItem(null);
	 * inputNum.setText(null); inputOcup.setText(null);
	 * inputCategoria.requestFocus(); imgCreate.setEnabled(true);
	 * imgDelete.setEnabled(false);
	 * 
	 * }
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas dialog = new Reservas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}