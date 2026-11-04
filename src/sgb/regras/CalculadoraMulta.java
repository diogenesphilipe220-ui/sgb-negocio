package sgb.regras;

import java.time.LocalDate;
import sgb.modelo.Emprestimo;

public class CalculadoraMulta {

    private static final double VALOR_POR_DIA = 1.00;

    public double calcular(Emprestimo emprestimo, LocalDate dataDevolucao) {
        return emprestimo.diasDeAtraso(dataDevolucao) * VALOR_POR_DIA;
    }
}