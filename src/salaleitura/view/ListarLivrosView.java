package salaleitura.view;

import java.awt.*;
import salaleitura.controller.SalaLeituraController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ListarLivrosView extends JFrame {

	public ListarLivrosView(SalaLeituraController controller, MenuView menu) {
		setTitle("Livros Cadastrados");
		setSize(500, 400);
		setLocationRelativeTo(null);
		// Criar área de texto para exibir livros

		JTextArea area = new JTextArea();
		area.setEditable(false);

		controller.listarLivros().forEach(livro -> {
			area.append(livro.toString() + "\n");

		});
		JScrollPane scroll = new JScrollPane(area);

		JButton voltar = new JButton("⬅ Voltar");

		voltar.addActionListener(ev -> {
			dispose(); // Fecha essa tela
			menu.setVisible(true); // Mostra o menu novamente
		});

		// Organizar layout da tela

		add(scroll, BorderLayout.CENTER);
		add(voltar, BorderLayout.SOUTH);
		setVisible(true);

	}

}
