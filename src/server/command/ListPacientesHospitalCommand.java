package server.command;

import model.Hospital;
import model.Medico;
import model.Pessoa;
import server.repository.HospitalRepository;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListPacientesHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public ListPacientesHospitalCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        String nomeHospital = args[0];
        Optional<Hospital> hOpt = hospitalRepository.buscarPorNome(nomeHospital);
        if (hOpt.isEmpty()) return "Hospital não encontrado.";

        Hospital hospital = hOpt.get();
        Map<Pessoa, Medico> atendimentos = hospital.getAtendimentos();
        if (atendimentos.isEmpty()) return "Nenhum paciente em atendimento neste hospital.";
        
        String dados = atendimentos.entrySet().stream()
            .map(entry -> "P: " + entry.getKey().toString() + " -> Dr(a). " + entry.getValue().getNome())
            .collect(Collectors.joining("\n"));
        
        return "Pacientes do " + nomeHospital + ":\n" + dados;
    }
}