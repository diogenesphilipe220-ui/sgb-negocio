package sgb.repositorio;

import java.util.List;
import sgb.modelo.Emprestimo;
import sgb.modelo.Usuario;

public interface EmprestimoRepositorio {

    void salvar(Emprestimo emprestimo);

    Emprestimo buscarPorId(int id);

    List<Emprestimo> listarAtivos();

    int contarAtivosDoUsuario(Usuario usuario);

    int proximoId();
}