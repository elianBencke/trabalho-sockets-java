package server.command;

import model.Medico;
import server.repository.PessoaRepository;

public class InsertMedicoCommand implements Command {
    private final PessoaRepository pessoaRepository;

    public InsertMedicoCommand(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String execute(String[] args) {
        String cpf = args[0];
        if (pessoaRepository.existe(cpf)) {
            return "ERRO: CPF já cadastrado no sistema.";
        }
        Medico medico = new Medico(cpf, args[1], args[2], args[3]);
        pessoaRepository.salvar(medico);
        return null;
    }
}