package server.controller;

import server.command.*;
import server.repository.HospitalRepository;
import server.repository.HospitalRepositoryInMemory;
import server.repository.PessoaRepository;
import server.repository.PessoaRepositoryInMemory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    private final Map<String, Command> comandos = new HashMap<>();

    public Servidor() {
        PessoaRepository pessoaRepository = new PessoaRepositoryInMemory();
        HospitalRepository hospitalRepository = new HospitalRepositoryInMemory();
        
        comandos.put("INSERT_PACIENTE", new InsertPacienteCommand(pessoaRepository));
        comandos.put("UPDATE_PACIENTE", new UpdatePacienteCommand(pessoaRepository));
        comandos.put("GET_PACIENTE", new GetPessoaCommand(pessoaRepository, "paciente"));
        comandos.put("DELETE_PACIENTE", new DeletePessoaCommand(pessoaRepository, hospitalRepository, "paciente"));
        comandos.put("LIST_PACIENTE", new ListPessoasCommand(pessoaRepository, "paciente"));
        
        comandos.put("INSERT_MEDICO", new InsertMedicoCommand(pessoaRepository));
        comandos.put("UPDATE_MEDICO", new UpdateMedicoCommand(pessoaRepository));
        comandos.put("GET_MEDICO", new GetPessoaCommand(pessoaRepository, "medico"));
        comandos.put("DELETE_MEDICO", new DeletePessoaCommand(pessoaRepository, hospitalRepository, "medico"));
        comandos.put("LIST_MEDICO", new ListPessoasCommand(pessoaRepository, "medico"));

        comandos.put("INSERT_HOSPITAL", new InsertHospitalCommand(hospitalRepository));
        comandos.put("UPDATE_HOSPITAL", new UpdateHospitalCommand(hospitalRepository));
        comandos.put("GET_HOSPITAL", new GetHospitalCommand(hospitalRepository));
        comandos.put("DELETE_HOSPITAL", new DeleteHospitalCommand(hospitalRepository));
        comandos.put("LIST_HOSPITAL", new ListHospitaisCommand(hospitalRepository));

        comandos.put("ADD_ATENDIMENTO", new AddAtendimentoCommand(hospitalRepository, pessoaRepository));
        comandos.put("ADD_MEDICO_HOSPITAL", new AddMedicoHospitalCommand(hospitalRepository, pessoaRepository));
        comandos.put("REMOVE_MEDICO_HOSPITAL", new RemoveMedicoHospitalCommand(hospitalRepository, pessoaRepository));
        comandos.put("LIST_MEDICOS_HOSPITAL", new ListMedicosHospitalCommand(hospitalRepository));
        comandos.put("REMOVE_PACIENTE_HOSPITAL", new RemovePacienteHospitalCommand(hospitalRepository, pessoaRepository));
        comandos.put("LIST_PACIENTES_HOSPITAL", new ListPacientesHospitalCommand(hospitalRepository));
    }

    public String processarMensagem(String mensagem) {
        if (mensagem == null || mensagem.isEmpty()) {
            return "ERRO: Mensagem inválida ou vazia.";
        }
        System.out.println("<<< Mensagem recebida: " + mensagem);

        String[] partes = mensagem.split(";");
        String operacao = partes[0].toUpperCase();
        
        Command comando = comandos.get(operacao);
        if (comando == null) {
            return "ERRO: Operação desconhecida.";
        }
        
        try {
            String[] args = Arrays.copyOfRange(partes, 1, partes.length);
            return comando.execute(args);
        } catch (Exception e) {
            e.printStackTrace();
            return "ERRO: Ocorreu uma falha interna no servidor.";
        }
    }

    public void start(int porta) {
        try (ServerSocket servidorSocket = new ServerSocket(porta)) {
            System.out.println(">>> Servidor iniciado na porta " + porta + ". Aguardando conexões de clientes...");
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

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.start(80);
    }
}