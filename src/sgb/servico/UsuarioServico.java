package sgb.servico;

import java.util.ArrayList;
import java.util.List;
import sgb.excecao.RegraDeNegocioException;
import sgb.modelo.Usuario;
import sgb.repositorio.UsuarioRepositorio;

public class UsuarioServico {

    private final UsuarioRepositorio usuarios;

    public UsuarioServico(UsuarioRepositorio usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario cadastrar(String nome, String cpf, String email) {
        if (usuarios.buscarPorCpf(cpf) != null) {
            throw new RegraDeNegocioException("CPF ja cadastrado");
        }
        Usuario usuario = new Usuario(usuarios.proximoId(), nome, cpf, email);
        usuarios.salvar(usuario);
        return usuario;
    }

    public List<Usuario> buscar(String termo) {
        String busca = termo.toLowerCase();
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : usuarios.listarTodos()) {
            if (u.getNome().toLowerCase().contains(busca)
                    || u.getCpf().contains(busca)) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }

    public List<Usuario> listar() {
        return usuarios.listarTodos();
    }
}