package sgb.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {

    private final int id;
    private final Usuario usuario;
    private final Livro livro;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private double multa;

    public Emprestimo(int id, Usuario usuario, Livro livro,
                      LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Livro getLivro() { return livro; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public double getMulta() { return multa; }

    public boolean estaAtivo() {
        return dataDevolucao == null;
    }

    public long diasDeAtraso(LocalDate referencia) {
        long dias = ChronoUnit.DAYS.between(dataPrevistaDevolucao, referencia);
        return Math.max(dias, 0);
    }

    public boolean estaEmAtraso(LocalDate hoje) {
        return estaAtivo() && diasDeAtraso(hoje) > 0;
    }

    public void registrarDevolucao(LocalDate data, double multa) {
        this.dataDevolucao = data;
        this.multa = multa;
    }
}