package sgb.repositorio;

import java.util.List;
import sgb.modelo.Usuario;

public interface UsuarioRepositorio {

    void salvar(Usuario usuario);

    Usuario buscarPorId(int id);

    Usuario buscarPorCpf(String cpf);

    List<Usuario> listarTodos();

    int proximoId();
}