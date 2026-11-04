package sgb.regras;

import sgb.excecao.RegraDeNegocioException;
import sgb.modelo.Livro;
import sgb.modelo.Usuario;

public class DisponibilidadeRegra implements RegraEmprestimo {

    @Override
    public void validar(Usuario usuario, Livro livro) {
        if (!livro.temExemplarDisponivel()) {
            throw new RegraDeNegocioException("Livro sem exemplares disponiveis");
        }
    }
}