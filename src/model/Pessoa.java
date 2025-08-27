package model;

// Superclasse abstrata, já que no sistema só existirão Médicos ou Pacientes.
public abstract class Pessoa {
    private String cpf;
    private String nome;
    private String endereco;

    public Pessoa(String cpf, String nome, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
    }

    // Getters e Setters
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return cpf + ";" + nome + ";" + endereco;
    }
}