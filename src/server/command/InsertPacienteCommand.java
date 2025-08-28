package server.command;

import model.Paciente;
import server.repository.PessoaRepository;

public class InsertPacienteCommand implements Command {
    private final PessoaRepository pessoaRepository;

    public InsertPacienteCommand(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String execute(String[] args) {
        String cpf = args[0];
        if (pessoaRepository.existe(cpf)) {
            return "ERRO: CPF já cadastrado no sistema.";
        }
        Paciente paciente = new Paciente(cpf, args[1], args[2], args[3], args[4]);
        pessoaRepository.salvar(paciente);
        return null;
    }
}