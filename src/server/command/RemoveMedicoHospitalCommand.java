package server.command;

import model.Hospital;
import model.Medico;
import model.Pessoa;
import server.repository.HospitalRepository;
import server.repository.PessoaRepository;
import java.util.Optional;

public class RemoveMedicoHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;
    private final PessoaRepository pessoaRepository;

    public RemoveMedicoHospitalCommand(HospitalRepository hospitalRepository, PessoaRepository pessoaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.pessoaRepository = pessoaRepository;
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
        
        Hospital hospital = hOpt.get();
        Medico medico = (Medico) pOpt.get();
        
        if (!hospital.getEquipeMedica().contains(medico)) return "ERRO: Médico não faz parte da equipe deste hospital.";
        if (hospital.getAtendimentos().containsValue(medico)) return "ERRO: Médico não pode ser removido pois é responsável por pacientes.";
        
        hospital.getEquipeMedica().remove(medico);
        hospitalRepository.salvar(hospital);
        
        return "Médico removido da equipe do hospital com sucesso.";
    }
}