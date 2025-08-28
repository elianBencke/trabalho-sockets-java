package server.command;

import model.Hospital;
import model.Medico;
import model.Pessoa;
import server.repository.HospitalRepository;
import server.repository.PessoaRepository;
import java.util.Optional;

public class AddMedicoHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;
    private final PessoaRepository pessoaRepository;

    public AddMedicoHospitalCommand(HospitalRepository hospitalRepository, PessoaRepository pessoaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.pessoaRepository = pessoaRepository;
    }

    private boolean isPessoaVinculada(Pessoa pessoa) {
        for (Hospital hospital : hospitalRepository.buscarTodos()) {
            if (hospital.getEquipeMedica().contains(pessoa) || hospital.getAtendimentos().containsKey(pessoa)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String execute(String[] args) {
        String nomeHospital = args[0];
        String cpfMedico = args[1];
        Optional<Hospital> hOpt = hospitalRepository.buscarPorNome(nomeHospital);
        if (hOpt.isEmpty()) return "Hospital não encontrado.";
        Optional<Pessoa> pOpt = pessoaRepository.buscarPorCpf(cpfMedico);
        if (pOpt.isEmpty()) return "Médico não encontrado no sistema.";
        if (!(pOpt.get() instanceof Medico)) return "ERRO: O CPF informado não pertence a um médico.";
        if (isPessoaVinculada(pOpt.get())) return "ERRO: Médico já está vinculado a um hospital.";
        
        Hospital hospital = hOpt.get();
        hospital.adicionarMedico((Medico) pOpt.get());
        hospitalRepository.salvar(hospital);
        
        return "Médico adicionado à equipe do hospital com sucesso.";
    }
}