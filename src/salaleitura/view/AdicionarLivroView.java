package salaleitura.view;

import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import salaleitura.controller.SalaLeituraController;



public class AdicionarLivroView extends JFrame {
	
	private SalaLeituraController controller;
	private MenuView menu;
	
	public AdicionarLivroView(SalaLeituraController controller, MenuView menu) {
		
		this.controller = controller;
		this.menu = menu;
		
		setTitle("Adicionar Livro");
		setSize(400,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(5,2,5,5));
		
		JTextField txtId = new JTextField();
		JTextField txtTitulo = new JTextField();
		JTextField txtAutor = new JTextField();
		JTextField txtQtd = new JTextField();
		
		JButton btnSalvar = new JButton("Salvar");
		JButton btnVoltar = new JButton("voltar");
		
		add(new Label("ID:"));
		add(txtId);
		add(new Label("Titulo:"));
		add(txtTitulo);
		add(new Label("Autor:"));
		add(txtAutor);
		add(new Label("Quantidade:"));
		add(txtQtd);
		
		add(btnSalvar);
		add(btnVoltar);
		
		btnSalvar.addActionListener(ev ->{
			try {
				int id = Integer.parseInt(txtId.getText());
				int qtd = Integer.parseInt(txtQtd.getText());
				String titulo = txtTitulo.getText();
				String autor = txtAutor.getText();
				
				controller.adicionarLivro(id, titulo, autor, qtd);
				
				JOptionPane.showMessageDialog(this, "Livro Cadastrado com sucesso!");
				dispose();
				menu.setVisible(true);
				
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "ID e quantidade devem ser números.");
			}
		});
		btnVoltar.addActionListener(ev -> {
			dispose();
			menu.setVisible(true);
		});
		
		setVisible(rootPaneCheckingEnabled);
	}

}
