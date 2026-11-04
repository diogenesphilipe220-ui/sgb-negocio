package sgb.regras;

import sgb.modelo.Livro;
import sgb.modelo.Usuario;

public interface RegraEmprestimo {

    void validar(Usuario usuario, Livro livro);
}