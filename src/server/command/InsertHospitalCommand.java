package server.command;

import model.Hospital;
import server.repository.HospitalRepository;

public class InsertHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public InsertHospitalCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        String nome = args[0];
        if (hospitalRepository.existe(nome)) {
            return "ERRO: Hospital com este nome já existe.";
        }
        int capacidade = Integer.parseInt(args[1]);
        Hospital hospital = new Hospital(nome, capacidade);
        hospitalRepository.salvar(hospital);
        return null;
    }
}