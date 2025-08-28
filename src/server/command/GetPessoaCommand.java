package server.command;

import model.Medico;
import model.Paciente;
import model.Pessoa;
import server.repository.PessoaRepository;
import java.util.Optional;

public class GetPessoaCommand implements Command {
    private final PessoaRepository pessoaRepository;
    private final String tipo;

    public GetPessoaCommand(PessoaRepository pessoaRepository, String tipo) {
        this.pessoaRepository = pessoaRepository;
        this.tipo = tipo;
    }

    @Override
    public String execute(String[] args) {
        String cpf = args[0];

        boolean tipoExiste = pessoaRepository.buscarTodos().stream()
                .anyMatch(p -> (tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico));
        
        if (!tipoExiste) {
            return "Sem pessoas cadastradas.";
        }

        Optional<Pessoa> pessoaOpt = pessoaRepository.buscarPorCpf(cpf);
        if (pessoaOpt.isEmpty()) {
            return "Pessoa não encontrada.";
        }

        Pessoa pessoa = pessoaOpt.get();
        boolean tipoCorreto = (tipo.equals("paciente") && pessoa instanceof Paciente) || (tipo.equals("medico") && pessoa instanceof Medico);

        if (tipoCorreto) {
            return pessoa.toString();
        } else {
            return "Pessoa não encontrada.";
        }
    }
}