package sgb.repositorio.memoria;

import java.util.ArrayList;
import java.util.List;
import sgb.modelo.Usuario;
import sgb.repositorio.UsuarioRepositorio;

public class UsuarioRepositorioMemoria implements UsuarioRepositorio {

    private final List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void salvar(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public int proximoId() {
        return usuarios.size() + 1;
    }
}