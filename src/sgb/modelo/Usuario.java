package sgb.modelo;

public class Usuario {

    private final int id;
    private final String nome;
    private final String cpf;
    private final String email;

    public Usuario(int id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
}