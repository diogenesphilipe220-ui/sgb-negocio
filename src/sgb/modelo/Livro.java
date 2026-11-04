package sgb.modelo;

public class Livro {

    private final int id;
    private final String titulo;
    private final String autor;
    private final String isbn;
    private int exemplaresDisponiveis;

    public Livro(int id, String titulo, String autor, String isbn, int exemplaresDisponiveis) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.exemplaresDisponiveis = exemplaresDisponiveis;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public int getExemplaresDisponiveis() { return exemplaresDisponiveis; }

    public boolean temExemplarDisponivel() {
        return exemplaresDisponiveis > 0;
    }

    public void retirarExemplar() {
        if (!temExemplarDisponivel()) {
            throw new IllegalStateException("Livro sem exemplares disponiveis");
        }
        exemplaresDisponiveis--;
    }

    public void devolverExemplar() {
        exemplaresDisponiveis++;
    }
}