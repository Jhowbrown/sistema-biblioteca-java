package salaleitura.model;

public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private int totalExemplares;
    private int exemplaresDisponiveis;

    public Livro(int id, String titulo, String autor, int totalExemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.totalExemplares = totalExemplares;
        this.exemplaresDisponiveis = totalExemplares;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getTotalExemplares() {
        return totalExemplares;
    }

    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    public boolean temDisponivel() {
        return exemplaresDisponiveis > 0;
    }

    public boolean emprestar() {
        if (temDisponivel()) {
            exemplaresDisponiveis--;
            return true;
        }
        return false;
    }

    public void devolver() {
        if (exemplaresDisponiveis < totalExemplares) {
            exemplaresDisponiveis++;
        }
    }

    @Override
    public String toString() {
        return "[ID: " + id + "] " + titulo + " - " + autor +
               " | Disponível: " + exemplaresDisponiveis + "/" + totalExemplares;
    }
}
