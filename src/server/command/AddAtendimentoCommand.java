package server.command;

import model.Hospital;
import model.Medico;
import model.Pessoa;
import server.repository.HospitalRepository;
import server.repository.PessoaRepository;
import java.util.Optional;

public class AddAtendimentoCommand implements Command {
    private final HospitalRepository hospitalRepository;
    private final PessoaRepository pessoaRepository;

    public AddAtendimentoCommand(HospitalRepository hospitalRepository, PessoaRepository pessoaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.pessoaRepository = pessoaRepository;
    }
    
    private boolean isPessoaEmAtendimento(Pessoa pessoa) {
        for (Hospital hospital : hospitalRepository.buscarTodos()) {
            if (hospital.getAtendimentos().containsKey(pessoa)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String execute(String[] args) {
        String nomeHospital = args[0];
        String cpfPaciente = args[1];
        String cpfMedico = args[2];

        if (cpfPaciente.equals(cpfMedico)) return "ERRO: Um médico não pode atender a si mesmo.";

        Optional<Hospital> hOpt = hospitalRepository.buscarPorNome(nomeHospital);
        if (hOpt.isEmpty()) return "Hospital não encontrado.";
        
        Optional<Pessoa> pOpt = pessoaRepository.buscarPorCpf(cpfPaciente);
        if (pOpt.isEmpty()) return "Pessoa (paciente) não encontrada.";
        
        if (isPessoaEmAtendimento(pOpt.get())) return "ERRO: Paciente já está em atendimento em um hospital.";

        Optional<Pessoa> mOpt = pessoaRepository.buscarPorCpf(cpfMedico);
        if (mOpt.isEmpty()) return "Médico não encontrado.";
        if (!(mOpt.get() instanceof Medico)) return "ERRO: O CPF do responsável deve ser de um médico.";
        
        Hospital hospital = hOpt.get();
        Pessoa paciente = pOpt.get();
        Medico medico = (Medico) mOpt.get();

        if (!hospital.getEquipeMedica().contains(medico)) return "ERRO: Médico não faz parte da equipe deste hospital.";
        if (hospital.getCapacidadeLeitos() <= 0) return "ERRO: Hospital sem leitos disponíveis.";

        hospital.adicionarAtendimento(paciente, medico);
        hospital.setCapacidadeLeitos(hospital.getCapacidadeLeitos() - 1);
        hospitalRepository.salvar(hospital);

        return "Atendimento registrado. Leitos restantes: " + hospital.getCapacidadeLeitos();
    }
}