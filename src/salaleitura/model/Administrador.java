package salaleitura.model;

import java.text.Normalizer;

public class Administrador {
	
	
	
	private String nome;
	
	public Administrador(String nome) {
		if(nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome do administrador não pode ser vazio.");
		}
		
		 nome = Normalizer.normalize(nome.trim(), Normalizer.Form.NFC); 
		 
		if (!nome.matches("[\\p{L} ]+")) {
		    throw new IllegalArgumentException("Nome do administrador deve conter apenas letras.");
		}
		
		 
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}



}
