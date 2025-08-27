package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital {

    private String nomeHospital;
    private int capacidadeLeitos;
    private List<Medico> equipeMedica;
    private Map<Pessoa, Medico> atendimentos;

    public Hospital(String nomeHospital, int capacidadeLeitos) {
        this.nomeHospital = nomeHospital;
        this.capacidadeLeitos = capacidadeLeitos;
        this.equipeMedica = new ArrayList<>();
        this.atendimentos = new HashMap<>();
    }

    public String getNomeHospital() {
        return nomeHospital;
    }

    public void setNomeHospital(String nomeHospital) {
        this.nomeHospital = nomeHospital;
    }

    public int getCapacidadeLeitos() {
        return capacidadeLeitos;
    }

    public void setCapacidadeLeitos(int capacidadeLeitos) {
        this.capacidadeLeitos = capacidadeLeitos;
    }

    public List<Medico> getEquipeMedica() {
        return equipeMedica;
    }

    public void adicionarMedico(Medico medico) {
        if (!this.equipeMedica.contains(medico)) {
            this.equipeMedica.add(medico);
        }
    }

    public Map<Pessoa, Medico> getAtendimentos() {
        return atendimentos;
    }

    public void adicionarAtendimento(Pessoa paciente, Medico medico) {
        this.atendimentos.put(paciente, medico);
    }
}