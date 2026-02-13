package salaleitura.app;

import javax.swing.JOptionPane;
import salaleitura.controller.SalaLeituraController;
import salaleitura.model.Administrador;
import salaleitura.view.MenuView;

public class Main {

	public static void main(String[] args) {
		
	    String nomeAdmin = JOptionPane.showInputDialog(
	            null,
	            "Informe o nome do administrador:",
	            "Acesso Administrativo",
	            JOptionPane.QUESTION_MESSAGE
	        );

	        if (nomeAdmin == null || nomeAdmin.trim().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Administrador não informado.");
	            System.exit(0);
	        }

	        Administrador admin = new Administrador(nomeAdmin);

	        SalaLeituraController controller = new SalaLeituraController();
	        controller.setAdministrador(admin);
	        

	        new MenuView(controller);
	    }
}


