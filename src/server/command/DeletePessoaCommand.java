package server.command;

import model.Hospital;
import model.Medico;
import model.Paciente;
import model.Pessoa;
import server.repository.HospitalRepository;
import server.repository.PessoaRepository;
import java.util.Optional;

public class DeletePessoaCommand implements Command {
    private final PessoaRepository pessoaRepository;
    private final HospitalRepository hospitalRepository;
    private final String tipo;

    public DeletePessoaCommand(PessoaRepository pessoaRepository, HospitalRepository hospitalRepository, String tipo) {
        this.pessoaRepository = pessoaRepository;
        this.hospitalRepository = hospitalRepository;
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

        if (!tipoCorreto) {
            return "Pessoa não encontrada.";
        }

        for (Hospital h : hospitalRepository.buscarTodos()) {
            if (h.getAtendimentos().containsKey(pessoa)) {
                h.getAtendimentos().remove(pessoa);
                h.setCapacidadeLeitos(h.getCapacidadeLeitos() + 1);
            }
            if (pessoa instanceof Medico) {
                h.getEquipeMedica().remove(pessoa);
                h.getAtendimentos().values().removeIf(medico -> medico.equals(pessoa));
            }
            hospitalRepository.salvar(h);
        }

        pessoaRepository.remover(cpf);
        return "Pessoa removida com sucesso.";
    }
}