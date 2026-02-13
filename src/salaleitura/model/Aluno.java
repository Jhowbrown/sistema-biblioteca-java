package salaleitura.model;

import java.text.Normalizer;

public class Aluno {
	
	private String nome;
	private String serie;
	
	public Aluno(String nome, String serie) {

		setNome(nome);
		this.serie = serie;
	}

	public void setSerie(String serie) {

		if (serie == null || !serie.matches("^[1-9]º[A-Z]$")) {
			throw new IllegalArgumentException("Série inválida. Use o formato: 1ºA, 2ºB, 9ºC.");
		}
		this.serie = serie;
	}

	private void setNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome do aluno não pode ser vazio.");
		}
		nome = Normalizer.normalize(nome, Normalizer.Form.NFC);
		
		if (!nome.matches("[A-Za-zÀ-ÿ ]*")) {
			throw new IllegalArgumentException("Nome do aluno deve conter apenas letras.");
		}

		this.nome = nome.trim();

	}

	public String getNome() {
		return nome;
	}

	public String getSerie() {
		return serie;
	}

	@Override
	public String toString() {
		return nome + " - " + serie;
	}

}
