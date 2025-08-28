package server.repository;

import model.Hospital;
import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    void salvar(Hospital hospital);
    Optional<Hospital> buscarPorNome(String nome);
    List<Hospital> buscarTodos();
    void remover(String nome);
    boolean existe(String nome);
}