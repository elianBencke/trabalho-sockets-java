package server.repository;

import model.Pessoa;
import java.util.List;
import java.util.Optional;

public interface PessoaRepository {
    void salvar(Pessoa pessoa);
    Optional<Pessoa> buscarPorCpf(String cpf);
    List<Pessoa> buscarTodos();
    void remover(String cpf);
    boolean existe(String cpf);
}