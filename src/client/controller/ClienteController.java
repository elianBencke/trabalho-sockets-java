package client.controller;

import client.view.ClienteView;
import network.RedeCliente;

public class ClienteController {
    private final ClienteView view;

    public ClienteController(ClienteView view) {
        this.view = view;
    }

    public void iniciar() {
        while (true) {
            int opcao = view.mostrarMenuPrincipal();
            switch (opcao) {
                case 1: gerenciarPacientes(); break;
                case 2: gerenciarMedicos(); break;
                case 3: gerenciarHospitais(); break;
                case 0:
                    view.mostrarMensagem("Encerrando cliente...");
                    return;
                default:
                    view.mostrarMensagem("Opção inválida.");
            }
        }
    }

    private void gerenciarPacientes() {
        while (true) {
            int opcao = view.mostrarMenuPacientes();
            String[] dados;
            String cpf;
            String mensagem;
            String resposta;
            switch (opcao) {q
                case 1:
                    dados = view.pedirDadosInserirPaciente();
                    mensagem = "INSERT_PACIENTE;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    if (resposta.isEmpty()) view.mostrarMensagem("Paciente inserido com sucesso.");
                    else view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 2:
                    dados = view.pedirDadosAtualizarPaciente();
                    mensagem = "UPDATE_PACIENTE;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 3:
                    cpf = view.pedirCpf("Paciente");
                    mensagem = "GET_PACIENTE;" + cpf;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Dados do Paciente", resposta);
                    break;
                case 4:
                    cpf = view.pedirCpf("Paciente");
                    mensagem = "DELETE_PACIENTE;" + cpf;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 5:
                    resposta = RedeCliente.enviarMensagem("LIST_PACIENTE");
                    view.mostrarResultado("Lista de Pacientes", resposta);
                    break;
                case 0: return;
                default: view.mostrarMensagem("Opção inválida.");
            }
        }
    }

    private void gerenciarMedicos() {
        while (true) {
            int opcao = view.mostrarMenuMedicos();
            String[] dados;
            String cpf;
            String mensagem;
            String resposta;
            switch (opcao) {
                case 1:
                    dados = view.pedirDadosInserirMedico();
                    mensagem = "INSERT_MEDICO;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    if (resposta.isEmpty()) view.mostrarMensagem("Médico inserido com sucesso.");
                    else view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 2:
                    dados = view.pedirDadosAtualizarMedico();
                    mensagem = "UPDATE_MEDICO;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 3:
                    cpf = view.pedirCpf("Médico");
                    mensagem = "GET_MEDICO;" + cpf;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Dados do Médico", resposta);
                    break;
                case 4:
                    cpf = view.pedirCpf("Médico");
                    mensagem = "DELETE_MEDICO;" + cpf;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 5:
                    resposta = RedeCliente.enviarMensagem("LIST_MEDICO");
                    view.mostrarResultado("Lista de Médicos", resposta);
                    break;
                case 0: return;
                default: view.mostrarMensagem("Opção inválida.");
            }
        }
    }

    private void gerenciarHospitais() {
        while (true) {
            int opcao = view.mostrarMenuHospitais();
            String[] dados;
            String nomeHospital;
            String mensagem;
            String resposta;
            switch (opcao) {
                case 1:
                    dados = view.pedirDadosInserirHospital();
                    mensagem = "INSERT_HOSPITAL;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    if (resposta.isEmpty()) view.mostrarMensagem("Hospital inserido com sucesso.");
                    else view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 2:
                    dados = view.pedirDadosAddMedicoHospital();
                    mensagem = "ADD_MEDICO_HOSPITAL;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 3:
                    dados = view.pedirDadosRemoveMedicoHospital();
                    mensagem = "REMOVE_MEDICO_HOSPITAL;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 4:
                    nomeHospital = view.pedirNomeHospital("Listar Médicos");
                    mensagem = "LIST_MEDICOS_HOSPITAL;" + nomeHospital;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 5:
                    dados = view.pedirDadosAdicionarAtendimento();
                    mensagem = "ADD_ATENDIMENTO;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 6:
                    dados = view.pedirDadosRemovePacienteHospital();
                    mensagem = "REMOVE_PACIENTE_HOSPITAL;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 7:
                    nomeHospital = view.pedirNomeHospital("Listar Pacientes");
                    mensagem = "LIST_PACIENTES_HOSPITAL;" + nomeHospital;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 8:
                    dados = view.pedirDadosAtualizarHospital();
                    mensagem = "UPDATE_HOSPITAL;" + String.join(";", dados);
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 9:
                    nomeHospital = view.pedirNomeHospital("Buscar Detalhes");
                    mensagem = "GET_HOSPITAL;" + nomeHospital;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Detalhes do Hospital", resposta);
                    break;
                case 10:
                    nomeHospital = view.pedirNomeHospital("Deletar");
                    mensagem = "DELETE_HOSPITAL;" + nomeHospital;
                    resposta = RedeCliente.enviarMensagem(mensagem);
                    view.mostrarResultado("Resposta do Servidor", resposta);
                    break;
                case 11:
                    resposta = RedeCliente.enviarMensagem("LIST_HOSPITAL");
                    view.mostrarResultado("Resumo de Hospitais", resposta);
                    break;
                case 0: return;
                default: view.mostrarMensagem("Opção inválida.");
            }
        }
    }

    public static void main(String[] args) {
        ClienteView view = new ClienteView();
        ClienteController controller = new ClienteController(view);
        controller.iniciar();
    }
}