package salaleitura.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import salaleitura.model.Aluno;
import salaleitura.model.Emprestimo;
import salaleitura.model.Livro;

public class EmprestimoCSVRepository {

    private String caminhoArquivo;

    private DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmprestimoCSVRepository(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    // SALVAR TODOS OS EMPRÉSTIMOS
    public void salvar(List<Emprestimo> emprestimos) {

        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(caminhoArquivo))) {

            bw.write("livroId;titulo;aluno;serie;administrador;data;ativo");
            bw.newLine();

            for (Emprestimo e : emprestimos) {

                bw.write(
                        e.getLivro().getId() + ";" +
                        e.getLivro().getTitulo() + ";" +
                        e.getAluno().getNome() + ";" +
                        e.getAluno().getSerie() + ";" +
                        e.getNomeAdministrador() + ";" +
                        e.getData().format(formatter) + ";" +
                        e.isAtivo()
                );

                bw.newLine();
            }

        } catch (IOException ex) {
            System.out.println("Erro ao salvar empréstimos: "
                    + ex.getMessage());
        }
        
    }
        
        public List<Emprestimo> carregar(Map<Integer, Livro> livros){
        	
        	List<Emprestimo> emprestimos = new ArrayList<>();
        	
        	try(BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
        		
        		String linha;
        		br.readLine();
        		
        		while ((linha = br.readLine()) != null) {
        			
        			String[] campos = linha.split(";");
        			
        			int LivroId = Integer.parseInt(campos[0]);
        			
        			String nomeAluno = campos[2];
        			
        			String serie = campos[3];
        			
        			String administrador = campos[4];
        			
        			boolean ativo = Boolean.parseBoolean(campos[6]);
        			
        			Livro livro = livros.get(LivroId);
        			if(livro == null) continue;
        			
        			Aluno aluno = new Aluno(nomeAluno, serie);
        			Emprestimo emp = new Emprestimo(aluno, livro, administrador);
        			
        			if(!ativo) {
        				emp.devolver();
        			} else {
        				livro.emprestar();
        			}
        			emprestimos.add(emp);
        			
        		}
        	
        } catch (Exception e) {
			System.out.println("Arquivo de empréstimos não encontrado.");
    }
        	
        	return emprestimos;
        }
}
        
