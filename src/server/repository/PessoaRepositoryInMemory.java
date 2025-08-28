package server.repository;

import model.Pessoa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PessoaRepositoryInMemory implements PessoaRepository {
    private final Map<String, Pessoa> bancoPessoas = new HashMap<>();

    @Override
    public void salvar(Pessoa pessoa) {
        bancoPessoas.put(pessoa.getCpf(), pessoa);
    }

    @Override
    public Optional<Pessoa> buscarPorCpf(String cpf) {
        return Optional.ofNullable(bancoPessoas.get(cpf));
    }

    @Override
    public List<Pessoa> buscarTodos() {
        return new ArrayList<>(bancoPessoas.values());
    }

    @Override
    public void remover(String cpf) {
        bancoPessoas.remove(cpf);
    }

    @Override
    public boolean existe(String cpf) {
        return bancoPessoas.containsKey(cpf);
    }
}