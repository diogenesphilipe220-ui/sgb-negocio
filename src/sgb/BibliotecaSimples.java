package sgb;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BibliotecaSimples {

    // cada livro: {id, titulo, autor, isbn, exemplares}
    ArrayList<String[]> livros = new ArrayList<>();
    // cada usuario: {id, nome, cpf, email}
    ArrayList<String[]> usuarios = new ArrayList<>();

    public void cadastrarLivro(String titulo, String autor, String isbn, int exemplares) {
        for (String[] l : livros) {
            if (l[3].equals(isbn)) {
                System.out.println("ISBN ja cadastrado");
                return;
            }
        }
        String id = String.valueOf(livros.size() + 1);
        livros.add(new String[]{id, titulo, autor, isbn, String.valueOf(exemplares)});
        System.out.println("Livro cadastrado: " + titulo);
    }

    public void cadastrarUsuario(String nome, String cpf, String email) {
        for (String[] u : usuarios) {
            if (u[2].equals(cpf)) {
                System.out.println("CPF ja cadastrado");
                return;
            }
        }
        String id = String.valueOf(usuarios.size() + 1);
        usuarios.add(new String[]{id, nome, cpf, email});
        System.out.println("Usuario cadastrado: " + nome);
    }
        // cada emprestimo: {id, usuarioId, livroId, dataEmprestimo, dataPrevista, dataDevolucao, multa}
    ArrayList<String[]> emprestimos = new ArrayList<>();

    public void registrarEmprestimo(String usuarioId, String livroId) {
        String[] usuario = null;
        for (String[] u : usuarios) {
            if (u[0].equals(usuarioId)) {
                usuario = u;
            }
        }
        String[] livro = null;
        for (String[] l : livros) {
            if (l[0].equals(livroId)) {
                livro = l;
            }
        }
        if (usuario == null || livro == null) {
            System.out.println("Usuario ou livro nao encontrado");
            return;
        }
        int ativos = 0;
        for (String[] e : emprestimos) {
            if (e[1].equals(usuarioId) && e[5] == null) {
                ativos++;
            }
        }
        if (ativos >= 3) {
            System.out.println("Usuario atingiu o limite de emprestimos");
            return;
        }
        if (Integer.parseInt(livro[4]) <= 0) {
            System.out.println("Livro sem exemplares disponiveis");
            return;
        }
        livro[4] = String.valueOf(Integer.parseInt(livro[4]) - 1);
        String id = String.valueOf(emprestimos.size() + 1);
        LocalDate hoje = LocalDate.now();
        emprestimos.add(new String[]{id, usuarioId, livroId,
            hoje.toString(), hoje.plusDays(7).toString(), null, "0"});
        System.out.println("Emprestimo registrado: " + livro[1] + " para " + usuario[1]);
    }

    public void registrarDevolucao(String emprestimoId, LocalDate dataDevolucao) {
        String[] emprestimo = null;
        for (String[] e : emprestimos) {
            if (e[0].equals(emprestimoId)) {
                emprestimo = e;
            }
        }
        if (emprestimo == null || emprestimo[5] != null) {
            System.out.println("Emprestimo invalido ou ja devolvido");
            return;
        }
        long atraso = ChronoUnit.DAYS.between(LocalDate.parse(emprestimo[4]), dataDevolucao);
        double multa = 0;
        if (atraso > 0) {
            multa = atraso * 1.0;
        }
        emprestimo[5] = dataDevolucao.toString();
        emprestimo[6] = String.valueOf(multa);
        for (String[] l : livros) {
            if (l[0].equals(emprestimo[2])) {
                l[4] = String.valueOf(Integer.parseInt(l[4]) + 1);
            }
        }
        System.out.println("Devolucao registrada. Multa: R$ " + multa);
    }

    public void listarAtivos() {
        System.out.println("Emprestimos ativos:");
        for (String[] e : emprestimos) {
            if (e[5] == null) {
                System.out.println(e[0] + " - usuario " + e[1] + " - livro " + e[2]
                    + " - prevista ate " + e[4]);
            }
        }
    }
        public void listarEmAtraso(LocalDate hoje) {
        System.out.println("Emprestimos em atraso:");
        for (String[] e : emprestimos) {
            if (e[5] == null) {
                long atraso = ChronoUnit.DAYS.between(LocalDate.parse(e[4]), hoje);
                if (atraso > 0) {
                    System.out.println(e[0] + " - usuario " + e[1] + " - livro " + e[2]
                        + " - atraso de " + atraso + " dia(s)");
                }
            }
        }
    }
}