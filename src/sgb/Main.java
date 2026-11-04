package sgb;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import sgb.excecao.RegraDeNegocioException;
import sgb.modelo.Emprestimo;
import sgb.modelo.Livro;
import sgb.modelo.Usuario;
import sgb.regras.CalculadoraMulta;
import sgb.regras.DisponibilidadeRegra;
import sgb.regras.LimiteEmprestimosRegra;
import sgb.regras.RegraEmprestimo;
import sgb.repositorio.EmprestimoRepositorio;
import sgb.repositorio.LivroRepositorio;
import sgb.repositorio.UsuarioRepositorio;
import sgb.repositorio.memoria.EmprestimoRepositorioMemoria;
import sgb.repositorio.memoria.LivroRepositorioMemoria;
import sgb.repositorio.memoria.UsuarioRepositorioMemoria;
import sgb.servico.EmprestimoServico;
import sgb.servico.LivroServico;
import sgb.servico.UsuarioServico;

public class Main {

    private static int falhas = 0;

    public static void main(String[] args) {
        // Composicao das dependencias
        LivroRepositorio livroRepo = new LivroRepositorioMemoria();
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorioMemoria();
        EmprestimoRepositorio emprestimoRepo = new EmprestimoRepositorioMemoria();
        List<RegraEmprestimo> regras = Arrays.<RegraEmprestimo>asList(
                new LimiteEmprestimosRegra(emprestimoRepo),
                new DisponibilidadeRegra());

        final LivroServico livroServico = new LivroServico(livroRepo);
        final UsuarioServico usuarioServico = new UsuarioServico(usuarioRepo);
        final EmprestimoServico emprestimoServico = new EmprestimoServico(
                emprestimoRepo, livroRepo, usuarioRepo, regras, new CalculadoraMulta());
        final LocalDate hoje = LocalDate.now();

        System.out.println("=== Cadastros (RF01, RF02, RN05) ===");
        final Livro dom = livroServico.cadastrar("Dom Casmurro", "Machado de Assis", "111", 2);
        final Livro memorias = livroServico.cadastrar("Memorias Postumas", "Machado de Assis", "222", 5);
        final Usuario maria = usuarioServico.cadastrar("Maria", "123", "maria@email.com");
        final Usuario joao = usuarioServico.cadastrar("Joao", "456", "joao@email.com");
        verificar("Dois livros cadastrados", livroServico.listar().size() == 2);
        verificar("Dois usuarios cadastrados", usuarioServico.listar().size() == 2);
        esperarExcecao("ISBN duplicado e bloqueado",
                () -> livroServico.cadastrar("Outro", "Autor", "111", 1));
        esperarExcecao("CPF duplicado e bloqueado",
                () -> usuarioServico.cadastrar("Outra", "123", "outra@email.com"));
        verificar("Busca de livro por titulo", livroServico.buscar("casmurro").size() == 1);
        verificar("Busca de usuario por CPF", usuarioServico.buscar("456").size() == 1);

        System.out.println("=== Emprestimos (RF03, RN01, RN02, RN03) ===");
        final Emprestimo e1 = emprestimoServico.registrarEmprestimo(maria.getId(), dom.getId());
        verificar("RN01: prazo de 7 dias",
                e1.getDataPrevistaDevolucao().equals(e1.getDataEmprestimo().plusDays(7)));
        verificar("Estoque do livro diminuiu", dom.getExemplaresDisponiveis() == 1);
        final Emprestimo e2 = emprestimoServico.registrarEmprestimo(joao.getId(), dom.getId());
        verificar("Estoque zerado", dom.getExemplaresDisponiveis() == 0);
        esperarExcecao("RN03: livro sem exemplares e bloqueado",
                () -> emprestimoServico.registrarEmprestimo(maria.getId(), dom.getId()));
        emprestimoServico.registrarEmprestimo(maria.getId(), memorias.getId());
        emprestimoServico.registrarEmprestimo(maria.getId(), memorias.getId());
        esperarExcecao("RN02: 4o emprestimo do mesmo usuario e bloqueado",
                () -> emprestimoServico.registrarEmprestimo(maria.getId(), memorias.getId()));

        System.out.println("=== Devolucoes (RF04, RN04) ===");
        emprestimoServico.registrarDevolucao(e1.getId(), hoje.plusDays(10));
        verificar("RN04: multa de R$ 3,00 para 3 dias de atraso", e1.getMulta() == 3.0);
        verificar("Estoque voltou apos a devolucao", dom.getExemplaresDisponiveis() == 1);
        emprestimoServico.registrarDevolucao(e2.getId(), hoje.plusDays(7));
        verificar("Devolucao no prazo nao gera multa", e2.getMulta() == 0.0);
        esperarExcecao("Devolucao repetida e bloqueada",
                () -> emprestimoServico.registrarDevolucao(e1.getId(), hoje));

        System.out.println("=== Consultas (RF05) ===");
        verificar("2 emprestimos ativos", emprestimoServico.listarAtivos().size() == 2);
        verificar("Nenhum em atraso hoje", emprestimoServico.listarEmAtraso(hoje).isEmpty());
        verificar("2 em atraso daqui a 10 dias",
                emprestimoServico.listarEmAtraso(hoje.plusDays(10)).size() == 2);

        System.out.println();
        if (falhas == 0) {
            System.out.println("RESULTADO: todos os testes passaram");
        } else {
            System.out.println("RESULTADO: " + falhas + " teste(s) falharam");
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            System.out.println("[OK]    " + descricao);
        } else {
            System.out.println("[FALHA] " + descricao);
            falhas++;
        }
    }

    private static void esperarExcecao(String descricao, Runnable acao) {
        try {
            acao.run();
            verificar(descricao, false);
        } catch (RegraDeNegocioException e) {
            verificar(descricao + " (" + e.getMessage() + ")", true);
        }
    }
}