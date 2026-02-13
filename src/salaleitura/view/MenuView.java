package salaleitura.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import salaleitura.controller.SalaLeituraController;

//Criar o construtor da tela
public class MenuView extends JFrame {
	
	private SalaLeituraController controller;
	
	public MenuView(SalaLeituraController controller) {
		
		this.controller = controller;
		
		setTitle("Bem-vindo a sala de Leitura");
		setSize(800,600);  // Largura x altura
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//Criar um PAINEL(botões)
		JPanel painel = new JPanel();
		painel.setLayout(new GridLayout(4,1,10,10));
		
		//Criar os BOTÕES
		
		
		JButton btnEmprestar = new JButton("📖 Emprestar Livro");
		JButton btnDevolver = new JButton("🔁 Devolver Livro");
		
		JButton btnAdicionar = new JButton("➕ Adicionar Livro");
		JButton btnSair = new JButton("❌ Sair");
		
		// ================= RODAPÉ =================
		JLabel lblRodape = new JLabel(
		    "© 2026 Sala de Leitura - Desenvolvido por Prof. João Santiago",
		    JLabel.CENTER
		);

		lblRodape.setFont(new Font("Arial", Font.PLAIN, 11));
		lblRodape.setForeground(Color.WHITE); // agora branco

		JPanel painelRodape = new JPanel(new BorderLayout());

		// Fundo azul
		painelRodape.setBackground(new Color(25, 55, 95));

		// Borda superior + espaçamento
		painelRodape.setBorder(
		    BorderFactory.createCompoundBorder(
		        BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
		        BorderFactory.createEmptyBorder(5, 0, 5, 0)
		    )
		);

		painelRodape.add(lblRodape, BorderLayout.CENTER);


		// adiciona no final da tela
		add(painelRodape, BorderLayout.SOUTH);

		
		//Adicionar os botões ao painel
		
		
		painel.add(btnEmprestar);
		painel.add(btnDevolver);
		
		painel.add(btnAdicionar);
		painel.add(btnSair);
		
		add(painel);
		setVisible(true);
		//o clique dos botões (ActionListener)
		
		
			
			
			
	
		
		btnEmprestar.addActionListener(ev -> {
			new EmprestarLivroView(controller, this);
			setVisible(false);
		});
		
		btnDevolver.addActionListener(e -> {
		    new DevolverLivroView(controller, this);
		    setVisible(false);
		});
		
		btnAdicionar.addActionListener( event -> {
			new AdicionarLivroView(controller, this);
			setVisible(false);
		});
		
		btnSair.addActionListener(e -> System.exit(0));
		
	}
	
	
	

}
