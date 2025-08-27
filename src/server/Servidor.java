package server;

import model.Hospital;
import model.Medico;
import model.Paciente;
import model.Pessoa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Servidor {

    private static final Map<String, Pessoa> bancoPessoas = new HashMap<>();
    private static final Map<String, Hospital> bancoHospitais = new HashMap<>();

    public static void main(String[] args) {
        final int PORTA = 80;

        try (ServerSocket servidorSocket = new ServerSocket(PORTA)) {
            System.out.println(">>> Servidor iniciado na porta " + PORTA + ". Aguardando conexões de clientes...");

            while (true) {
                try (Socket clienteSocket = servidorSocket.accept()) {
                    System.out.println(">>> Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
                    String mensagem = in.readLine();
                    String resposta = processarMensagem(mensagem);
                    if (resposta != null) {
                        out.print(resposta.replace("\n", "&NL&") + "\n");
                        out.flush();
                    }
                } catch (IOException e) {
                    System.err.println("!!! Erro na comunicação com o cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("!!! Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static String processarMensagem(String mensagem) {
        if (mensagem == null || mensagem.isEmpty()) {
            return "ERRO: Mensagem inválida ou vazia.";
        }
        System.out.println("<<< Mensagem recebida: " + mensagem);
        String[] partes = mensagem.split(";");
        String operacao = partes[0].toUpperCase();
        try {
            switch (operacao) {
                case "INSERT_PACIENTE":
                    bancoPessoas.put(partes[1], new Paciente(partes[1], partes[2], partes[3], partes[4], partes[5]));
                    return null;
                case "UPDATE_PACIENTE":
                    return atualizarPessoa(partes, "paciente");
                case "GET_PACIENTE":
                    return getPessoa(partes[1], "paciente");
                case "DELETE_PACIENTE":
                    return deletePessoa(partes[1], "paciente");
                case "LIST_PACIENTE":
                    return listarPessoas("paciente");
                case "INSERT_MEDICO":
                    bancoPessoas.put(partes[1], new Medico(partes[1], partes[2], partes[3], partes[4]));
                    return null;
                case "UPDATE_MEDICO":
                    return atualizarPessoa(partes, "medico");
                case "GET_MEDICO":
                    return getPessoa(partes[1], "medico");
                case "DELETE_MEDICO":
                    return deletePessoa(partes[1], "medico");
                case "LIST_MEDICO":
                    return listarPessoas("medico");
                case "INSERT_HOSPITAL":
                    return inserirHospital(partes);
                case "UPDATE_HOSPITAL":
                    return atualizarHospital(partes);
                case "GET_HOSPITAL":
                    return getHospital(partes[1]);
                case "DELETE_HOSPITAL":
                    return deleteHospital(partes[1]);
                case "LIST_HOSPITAL":
                    return listarHospitais();
                case "ADD_ATENDIMENTO":
                    return adicionarAtendimento(partes);
                default:
                    return "ERRO: Operação desconhecida.";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return "ERRO: Número de parâmetros incorreto para a operação " + operacao + ".";
        } catch (NumberFormatException e) {
            return "ERRO: Parâmetro numérico inválido para a operação " + operacao + ".";
        }
    }

    private static boolean isPessoaVinculada(Pessoa pessoa) {
        for (Hospital hospital : bancoHospitais.values()) {
            if (hospital.getEquipeMedica().contains(pessoa) || hospital.getAtendimentos().containsKey(pessoa)) {
                return true;
            }
        }
        return false;
    }

    private static String inserirHospital(String[] partes) {
        String nomeHospital = partes[1];
        if (bancoHospitais.containsKey(nomeHospital)) return "ERRO: Hospital com este nome já existe.";
        int capacidade = Integer.parseInt(partes[2]);
        Hospital h = new Hospital(nomeHospital, capacidade);

        for (int i = 3; i < partes.length; i++) {
            Pessoa p = bancoPessoas.get(partes[i]);
            if (p instanceof Medico) {
                if (isPessoaVinculada(p)) {
                    return "ERRO: O médico com CPF " + p.getCpf() + " já está vinculado a outro hospital.";
                }
                h.adicionarMedico((Medico) p);
            }
        }
        bancoHospitais.put(nomeHospital, h);
        return null;
    }

    private static String adicionarAtendimento(String[] partes) {
        String nomeHospital = partes[1];
        String cpfPaciente = partes[2];
        String cpfMedico = partes[3];

        if (cpfPaciente.equals(cpfMedico)) {
            return "ERRO: Um médico não pode atender a si mesmo.";
        }

        Hospital h = bancoHospitais.get(nomeHospital);
        if (h == null) return "Hospital não encontrado.";
        
        Pessoa p = bancoPessoas.get(cpfPaciente);
        if (p == null) return "Pessoa (paciente) não encontrada.";

        if (isPessoaVinculada(p)) {
            return "ERRO: Esta pessoa já está em atendimento ou vinculada a outro hospital.";
        }

        Pessoa m = bancoPessoas.get(cpfMedico);
        if (m == null) return "Médico não encontrado.";
        if (!(m instanceof Medico)) return "ERRO: O CPF do responsável deve ser de um médico.";
        
        Medico medicoResponsavel = (Medico) m;
        
        if (!h.getEquipeMedica().contains(medicoResponsavel)) {
            return "ERRO: Médico não faz parte da equipe deste hospital.";
        }
        
        if (h.getCapacidadeLeitos() <= 0) {
            return "ERRO: Hospital sem leitos disponíveis.";
        }
        
        h.adicionarAtendimento(p, medicoResponsavel);
        h.setCapacidadeLeitos(h.getCapacidadeLeitos() - 1);

        return "Atendimento registrado. Paciente vinculado ao médico. Leitos restantes: " + h.getCapacidadeLeitos();
    }

    private static String atualizarPessoa(String[] partes, String tipo) {
        Pessoa p = bancoPessoas.get(partes[1]);
        if (p == null) return "Pessoa não encontrada.";
        if (tipo.equals("paciente") && p instanceof Paciente) {
            p.setNome(partes[2]);
            p.setEndereco(partes[3]);
            ((Paciente) p).setEstadoCivil(partes[4]);
            return "Pessoa atualizada com sucesso.";
        } else if (tipo.equals("medico") && p instanceof Medico) {
            p.setNome(partes[2]);
            p.setEndereco(partes[3]);
            ((Medico) p).setEspecializacao(partes[4]);
            return "Pessoa atualizada com sucesso.";
        }
        return "ERRO: Tipo de pessoa inconsistente.";
    }

    private static String getPessoa(String cpf, String tipo) {
        boolean tipoExiste = bancoPessoas.values().stream().anyMatch(p -> (tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico));
        if (!tipoExiste) return "Sem pessoas cadastradas.";
        Pessoa p = bancoPessoas.get(cpf);
        if (p != null) {
            if ((tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico)) {
                return p.toString();
            }
        }
        return "Pessoa não encontrada.";
    }

    private static String deletePessoa(String cpf, String tipo) {
        boolean tipoExiste = bancoPessoas.values().stream().anyMatch(p -> (tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico));
        if (!tipoExiste) return "Sem pessoas cadastradas.";
        Pessoa p = bancoPessoas.get(cpf);
        if (p != null) {
            if ((tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico)) {
                bancoPessoas.remove(cpf);
                for (Hospital h : bancoHospitais.values()) {
                    if (h.getAtendimentos().containsKey(p)) {
                        h.getAtendimentos().remove(p);
                        h.setCapacidadeLeitos(h.getCapacidadeLeitos() + 1);
                    }
                    if (p instanceof Medico) {
                        h.getEquipeMedica().remove(p);
                        h.getAtendimentos().values().removeIf(medico -> medico.equals(p));
                    }
                }
                return "Pessoa removida com sucesso.";
            }
        }
        return "Pessoa não encontrada.";
    }

    private static String listarPessoas(String tipo) {
        long count = bancoPessoas.values().stream().filter(p -> (tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico)).count();
        if (count == 0) return "0";
        String dados = bancoPessoas.values().stream().filter(p -> (tipo.equals("paciente") && p instanceof Paciente) || (tipo.equals("medico") && p instanceof Medico)).map(Pessoa::toString).collect(Collectors.joining("\n"));
        return String.format("%02d\n%s", count, dados);
    }
    
    private static String atualizarHospital(String[] partes) {
        String nomeAntigo = partes[1];
        String novoNome = partes[2];
        int novaCapacidade = Integer.parseInt(partes[3]);
        Hospital h = bancoHospitais.get(nomeAntigo);
        if (h == null) return "Hospital não encontrado.";
        if (!nomeAntigo.equals(novoNome) && bancoHospitais.containsKey(novoNome)) return "ERRO: Já existe um hospital com o novo nome.";
        h.setNomeHospital(novoNome);
        h.setCapacidadeLeitos(novaCapacidade);
        if (!nomeAntigo.equals(novoNome)) {
            bancoHospitais.remove(nomeAntigo);
            bancoHospitais.put(novoNome, h);
        }
        return "Hospital atualizado com sucesso.";
    }

    private static String getHospital(String nomeHospital) {
        if (bancoHospitais.isEmpty()) return "Sem hospitais cadastrados.";
        Hospital h = bancoHospitais.get(nomeHospital);
        if (h == null) return "Hospital não encontrado.";
        StringBuilder sb = new StringBuilder();
        sb.append(h.getNomeHospital()).append("; Leitos Disponíveis: ").append(h.getCapacidadeLeitos()).append("\n");
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

    private static String deleteHospital(String nomeHospital) {
        if (bancoHospitais.isEmpty()) return "Sem hospitais cadastrados.";
        if (bancoHospitais.remove(nomeHospital) != null) return "Hospital removido com sucesso.";
        return "Hospital não encontrado.";
    }

    private static String listarHospitais() {
        if (bancoHospitais.isEmpty()) return "0";
        long count = bancoHospitais.size();
        String dados = bancoHospitais.values().stream().map(h -> h.getNomeHospital() + ";" + h.getCapacidadeLeitos() + ";" + h.getEquipeMedica().size() + ";" + h.getAtendimentos().size()).collect(Collectors.joining("\n"));
        return String.format("%02d\n%s", count, dados);
    }
}