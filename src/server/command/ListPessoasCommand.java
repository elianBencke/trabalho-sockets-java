package server.command;

import model.Medico;
import model.Paciente;
import model.Pessoa;
import server.repository.PessoaRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ListPessoasCommand implements Command {
    private final PessoaRepository pessoaRepository;
    private final String tipo;

    public ListPessoasCommand(PessoaRepository pessoaRepository, String tipo) {
        this.pessoaRepository = pessoaRepository;
        this.tipo = tipo;
    }

    @Override
    public String execute(String[] args) {
        List<Pessoa> pessoasDoTipo = pessoaRepository.buscarTodos().stream()
                .filter(p -> (tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico))
                .collect(Collectors.toList());

        if (pessoasDoTipo.isEmpty()) {
            return "0";
        }

        String dados = pessoasDoTipo.stream()
                .map(Pessoa::toString)
                .collect(Collectors.joining("\n"));

        return String.format("%02d\n%s", pessoasDoTipo.size(), dados);
    }
}