/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sgb;
import java.time.LocalDate;
/**
 *
 * @author dioge
 */
public class Main {

    public static void main(String[] args) {
     System.out.println("SGB iniciado");
     BibliotecaSimples b = new BibliotecaSimples();
b.cadastrarLivro("Dom Casmurro", "Machado de Assis", "111", 2);
b.cadastrarLivro("Dom Casmurro", "Machado de Assis", "111", 2);
b.cadastrarUsuario("Maria", "123", "maria@email.com");
b.cadastrarUsuario("Maria", "123", "maria@email.com");
b.registrarEmprestimo("1", "1");
b.registrarEmprestimo("1", "1");
b.registrarEmprestimo("1", "1");
b.listarAtivos();
b.registrarDevolucao("1", LocalDate.now().plusDays(10));
b.listarEmAtraso(LocalDate.now().plusDays(10));
    }
    
}
