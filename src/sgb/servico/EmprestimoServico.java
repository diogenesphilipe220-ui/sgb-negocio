package sgb.servico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sgb.excecao.RegraDeNegocioException;
import sgb.modelo.Emprestimo;
import sgb.modelo.Livro;
import sgb.modelo.Usuario;
import sgb.regras.CalculadoraMulta;
import sgb.regras.RegraEmprestimo;
import sgb.repositorio.EmprestimoRepositorio;
import sgb.repositorio.LivroRepositorio;
import sgb.repositorio.UsuarioRepositorio;

public class EmprestimoServico {

    private static final int PRAZO_EM_DIAS = 7;

    private final EmprestimoRepositorio emprestimos;
    private final LivroRepositorio livros;
    private final UsuarioRepositorio usuarios;
    private final List<RegraEmprestimo> regras;
    private final CalculadoraMulta calculadoraMulta;

    public EmprestimoServico(EmprestimoRepositorio emprestimos,
                             LivroRepositorio livros,
                             UsuarioRepositorio usuarios,
                             List<RegraEmprestimo> regras,
                             CalculadoraMulta calculadoraMulta) {
        this.emprestimos = emprestimos;
        this.livros = livros;
        this.usuarios = usuarios;
        this.regras = regras;
        this.calculadoraMulta = calculadoraMulta;
    }

    public Emprestimo registrarEmprestimo(int usuarioId, int livroId) {
        Usuario usuario = usuarios.buscarPorId(usuarioId);
        if (usuario == null) {
            throw new RegraDeNegocioException("Usuario nao encontrado");
        }
        Livro livro = livros.buscarPorId(livroId);
        if (livro == null) {
            throw new RegraDeNegocioException("Livro nao encontrado");
        }
        for (RegraEmprestimo regra : regras) {
            regra.validar(usuario, livro);
        }
        livro.retirarExemplar();
        LocalDate hoje = LocalDate.now();
        Emprestimo emprestimo = new Emprestimo(emprestimos.proximoId(), usuario, livro,
                hoje, hoje.plusDays(PRAZO_EM_DIAS));
        emprestimos.salvar(emprestimo);
        return emprestimo;
    }

    public Emprestimo registrarDevolucao(int emprestimoId, LocalDate dataDevolucao) {
        Emprestimo emprestimo = emprestimos.buscarPorId(emprestimoId);
        if (emprestimo == null || !emprestimo.estaAtivo()) {
            throw new RegraDeNegocioException("Emprestimo invalido ou ja devolvido");
        }
        double multa = calculadoraMulta.calcular(emprestimo, dataDevolucao);
        emprestimo.registrarDevolucao(dataDevolucao, multa);
        emprestimo.getLivro().devolverExemplar();
        return emprestimo;
    }

    public List<Emprestimo> listarAtivos() {
        return emprestimos.listarAtivos();
    }

    public List<Emprestimo> listarEmAtraso(LocalDate hoje) {
        List<Emprestimo> emAtraso = new ArrayList<>();
        for (Emprestimo e : emprestimos.listarAtivos()) {
            if (e.estaEmAtraso(hoje)) {
                emAtraso.add(e);
            }
        }
        return emAtraso;
    }
}