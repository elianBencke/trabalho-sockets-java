package server.command;

import model.Hospital;
import model.Medico;
import model.Pessoa;
import server.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListMedicosHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public ListMedicosHospitalCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        String nomeHospital = args[0];
        Optional<Hospital> hOpt = hospitalRepository.buscarPorNome(nomeHospital);
        if (hOpt.isEmpty()) return "Hospital não encontrado.";
        
        Hospital hospital = hOpt.get();
        List<Medico> equipe = hospital.getEquipeMedica();
        if (equipe.isEmpty()) return "Nenhum médico na equipe deste hospital.";
        
        String dados = equipe.stream().map(Pessoa::toString).collect(Collectors.joining("\n"));
        return "Equipe Médica do " + nomeHospital + ":\n" + dados;
    }
}