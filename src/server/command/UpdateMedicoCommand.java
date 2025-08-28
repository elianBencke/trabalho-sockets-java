package server.command;

import model.Medico;
import model.Pessoa;
import server.repository.PessoaRepository;
import java.util.Optional;

public class UpdateMedicoCommand implements Command {
    private final PessoaRepository pessoaRepository;

    public UpdateMedicoCommand(PessoaRepository pessoaRepository) {
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
        if (!(pessoa instanceof Medico)) {
            return "ERRO: O CPF informado não pertence a um médico.";
        }

        Medico medico = (Medico) pessoa;
        medico.setNome(args[1]);
        medico.setEndereco(args[2]);
        medico.setEspecializacao(args[3]);
        
        pessoaRepository.salvar(medico);
        
        return "Pessoa atualizada com sucesso.";
    }
}