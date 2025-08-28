package server.repository;

import model.Hospital;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HospitalRepositoryInMemory implements HospitalRepository {
    private final Map<String, Hospital> bancoHospitais = new HashMap<>();

    @Override
    public void salvar(Hospital hospital) {
        bancoHospitais.put(hospital.getNomeHospital(), hospital);
    }

    @Override
    public Optional<Hospital> buscarPorNome(String nome) {
        return Optional.ofNullable(bancoHospitais.get(nome));
    }

    @Override
    public List<Hospital> buscarTodos() {
        return new ArrayList<>(bancoHospitais.values());
    }

    @Override
    public void remover(String nome) {
        bancoHospitais.remove(nome);
    }
    
    @Override
    public boolean existe(String nome) {
        return bancoHospitais.containsKey(nome);
    }
}