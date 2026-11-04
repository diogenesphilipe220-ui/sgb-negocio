package sgb.regras;

import sgb.excecao.RegraDeNegocioException;
import sgb.modelo.Livro;
import sgb.modelo.Usuario;
import sgb.repositorio.EmprestimoRepositorio;

public class LimiteEmprestimosRegra implements RegraEmprestimo {

    private static final int LIMITE_MAXIMO = 3;

    private final EmprestimoRepositorio emprestimos;

    public LimiteEmprestimosRegra(EmprestimoRepositorio emprestimos) {
        this.emprestimos = emprestimos;
    }

    @Override
    public void validar(Usuario usuario, Livro livro) {
        if (emprestimos.contarAtivosDoUsuario(usuario) >= LIMITE_MAXIMO) {
            throw new RegraDeNegocioException("Usuario atingiu o limite de emprestimos");
        }
    }
}