package server.command;

import model.Hospital;
import server.repository.HospitalRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ListHospitaisCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public ListHospitaisCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        List<Hospital> hospitais = hospitalRepository.buscarTodos();
        if (hospitais.isEmpty()) {
            return "0";
        }
        String dados = hospitais.stream()
            .map(h -> h.getNomeHospital() + ";" + h.getCapacidadeLeitos() + ";" + h.getEquipeMedica().size() + ";" + h.getAtendimentos().size())
            .collect(Collectors.joining("\n"));
        return String.format("%02d\n%s", hospitais.size(), dados);
    }
}