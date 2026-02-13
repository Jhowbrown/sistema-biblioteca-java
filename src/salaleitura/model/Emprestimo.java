package salaleitura.model;

import java.time.LocalDate;

public class Emprestimo {
	
	private Aluno aluno;
	private Livro livro;
	private boolean ativo = true;
	private String nomeAdministrador;
	private LocalDate data;
	
	
	public Emprestimo(Aluno aluno, Livro livro, String nomeAdministrador) {
		this.aluno = aluno;
		this.livro = livro;
		this.nomeAdministrador = nomeAdministrador;
		this.data = LocalDate.now();
	}

	public Aluno getAluno() {
		return aluno;
	}

	public Livro getLivro() {
		return livro;
	}

	public boolean isAtivo() {
		return ativo;
	}
	public void devolver() {
		this.ativo = false;
	}
	
	
	@Override
	
	public String toString() {
		return getLivro().getTitulo() + 
				" | Aluno: " + getAluno().getNome() +
				" | Série: " + getAluno().getSerie();
	}

	public String getNomeAdministrador() {
		return nomeAdministrador;
	}

	public LocalDate getData() {
		return data;
	}

}
