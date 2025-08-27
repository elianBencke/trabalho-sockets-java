package model;

public class Medico extends Pessoa {
    private String especializacao;

    public Medico(String cpf, String nome, String endereco, String especializacao) {
        super(cpf, nome, endereco);
        this.especializacao = especializacao;
    }

    // Getter e Setter
    public String getEspecializacao() { return especializacao; }
    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }

    @Override
    public String toString() {
        return super.toString() + ";" + especializacao;
    }
}