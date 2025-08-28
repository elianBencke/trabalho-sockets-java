package server.command;

import server.repository.HospitalRepository;

public class DeleteHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public DeleteHospitalCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        String nome = args[0];
        if (!hospitalRepository.existe(nome)) {
            return "Hospital não encontrado.";
        }
        hospitalRepository.remover(nome);
        return "Hospital removido com sucesso.";
    }
}