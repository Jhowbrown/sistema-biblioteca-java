package salaleitura.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import salaleitura.model.Aluno;
import salaleitura.model.Emprestimo;
import salaleitura.model.Livro;
import salaleitura.persistence.EmprestimoCSVRepository;
import salaleitura.persistence.LivroCSVRepository;


public class SalaLeituraService {

	private Map<Integer, Livro> livros = new HashMap<>();
	private List<Aluno> alunos = new ArrayList<>();
	private List<Emprestimo> emprestimos = new ArrayList<>();

	private LivroCSVRepository livroRepo = new LivroCSVRepository("dados/livros.csv");

	private EmprestimoCSVRepository emprestimoRepo = new EmprestimoCSVRepository("dados/emprestimos.csv");

	public SalaLeituraService() {
		carregarLivrosDoCSV();
		carregarEmprestimosDoCSV();
	}

	// Alunos

	private void carregarEmprestimosDoCSV() {
		this.emprestimos = emprestimoRepo.carregar(livros);
		
	}

	private void carregarLivrosDoCSV() {
		List<Livro> lista = livroRepo.carregar();
		for (Livro livro : lista) {
			livros.put(livro.getId(), livro);
		}
		

	}

	public void cadastrarAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public Aluno buscarAlunoporNome(String nome) {
		return alunos.stream().filter(a -> a.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
	}

	// Livros

	public void cadastrarLivro(Livro livro) {
		livros.put(livro.getId(), livro);
		salvarLivrosNoCSV();
	}

	private void salvarLivrosNoCSV() {
		livroRepo.salvar(livros.values());
	}

	public void salvarLivros() {
		livroRepo.salvar(new ArrayList<>(livros.values()));
	}

	public Collection<Livro> listarLivros() {
		return livros.values();
	}

	public List<Livro> buscarLivrosPorTitulo(String titulo) {

		return livros.values().stream().filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
				.collect(Collectors.toList());
	}

	// Empréstimos

	public boolean emprestar(int livroId, Aluno aluno, String nomeAdmin) {
		Livro livro = livros.get(livroId);
		if (livro == null)
			return false;
		if (!livro.temDisponivel())
			return false;

		livro.emprestar();

		emprestimos.add(new Emprestimo(aluno, livro, nomeAdmin));

		// SALVA NO CSV
		emprestimoRepo.salvar(emprestimos);

		return true;

	}

	public boolean devolver(int LivroId) {
		for (Emprestimo e : emprestimos) {
			if (e.getLivro().getId() == LivroId && e.isAtivo()) {
				e.devolver();
				e.getLivro().devolver();
				return true;
			}

		}

		return false;
	}

	public List<Emprestimo> listarEmprestimosAtivos() {
		return emprestimos.stream().filter(Emprestimo::isAtivo).collect(Collectors.toList());

	}

	public void devolverEmprestimo(Emprestimo emprestimo) {
		emprestimo.devolver(); // marca como inativo
		emprestimo.getLivro().devolver();// aumenta a quantidade disponivel
		
		emprestimoRepo.salvar(emprestimos);
	}

	public List<Livro> buscarLivrosPorTitulos(String titulo) {

		return livros.values().stream().filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
				.collect(Collectors.toList());
	}

}
