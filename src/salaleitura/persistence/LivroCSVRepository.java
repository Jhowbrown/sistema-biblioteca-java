package salaleitura.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import salaleitura.model.Livro;

public class LivroCSVRepository {
	
	private String caminhoArquivo;

	public LivroCSVRepository(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	 // 🔹 Carregar livros do CSV
	public List<Livro> carregar() {

	    List<Livro> livros = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

	        String linha;
	        br.readLine(); // pula cabeçalho

	        while ((linha = br.readLine()) != null) {

	            if (linha.trim().isEmpty()) continue;
	            
	            linha = linha.replace("\"", "").trim();

	            String[] campos = linha.split(";");

	            // Proteção mínima
	            if (campos.length < 5) {
	                System.out.println("Linha ignorada (formato inválido): " + linha);
	                continue;
	            }

	            try {
	                int id = Integer.parseInt(campos[0].trim());
	                String titulo = campos[1].trim();
	                String autor = campos[2].trim();

	                int total = Integer.parseInt(campos[3].trim());
	                int disponiveis = Integer.parseInt(campos[4].trim());

	                Livro livro = new Livro(id, titulo, autor, total);

	                // Ajusta exemplares disponíveis
	                while (livro.getExemplaresDisponiveis() > disponiveis) {
	                    livro.emprestar();
	                }

	                livros.add(livro);

	            } catch (NumberFormatException e) {
	                System.out.println("Linha ignorada (erro numérico): " + linha);
	            }
	        }

	    } catch (IOException e) {
	        System.out.println("Arquivo de livros não encontrado: " + caminhoArquivo);
	    }

	    return livros;
	}


	// 🔹 Salvar livros no CSV
	public void salvar(Collection<Livro> collection) {
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))){
			// cabeçalho
			bw.write("id;titulo;autor;totalExemplares;exemplaresDisponiveis");
			bw.newLine();
			
			for (Livro livro : collection) {
	            bw.write(
	                livro.getId() + ";" +
	                livro.getTitulo() + ";" +
	                livro.getAutor() + ";" +
	                livro.getTotalExemplares() + ";" +
	                livro.getExemplaresDisponiveis()
	            );
				
				bw.newLine();
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
