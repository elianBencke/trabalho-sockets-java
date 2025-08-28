package server.command;

import model.Paciente;
import model.Pessoa;
import server.repository.PessoaRepository;
import java.util.Optional;

public class UpdatePacienteCommand implements Command {
    private final PessoaRepository pessoaRepository;

    public UpdatePacienteCommand(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String execute(String[] args) {
        String cpf = args[0];
        Optional<Pessoa> pessoaOpt = pessoaRepository.buscarPorCpf(cpf);

        if (pessoaOpt.isEmpty()) {
            return "Pessoa não encontrada.";
        }

        Pessoa pessoa = pessoaOpt.get();
        if (!(pessoa instanceof Paciente)) {
            return "ERRO: O CPF informado não pertence a um paciente.";
        }

        Paciente paciente = (Paciente) pessoa;
        paciente.setNome(args[1]);
        paciente.setEndereco(args[2]);
        paciente.setEstadoCivil(args[3]);
        
        pessoaRepository.salvar(paciente);
        
        return "Pessoa atualizada com sucesso.";
    }
}