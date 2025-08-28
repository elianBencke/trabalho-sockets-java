package server.command;

import model.Hospital;
import server.repository.HospitalRepository;
import java.util.Optional;

public class UpdateHospitalCommand implements Command {
    private final HospitalRepository hospitalRepository;

    public UpdateHospitalCommand(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public String execute(String[] args) {
        String nomeAntigo = args[0];
        String novoNome = args[1];
        int novaCapacidade = Integer.parseInt(args[2]);

        Optional<Hospital> hospitalOpt = hospitalRepository.buscarPorNome(nomeAntigo);
        if (hospitalOpt.isEmpty()) {
            return "Hospital não encontrado.";
        }
        if (!nomeAntigo.equals(novoNome) && hospitalRepository.existe(novoNome)) {
            return "ERRO: Já existe um hospital com o novo nome.";
        }
        
        Hospital hospital = hospitalOpt.get();
        hospital.setNomeHospital(novoNome);
        hospital.setCapacidadeLeitos(novaCapacidade);
        
        if (!nomeAntigo.equals(novoNome)) {
            hospitalRepository.remover(nomeAntigo);
        }
        hospitalRepository.salvar(hospital);

        return "Hospital atualizado com sucesso.";
    }
}