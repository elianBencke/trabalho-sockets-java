package server.command;

import model.Hospital;
import model.Pessoa;
import server.repository.HospitalRepository;
import server.repository.PessoaRepository;
import java.util.Optional;

public class RemovePacienteHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;
    private final PessoaRepository pessoaRepository;

    public RemovePacienteHospitalCommand(HospitalRepository hospitalRepository, PessoaRepository pessoaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String execute(String[] args) {
        String nomeHospital = args[0];
        String cpfPaciente = args[1];
        Optional<Hospital> hOpt = hospitalRepository.buscarPorNome(nomeHospital);
        if (hOpt.isEmpty()) return "Hospital não encontrado.";
        Optional<Pessoa> pOpt = pessoaRepository.buscarPorCpf(cpfPaciente);
        if (pOpt.isEmpty()) return "Paciente não encontrado no sistema.";

        Hospital hospital = hOpt.get();
        Pessoa paciente = pOpt.get();

        if (!hospital.getAtendimentos().containsKey(paciente)) return "ERRO: Paciente não está em atendimento neste hospital.";
        
        hospital.getAtendimentos().remove(paciente);
        hospital.setCapacidadeLeitos(hospital.getCapacidadeLeitos() + 1);
        hospitalRepository.salvar(hospital);
        
        return "Paciente recebeu alta do hospital com sucesso. Leitos agora: " + hospital.getCapacidadeLeitos();
    }
}