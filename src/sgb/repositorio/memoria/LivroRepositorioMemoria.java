package sgb.repositorio.memoria;

import java.util.ArrayList;
import java.util.List;
import sgb.modelo.Livro;
import sgb.repositorio.LivroRepositorio;

public class LivroRepositorioMemoria implements LivroRepositorio {

    private final List<Livro> livros = new ArrayList<>();

    @Override
    public void salvar(Livro livro) {
        if (!livros.contains(livro)) {
            livros.add(livro);
        }
    }

    @Override
    public Livro buscarPorId(int id) {
        for (Livro l : livros) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    @Override
    public Livro buscarPorIsbn(String isbn) {
        for (Livro l : livros) {
            if (l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }

    @Override
    public int proximoId() {
        return livros.size() + 1;
    }
}