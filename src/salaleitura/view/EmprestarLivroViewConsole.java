package salaleitura.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salaleitura.controller.SalaLeituraController;

public class EmprestarLivroViewConsole extends JFrame {

	private SalaLeituraController controller;
	private MenuView menu;

	public EmprestarLivroViewConsole(SalaLeituraController controller, MenuView menu) {
		
		this.controller = controller;
		this.menu = menu;
		
		setTitle("Emprestar Livro");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Tela Emprestar Livro" , JLabel.CENTER);
		add(label, BorderLayout.NORTH);
		
	
		
		JPanel painel = new JPanel();
		
		painel.setLayout(new GridLayout(5,2,10,10));
		
		JTextField txtLivroId = new JTextField();
		JTextField txtNomeAluno = new JTextField();
		JTextField txtSerie = new JTextField();
		
		painel.add(new JLabel("ID do Livro: "));
		painel.add(txtLivroId);
		
		painel.add(new JLabel("Nome do Aluno: "));
		painel.add(txtNomeAluno);
		
		painel.add(new JLabel("Série: "));
		painel.add(txtSerie);
		
		JButton btnEmprestar = new JButton("📖 Emprestar");
		JButton btnVoltar = new JButton("⬅ Voltar");
		
		painel.add(btnEmprestar);
		painel.add(btnVoltar);
		
		add(painel, BorderLayout.CENTER);
		
		btnEmprestar.addActionListener(ev -> {
			try {
				int livroId = Integer.parseInt(txtLivroId.getText());
				String nome = txtNomeAluno.getText();
				String serie = txtSerie.getText();
				
				boolean sucesso = controller.emprestarLivro(livroId, nome, serie);
				
				if(sucesso) {
					JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso! ");
				}else {
					JOptionPane.showMessageDialog(this, "Erro: livro indisponível ou ID inválido.");
				}
			}catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID do livro deve ser numérico.");
			}
			
			
			
		});
		
		btnVoltar.addActionListener(event -> {
			dispose();
			menu.setVisible(true);
		});

		
        setVisible(true);
	}
}
