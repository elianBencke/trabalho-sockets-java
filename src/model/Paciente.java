package model;

public class Paciente extends Pessoa {
    private String dataNascimento;
    private String estadoCivil;

    public Paciente(String cpf, String nome, String endereco, String dataNascimento, String estadoCivil) {
        super(cpf, nome, endereco);
        this.dataNascimento = dataNascimento;
        this.estadoCivil = estadoCivil;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + dataNascimento + ";" + estadoCivil;
    }
}