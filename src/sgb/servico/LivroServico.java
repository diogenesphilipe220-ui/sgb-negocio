package sgb.servico;

import java.util.ArrayList;
import java.util.List;
import sgb.excecao.RegraDeNegocioException;
import sgb.modelo.Livro;
import sgb.repositorio.LivroRepositorio;

public class LivroServico {

    private final LivroRepositorio livros;

    public LivroServico(LivroRepositorio livros) {
        this.livros = livros;
    }

    public Livro cadastrar(String titulo, String autor, String isbn, int exemplares) {
        if (livros.buscarPorIsbn(isbn) != null) {
            throw new RegraDeNegocioException("ISBN ja cadastrado");
        }
        Livro livro = new Livro(livros.proximoId(), titulo, autor, isbn, exemplares);
        livros.salvar(livro);
        return livro;
    }

    public List<Livro> buscar(String termo) {
        String busca = termo.toLowerCase();
        List<Livro> encontrados = new ArrayList<>();
        for (Livro l : livros.listarTodos()) {
            if (l.getTitulo().toLowerCase().contains(busca)
                    || l.getAutor().toLowerCase().contains(busca)
                    || l.getIsbn().contains(busca)) {
                encontrados.add(l);
            }
        }
        return encontrados;
    }

    public List<Livro> listar() {
        return livros.listarTodos();
    }
}