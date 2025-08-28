package client.view;

import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;

    public ClienteView() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Pacientes");
        System.out.println("2. Médicos");
        System.out.println("3. Hospitais");
        System.out.println("0. Sair do Sistema");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public int mostrarMenuPacientes() {
        System.out.println("\n--- PACIENTES ---");
        System.out.println("1. Inserir Paciente");
        System.out.println("2. Atualizar Paciente");
        System.out.println("3. Buscar Paciente");
        System.out.println("4. Deletar Paciente");
        System.out.println("5. Listar Pacientes");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public int mostrarMenuMedicos() {
        System.out.println("\n--- MÉDICOS ---");
        System.out.println("1. Inserir Médico");
        System.out.println("2. Atualizar Médico");
        System.out.println("3. Buscar Médico");
        System.out.println("4. Deletar Médico");
        System.out.println("5. Listar Médicos");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public int mostrarMenuHospitais() {
        System.out.println("\n--- HOSPITAIS ---");
        System.out.println("1. Inserir Hospital");
        System.out.println("2. Adicionar Médico à Equipe");
        System.out.println("3. Remover Médico da Equipe");
        System.out.println("4. Listar Médicos de um Hospital");
        System.out.println("5. Adicionar Atendimento");
        System.out.println("6. Remover Paciente do Hospital");
        System.out.println("7. Listar Pacientes de um Hospital");
        System.out.println("8. Atualizar Hospital");
        System.out.println("9. Buscar Hospital");
        System.out.println("10. Deletar Hospital");
        System.out.println("11. Listar Hospitais");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public String[] pedirDadosInserirPaciente() {
        System.out.println("--- Inserir Novo Paciente ---");
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Data de Nascimento (dd/mm/aaaa): "); String data = scanner.nextLine();
        System.out.print("Estado Civil: "); String estadoCivil = scanner.nextLine();
        return new String[]{cpf, nome, endereco, data, estadoCivil};
    }

    public String[] pedirDadosAtualizarPaciente() {
        System.out.println("--- Atualizar Paciente ---");
        System.out.print("CPF do paciente a ser atualizado: "); String cpf = scanner.nextLine();
        System.out.print("Novo Nome: "); String nome = scanner.nextLine();
        System.out.print("Novo Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Novo Estado Civil: "); String estadoCivil = scanner.nextLine();
        return new String[]{cpf, nome, endereco, estadoCivil};
    }

    public String[] pedirDadosInserirMedico() {
        System.out.println("--- Inserir Novo Médico ---");
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Especialização: "); String especializacao = scanner.nextLine();
        return new String[]{cpf, nome, endereco, especializacao};
    }
    
    public String[] pedirDadosAtualizarMedico() {
        System.out.println("--- Atualizar Médico ---");
        System.out.print("CPF do médico a ser atualizado: "); String cpf = scanner.nextLine();
        System.out.print("Novo Nome: "); String nome = scanner.nextLine();
        System.out.print("Novo Endereço: "); String endereco = scanner.nextLine();
        System.out.print("Nova Especialização: "); String especializacao = scanner.nextLine();
        return new String[]{cpf, nome, endereco, especializacao};
    }

    public String[] pedirDadosInserirHospital() {
        System.out.println("--- Inserir Novo Hospital ---");
        System.out.print("Nome do Hospital: "); String nome = scanner.nextLine();
        System.out.print("Capacidade de Leitos: "); String capacidade = scanner.nextLine();
        return new String[]{nome, capacidade};
    }

    public String[] pedirDadosAtualizarHospital() {
        System.out.println("--- Atualizar Hospital (Nome/Leitos) ---");
        System.out.print("Nome ATUAL do hospital: "); String nomeAntigo = scanner.nextLine();
        System.out.print("NOVO nome do hospital: "); String novoNome = scanner.nextLine();
        System.out.print("NOVA capacidade de leitos: "); String capacidade = scanner.nextLine();
        return new String[]{nomeAntigo, novoNome, capacidade};
    }

    public String[] pedirDadosAddMedicoHospital() {
        System.out.println("--- Adicionar Médico à Equipe ---");
        System.out.print("Nome do hospital: "); String nomeHospital = scanner.nextLine();
        System.out.print("CPF do médico a ser adicionado: "); String cpfMedico = scanner.nextLine();
        return new String[]{nomeHospital, cpfMedico};
    }
    
    public String[] pedirDadosRemoveMedicoHospital() {
        System.out.println("--- Remover Médico da Equipe ---");
        System.out.print("Nome do hospital: "); String nomeHospital = scanner.nextLine();
        System.out.print("CPF do médico a ser removido: "); String cpfMedico = scanner.nextLine();
        return new String[]{nomeHospital, cpfMedico};
    }

    public String[] pedirDadosAdicionarAtendimento() {
        System.out.println("--- Adicionar Atendimento (Internar Paciente) ---");
        System.out.print("Nome do hospital: "); String nomeHospital = scanner.nextLine();
        System.out.print("CPF do paciente a ser atendido: "); String cpfPaciente = scanner.nextLine();
        System.out.print("CPF do médico responsável: "); String cpfMedico = scanner.nextLine();
        return new String[]{nomeHospital, cpfPaciente, cpfMedico};
    }
    
    public String[] pedirDadosRemovePacienteHospital() {
        System.out.println("--- Remover Paciente do Hospital (Alta) ---");
        System.out.print("Nome do hospital: "); String nomeHospital = scanner.nextLine();
        System.out.print("CPF do paciente a receber alta: "); String cpfPaciente = scanner.nextLine();
        return new String[]{nomeHospital, cpfPaciente};
    }

    public String pedirCpf(String tipo) {
        System.out.println("--- Buscar/Deletar " + tipo + " ---");
        System.out.print("Digite o CPF: ");
        return scanner.nextLine();
    }

    public String pedirNomeHospital(String acao) {
        System.out.println("--- " + acao + " ---");
        System.out.print("Digite o nome do hospital: ");
        return scanner.nextLine();
    }

    public void mostrarResultado(String titulo, String resultado) {
        System.out.println("--- " + titulo + " ---");
        System.out.println(resultado);
    }
    
    public void mostrarMensagem(String mensagem) {
        System.out.println(">> " + mensagem);
    }
}