package salaleitura.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import salaleitura.model.Administrador;
import salaleitura.model.Aluno;
import salaleitura.model.Emprestimo;
import salaleitura.model.Livro;
import salaleitura.service.SalaLeituraService;

public class SalaLeituraController {
	
	private SalaLeituraService service = new SalaLeituraService();
	private Administrador administrador;
	private String administradorLogado;

	public void setAdministradorLogado(String nome) {
	    this.administradorLogado = nome;
	}

	public String getAdministradorLogado() {
	    return administradorLogado;
	}
	
	public void setAdministrador(Administrador administrador) {
	    this.administrador = administrador;
	}
	


	
	public SalaLeituraController() {
		
		
		/*
		 * service.cadastrarLivro(new Livro (1, "O pequeno principe ",
		 * "Antoine de Saint-Exupéry" , 2)); service.cadastrarLivro(new Livro (2,
		 * "Dom Casmurro", "Machado de Assis",1));
		 */
		
		
	}
	
	public Collection<Livro> listarLivros() {
		return service.listarLivros();
	}
	
	public boolean emprestarLivro(int livroId, String nomeCompleto, String serie) {
	    Aluno aluno = new Aluno(nomeCompleto, serie);
	    service.cadastrarAluno(aluno);
	    
	    
	    
	    String nomeAdmin = administrador.getNome();
	 
	    return service.emprestar(livroId, aluno, nomeAdmin);
	}
	
		
	
	
	public List<Emprestimo> listarEmprestimosAtivos() {
		return service.listarEmprestimosAtivos();
	}
	public void adicionarLivro(int id, String titulo, String autor, int quantidade) {
		Livro livro = new Livro(id, titulo, autor, quantidade);
		service.cadastrarLivro(livro);
	}
	public void devolverEmprestimo(Emprestimo emprestimo) {
		service.devolverEmprestimo(emprestimo);
	
	
	}
	public List<Livro> buscarLivrosPortitulo(String titulo){
		System.out.println("Chegou no controller");
		return service.buscarLivrosPorTitulo(titulo);
	}
	
	public SalaLeituraController(Administrador administrador) {
		this.administrador = administrador;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

}
