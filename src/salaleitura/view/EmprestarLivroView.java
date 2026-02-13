package salaleitura.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.security.KeyStore.Entry.Attribute;
import java.text.Normalizer;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import salaleitura.controller.SalaLeituraController;
import salaleitura.model.Livro;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;

public class EmprestarLivroView extends JFrame {

	private SalaLeituraController controller;
	private MenuView menu;
	private JTextField txtNome;
	private JTextField txtSerie;

	class ApenasLetrasFilter extends DocumentFilter {

	    @Override
	    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
	            throws BadLocationException {

	        if (text == null) return;

	        String novoTexto = fb.getDocument().getText(0, fb.getDocument().getLength());
	        novoTexto = novoTexto.substring(0, offset) + text;

	        if (novoTexto.matches("[\\p{L} ]*")) {
	            super.insertString(fb, offset, text, attr);
	        }
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
	            throws BadLocationException {

	        if (text == null) return;

	        String atual = fb.getDocument().getText(0, fb.getDocument().getLength());
	        String novo = atual.substring(0, offset) + text + atual.substring(offset + length);

	        if (novo.matches("[\\p{L} ]*")) {
	            super.replace(fb, offset, length, text, attrs);
	        }
	    }
	}


	class SerieFilter extends DocumentFilter {

		private static final String PADRAO_PROGRESSIVO = "\\d{0,2}[º°]?[A-Za-z]?";;

		public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
				throws BadLocationException {

			if (text == null)
				return;

			String atual = fb.getDocument().getText(0, fb.getDocument().getLength());
			String novo = atual.substring(0, offset) + text + atual.substring(offset);

			if (novo.matches(PADRAO_PROGRESSIVO)) {
				// opcional: normaliza ° → º
				text = text.replace('°', 'º');
				super.insertString(fb, offset, text.toUpperCase(), attr);
			}
		}

		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {

			if (text == null)
				return;

			String atual = fb.getDocument().getText(0, fb.getDocument().getLength());
			String novo = atual.substring(0, offset) + text + atual.substring(offset + length);

			if (novo.matches(PADRAO_PROGRESSIVO)) {
				super.replace(fb, offset, length, text.toUpperCase(), attrs);
			}
		}

	}

	public EmprestarLivroView(SalaLeituraController controller, MenuView menu) {

		txtNome = new JTextField();
		txtSerie = new JTextField();

		// Aplicando filtros de validação
		

		((AbstractDocument) txtSerie.getDocument()).setDocumentFilter(new SerieFilter());

		this.controller = controller;
		this.menu = menu;

		setTitle("📖 Emprestar Livro");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel painelBusca = new JPanel(new BorderLayout(5, 5));

		JPanel painelCampos = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JTextField txtTitulo = new JTextField(20);
		JButton btnBuscar = new JButton("🔍 Buscar");

		painelCampos.add(new JLabel("título do Livro:"));
		painelCampos.add(txtTitulo);
		painelCampos.add(btnBuscar);

		painelBusca.add(painelCampos, BorderLayout.CENTER);

		// Lista para mostrar os livros encontrados
		String[] colunas = { "ID", "Título", "Autor", "Disponível" };

		DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tabela só leitura
			}
		};

		JTable tabela = new JTable(tableModel);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(tabela);

		// Ação do botão BUSCAR

		btnBuscar.addActionListener(e -> {
			tableModel.setRowCount(0); // limpa tabela

			String titulo = txtTitulo.getText();

			List<Livro> encontrados = controller.buscarLivrosPortitulo(titulo);

			if (encontrados.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nenhum livro encontrado. ");

			} else {
				for (Livro livro : encontrados) {
					tableModel.addRow(
							new Object[] { livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getExemplaresDisponiveis()

							});
				}
			}

		});

		JPanel painelAluno = new JPanel(new GridLayout(2, 2, 5, 5));

		txtSerie.setToolTipText("Padrão: 1ºA, 2ºB, 9ºC");

		painelAluno.add(new JLabel("Nome do aluno: "));
		painelAluno.add(txtNome);

		painelAluno.add(new JLabel("Série (ex: 1ºA): "));
		painelAluno.add(txtSerie);

		JButton btnEmprestar = new JButton("📖 Emprestar");
		JButton btnVoltar = new JButton("⬅ Voltar");

		btnEmprestar.addActionListener(e -> {
			try {
				int linha = tabela.getSelectedRow();
				if (linha == -1) {
					JOptionPane.showMessageDialog(this, "Selecione um livro.");
					return;
				}

				int livroId = (int) tableModel.getValueAt(linha, 0);
				String nome = txtNome.getText().trim();
				String serie = txtSerie.getText().trim();

				if (nome.isEmpty() || serie.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Preencha nome e série.");
					return;
				}

				boolean sucesso = controller.emprestarLivro(livroId, nome, serie);

				if (sucesso) {
					JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso!");
				} else {
					JOptionPane.showMessageDialog(this, "Livro indisponível.");
				}

			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de validação", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnVoltar.addActionListener(e -> {
			dispose();
			menu.setVisible(true);
		});

		JPanel painelSul = new JPanel(new GridLayout(3, 1, 5, 5));
		painelSul.add(painelAluno);

		JPanel painelBotoes = new JPanel();
		painelBotoes.add(btnEmprestar);
		painelBotoes.add(btnVoltar);

		painelSul.add(painelBotoes);

		add(painelBusca, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(painelSul, BorderLayout.SOUTH);

		setVisible(true);

	}

}
