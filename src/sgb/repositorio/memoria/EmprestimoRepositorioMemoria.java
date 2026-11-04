package sgb.repositorio.memoria;

import java.util.ArrayList;
import java.util.List;
import sgb.modelo.Emprestimo;
import sgb.modelo.Usuario;
import sgb.repositorio.EmprestimoRepositorio;

public class EmprestimoRepositorioMemoria implements EmprestimoRepositorio {

    private final List<Emprestimo> emprestimos = new ArrayList<>();

    @Override
    public void salvar(Emprestimo emprestimo) {
        if (!emprestimos.contains(emprestimo)) {
            emprestimos.add(emprestimo);
        }
    }

    @Override
    public Emprestimo buscarPorId(int id) {
        for (Emprestimo e : emprestimos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Emprestimo> listarAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.estaAtivo()) {
                ativos.add(e);
            }
        }
        return ativos;
    }

    @Override
    public int contarAtivosDoUsuario(Usuario usuario) {
        int total = 0;
        for (Emprestimo e : emprestimos) {
            if (e.estaAtivo() && e.getUsuario().getId() == usuario.getId()) {
                total++;
            }
        }
        return total;
    }

    @Override
    public int proximoId() {
        return emprestimos.size() + 1;
    }
}