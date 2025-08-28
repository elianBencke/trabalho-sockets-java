package server.command;

import model.Hospital;
import model.Medico;
import model.Pessoa;
import server.repository.HospitalRepository;

import java.util.Map;
import java.util.Optional;

public class GetHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public GetHospitalCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        String nomeHospital = args[0];
        Optional<Hospital> hospitalOpt = hospitalRepository.buscarPorNome(nomeHospital);

        if (hospitalOpt.isEmpty()) {
            return "Hospital não encontrado.";
        }
        
        Hospital h = hospitalOpt.get();
        StringBuilder sb = new StringBuilder();
        sb.append(h.getNomeHospital()).append(h.getCapacidadeLeitos()).append("\n");
        sb.append("--- Equipe Médica --- (").append(h.getEquipeMedica().size()).append(")\n");
        for (Medico m : h.getEquipeMedica()) {
            sb.append("M: ").append(m.toString()).append("\n");
        }
        sb.append("--- Atendimentos --- (").append(h.getAtendimentos().size()).append(")\n");
        for (Map.Entry<Pessoa, Medico> entry : h.getAtendimentos().entrySet()) {
            sb.append("P: ").append(entry.getKey().toString()).append(" -> Dr(a). ").append(entry.getValue().getNome()).append("\n");
        }
        return sb.toString().trim();
    }
}