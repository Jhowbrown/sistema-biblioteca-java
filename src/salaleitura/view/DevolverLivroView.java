package salaleitura.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import salaleitura.controller.SalaLeituraController;
import salaleitura.model.Emprestimo;

public class DevolverLivroView extends JFrame {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final String CARD_LISTA = "LISTA";
	private static final String CARD_CONFIRMACAO = "CONFIRMACAO";

	private SalaLeituraController controller;
	private MenuView menu;

	private CardLayout cardLayout;
	private JPanel painelCards;

	private Emprestimo emprestimoSelecionado;

	private JLabel lblInfoConfirmacao;

	public DevolverLivroView(SalaLeituraController controller, MenuView menu) {

		this.controller = controller;
		this.menu = menu;

		setTitle("Devolver Livro");
		setSize(500, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		cardLayout = new CardLayout();
		painelCards = new JPanel(cardLayout);

		painelCards.add(criarCardLista(), CARD_LISTA);
		painelCards.add(criarCardConfirmacao(), CARD_CONFIRMACAO);

		setContentPane(painelCards);

		cardLayout.show(painelCards, CARD_LISTA);

		setVisible(true);

	}

	private class EmprestimoCardRenderer extends JPanel implements ListCellRenderer<Emprestimo> {

		private JLabel lblLivro = new JLabel();
		private JLabel lblAluno = new JLabel();
		private JLabel lblSerie = new JLabel();

		public EmprestimoCardRenderer() {

			setLayout(new BorderLayout(5, 5));

			lblLivro.setFont(new Font("Arial", Font.BOLD, 14));
			lblAluno.setFont(new Font("Arial", Font.PLAIN, 12));
			lblSerie.setFont(new Font("Arial", Font.PLAIN, 12));

			JPanel info = new JPanel();
			info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
			info.setOpaque(false);

			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
					BorderFactory.createEmptyBorder(8, 12, 8, 12)

			));

			/* JPanel info = new JPanel(new GridLayout(2,1)); */

			info.add(lblLivro);
			info.add(lblAluno);
			info.add(lblSerie);

			add(info, BorderLayout.CENTER);

		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Emprestimo> list, Emprestimo e, int index,
				boolean isSelected, boolean cellHasFocus) {

			setLayout(new BorderLayout(5, 5));
			removeAll();

			JLabel lblTitulo = new JLabel("📕 " + e.getLivro().getTitulo());
			lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

			JLabel lblAluno = new JLabel("Aluno: " + e.getAluno().getNome());
			JLabel lblSerie = new JLabel("Série: " + e.getAluno().getSerie());

			JPanel painelTexto = new JPanel(new GridLayout(3, 1));

			painelTexto.setOpaque(false);
			painelTexto.add(lblTitulo);
			painelTexto.add(lblAluno);
			painelTexto.add(lblSerie);

			add(painelTexto, BorderLayout.CENTER);

			// ESTILO VISUAL DO CARD

			if (isSelected) {
				setBackground(new Color(210, 225, 255)); // azul claro
				setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(70, 120, 200), 2),
						BorderFactory.createEmptyBorder(8, 10, 8, 10)));
			} else {
				setBackground(Color.WHITE);
				setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			}

			setOpaque(true);
			setPreferredSize(new Dimension(list.getWidth() - 20, 80));

			return this;

		}

	}

	// Buscar empréstimos ativos
	private JPanel criarCardLista() {

		JPanel painel = new JPanel(new BorderLayout(10, 10));

		DefaultListModel<Emprestimo> model = new DefaultListModel<>();
		controller.listarEmprestimosAtivos().forEach(model::addElement);

		JList<Emprestimo> lista = new JList<>(model);
		lista.setCellRenderer(new EmprestimoCardRenderer());
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Painel de detalhes (lado direito)

		JPanel painelDetalhes = new JPanel();
		painelDetalhes.setLayout(new BoxLayout(painelDetalhes, BoxLayout.Y_AXIS));
		painelDetalhes.setPreferredSize(new Dimension(180, 0));
		painelDetalhes.setBorder(BorderFactory.createTitledBorder("Detalhes"));

		JLabel lblAdminTitulo = new JLabel("Administrador");
		lblAdminTitulo.setFont(new Font("Arial ", Font.BOLD, 12));

		JLabel lblAdminValor = new JLabel("-");
		lblAdminValor.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblDataTitulo = new JLabel("Data do empréstimo");
		lblDataTitulo.setFont(new Font("Arial ", Font.BOLD, 12));

		JLabel lblDataValor = new JLabel("-");
		lblDataValor.setFont(new Font("Arial", Font.PLAIN, 12));

		painelDetalhes.add(lblAdminTitulo);
		painelDetalhes.add(lblAdminValor);

		painelDetalhes.add(Box.createVerticalStrut(10));

		painelDetalhes.add(lblDataTitulo);
		painelDetalhes.add(lblDataValor);

		lista.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Emprestimo emp = lista.getSelectedValue();
				if (emp != null) {
					lblAdminValor.setText(emp.getNomeAdministrador());
					lblDataValor.setText(emp.getData().format(formatter));
				}
			}
		});

		JScrollPane scroll = new JScrollPane(lista);

		JButton btnAvançar = new JButton("➡ Selecionar");
		JButton btnVoltar = new JButton("⬅ Voltar");

		btnAvançar.addActionListener(event -> {

			emprestimoSelecionado = lista.getSelectedValue();

			if (emprestimoSelecionado == null) {
				JOptionPane.showMessageDialog(this, "Selecione um empréstimo para devolver.");
				return;
			}

			lblInfoConfirmacao
					.setText("<html><h3>Confirmar devolução?</h3>" + "<p>" + emprestimoSelecionado + "</p></html>");

			cardLayout.show(painelCards, CARD_CONFIRMACAO);

			painelCards.revalidate();
			painelCards.repaint();

		});

		btnVoltar.addActionListener(ev -> {
			dispose();
			menu.setVisible(true);
		});

		JPanel painelBotoes = new JPanel();
		painelBotoes.add(btnAvançar);
		painelBotoes.add(btnVoltar);

		painel.add(new JLabel("📚 Empréstimos Ativos", JLabel.CENTER), BorderLayout.NORTH);
		painel.add(scroll, BorderLayout.CENTER);
		painel.add(painelDetalhes, BorderLayout.EAST);

		painel.add(painelBotoes, BorderLayout.SOUTH);

		return painel;
	}

	private JPanel criarCardConfirmacao() {

		// Texto central que pode ser atualizado dinamicamente

		JPanel painel = new JPanel(new BorderLayout(10, 10));

		lblInfoConfirmacao = new JLabel("Confirmar devolução", JLabel.CENTER);
		lblInfoConfirmacao.setFont(new Font("Arial", Font.BOLD, 14));

		// Botões
		JButton btnConfirmar = new JButton("✅ Confirmar Devolução");
		JButton btnCancelar = new JButton("❌ Cancelar");

		// Painel para organizar os botões
		JPanel painelBotoes = new JPanel();
		painelBotoes.add(btnConfirmar);
		painelBotoes.add(btnCancelar);

		// Adiciona componentes ao painel principal

		painel.add(lblInfoConfirmacao, BorderLayout.CENTER);

		painel.add(painelBotoes, BorderLayout.SOUTH);

		btnConfirmar.addActionListener(event -> {

			controller.devolverEmprestimo(emprestimoSelecionado);

			JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");

			dispose();
			menu.setVisible(true);

		});

		btnCancelar.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				cardLayout.show(painelCards, CARD_LISTA);
				painelCards.revalidate();
				painelCards.repaint();

			});
		});

		return painel;
	}

}
