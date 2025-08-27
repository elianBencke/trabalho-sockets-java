package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String SERVER_ADDRESS = "";
    private static final int SERVER_PORT = 80;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- MENU PACIENTES ---");
            System.out.println("1. Inserir Paciente");
            System.out.println("2. Atualizar Paciente");
            System.out.println("3. Buscar Paciente");
            System.out.println("4. Deletar Paciente");
            System.out.println("5. Listar Pacientes");
            System.out.println("\n--- MENU MÉDICOS ---");
            System.out.println("6. Inserir Médico");
            System.out.println("7. Atualizar Médico");
            System.out.println("8. Buscar Médico");
            System.out.println("9. Deletar Médico");
            System.out.println("10. Listar Médicos");
            System.out.println("\n--- MENU HOSPITAIS ---");
            System.out.println("11. Inserir Hospital (com equipe médica)");
            System.out.println("12. Adicionar Atendimento (Paciente -> Médico)");
            System.out.println("13. Atualizar Hospital (Nome/Leitos)");
            System.out.println("14. Buscar Hospital");
            System.out.println("15. Deletar Hospital");
            System.out.println("16. Listar Hospitais");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: inserirPaciente(scanner); break;
                case 2: atualizarPaciente(scanner); break;
                case 3: buscarPaciente(scanner); break;
                case 4: deletarPaciente(scanner); break;
                case 5: listarPacientes(); break;
                case 6: inserirMedico(scanner); break;
                case 7: atualizarMedico(scanner); break;
                case 8: buscarMedico(scanner); break;
                case 9: deletarMedico(scanner); break;
                case 10: listarMedicos(); break;
                case 11: inserirHospital(scanner); break;
                case 12: adicionarAtendimento(scanner); break;
                case 13: atualizarHospital(scanner); break;
                case 14: buscarHospital(scanner); break;
                case 15: deletarHospital(scanner); break;
                case 16: listarHospitais(); break;
                case 0:
                    System.out.println("Encerrando cliente...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static String enviarMensagem(String mensagem) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(mensagem);

            String respostaDoServidor = in.readLine();
            
            if (respostaDoServidor != null) {
                return respostaDoServidor.replace("&NL&", System.lineSeparator());
            }
            return "";

        } catch (IOException e) {
            return "Erro de conexão com o servidor: " + e.getMessage();
        }
    }

    private static void inserirPaciente(Scanner scanner) {
        System.out.println("--- Inserir Novo Paciente ---");
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Data de Nascimento (dd/mm/aaaa): "); String data = scanner.nextLine();
        System.out.print("Estado Civil: "); String estadoCivil = scanner.nextLine();
        String mensagem = String.join(";", "INSERT_PACIENTE", cpf, nome, endereco, data, estadoCivil);
        enviarMensagem(mensagem);
        System.out.println(">> Paciente inserido (se dados válidos).");
    }

    private static void atualizarPaciente(Scanner scanner) {
        System.out.println("--- Atualizar Paciente ---");
        System.out.print("CPF do paciente a ser atualizado: "); String cpf = scanner.nextLine();
        System.out.print("Novo Nome: "); String nome = scanner.nextLine();
        System.out.print("Novo Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Novo Estado Civil: "); String estadoCivil = scanner.nextLine();
        String mensagem = String.join(";", "UPDATE_PACIENTE", cpf, nome, endereco, estadoCivil);
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }
    
    private static void buscarPaciente(Scanner scanner) {
        System.out.println("--- Buscar Paciente ---");
        System.out.print("CPF do paciente a buscar: "); String cpf = scanner.nextLine();
        String mensagem = "GET_PACIENTE;" + cpf;
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor:\n" + resposta);
    }

    private static void deletarPaciente(Scanner scanner) {
        System.out.println("--- Deletar Paciente ---");
        System.out.print("CPF do paciente a ser deletado: "); String cpf = scanner.nextLine();
        String mensagem = "DELETE_PACIENTE;" + cpf;
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }

    private static void listarPacientes() {
        System.out.println("--- Listando Todos os Pacientes ---");
        String resposta = enviarMensagem("LIST_PACIENTE");
        System.out.println(">> Resposta do servidor:\n" + resposta);
    }

    private static void inserirMedico(Scanner scanner) {
        System.out.println("--- Inserir Novo Médico ---");
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Especialização: "); String especializacao = scanner.nextLine();
        String mensagem = String.join(";", "INSERT_MEDICO", cpf, nome, endereco, especializacao);
        enviarMensagem(mensagem);
        System.out.println(">> Médico inserido (se dados válidos).");
    }

    private static void atualizarMedico(Scanner scanner) {
        System.out.println("--- Atualizar Médico ---");
        System.out.print("CPF do médico a ser atualizado: "); String cpf = scanner.nextLine();
        System.out.print("Novo Nome: "); String nome = scanner.nextLine();
        System.out.print("Novo Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Nova Especialização: "); String especializacao = scanner.nextLine();
        String mensagem = String.join(";", "UPDATE_MEDICO", cpf, nome, endereco, especializacao);
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }

    private static void buscarMedico(Scanner scanner) {
        System.out.println("--- Buscar Médico ---");
        System.out.print("CPF do médico a buscar: "); String cpf = scanner.nextLine();
        String mensagem = "GET_MEDICO;" + cpf;
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor:\n" + resposta);
    }

    private static void deletarMedico(Scanner scanner) {
        System.out.println("--- Deletar Médico ---");
        System.out.print("CPF do médico a ser deletado: "); String cpf = scanner.nextLine();
        String mensagem = "DELETE_MEDICO;" + cpf;
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }
    
    private static void listarMedicos() {
        System.out.println("--- Listando Todos os Médicos ---");
        String resposta = enviarMensagem("LIST_MEDICO");
        System.out.println(">> Resposta do servidor:\n" + resposta);
    }

    private static void inserirHospital(Scanner scanner) {
        System.out.println("--- Inserir Novo Hospital ---");
        System.out.print("Nome do Hospital: "); String nome = scanner.nextLine();
        System.out.print("Capacidade de Leitos: "); String capacidade = scanner.nextLine();
        System.out.print("CPFs dos médicos da equipe (opcional, separados por ';'): ");
        String cpfs = scanner.nextLine();
        String mensagem = "INSERT_HOSPITAL;" + nome + ";" + capacidade + ";" + cpfs;
        enviarMensagem(mensagem);
        System.out.println(">> Hospital inserido (se dados válidos).");
    }
    
    private static void adicionarAtendimento(Scanner scanner) {
        System.out.println("--- Adicionar Atendimento (Paciente -> Médico) ---");
        System.out.print("Nome do hospital: "); String nomeHospital = scanner.nextLine();
        System.out.print("CPF do paciente a ser atendido: "); String cpfPaciente = scanner.nextLine();
        System.out.print("CPF do médico responsável: "); String cpfMedico = scanner.nextLine();
        String mensagem = String.join(";", "ADD_ATENDIMENTO", nomeHospital, cpfPaciente, cpfMedico);
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }

    private static void atualizarHospital(Scanner scanner) {
        System.out.println("--- Atualizar Hospital (Nome/Leitos) ---");
        System.out.print("Nome ATUAL do hospital: "); String nomeAntigo = scanner.nextLine();
        System.out.print("NOVO nome do hospital: "); String novoNome = scanner.nextLine();
        System.out.print("NOVA capacidade de leitos: "); String capacidade = scanner.nextLine();
        String mensagem = String.join(";", "UPDATE_HOSPITAL", nomeAntigo, novoNome, capacidade);
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }
    
    private static void buscarHospital(Scanner scanner) {
        System.out.println("--- Buscar Hospital ---");
        System.out.print("Nome do hospital a buscar: "); String nome = scanner.nextLine();
        String mensagem = "GET_HOSPITAL;" + nome;
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor:\n" + resposta);
    }

    private static void deletarHospital(Scanner scanner) {
        System.out.println("--- Deletar Hospital ---");
        System.out.print("Nome do hospital a ser deletado: "); String nome = scanner.nextLine();
        String mensagem = "DELETE_HOSPITAL;" + nome;
        String resposta = enviarMensagem(mensagem);
        System.out.println(">> Resposta do servidor: " + resposta);
    }

    private static void listarHospitais() {
        System.out.println("--- Listando Todos os Hospitais ---");
        String resposta = enviarMensagem("LIST_HOSPITAL");
        System.out.println(">> Resposta do servidor:\n" + resposta);
    }
}