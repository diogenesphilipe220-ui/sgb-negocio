package sgb.repositorio;

import java.util.List;
import sgb.modelo.Livro;

public interface LivroRepositorio {

    void salvar(Livro livro);

    Livro buscarPorId(int id);

    Livro buscarPorIsbn(String isbn);

    List<Livro> listarTodos();

    int proximoId();
}