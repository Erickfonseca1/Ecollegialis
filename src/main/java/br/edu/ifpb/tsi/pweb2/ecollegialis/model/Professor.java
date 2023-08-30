package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.sql.Date;
import java.util.ArrayList;

public class Professor extends Usuario{
    private Boolean coordenador;
    private Boolean relator;
    private ArrayList<Processo> processosDoCoordenador;
    private ArrayList<Processo> processosDoRelator;

    public ArrayList<Processo> getProcessosDoRelator() {
        return processosDoRelator;
    }

    public ArrayList<Processo> getProcessosDoCoordenador() {
        return processosDoCoordenador;
    }

    public Professor(int id, String nome, String email, String matricula, String phone , Boolean admin, Boolean relator) {
        super(id,nome, email, matricula, phone, admin);
        this.coordenador = false;
        this.relator = false;
        this.setAdmin(false);
        this.setRelator(relator);
    }

    public Boolean getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Boolean coordenador) {
        this.coordenador = coordenador;
    }

    public Boolean getRelator() {
        return relator;
    }

    public void julgarProcesso(Processo processo, Voto voto) {
        processo.adicionarVoto(voto);
    }

    public void julgamentoDoRelator(Processo processo, Boolean decisao) {
        processo.setDecisaoDoRelator(decisao);
    }

    public Reuniao marcarReuniao(Date data, String ata, ArrayList<Processo> processos){
        Reuniao reuniao = new Reuniao(data, ata, processos);
        return reuniao;
    }

    public void receberProcesso(Processo processo) throws Exception{
        if(this.coordenador == true){
            this.processosDoCoordenador.add(processo);
        } else if(this.relator == true){
            this.processosDoRelator.add(processo);
        } else {
            throw new Exception("O professor não é coordenador");
        }
    }

    public void setRelator(Boolean relator) {
        this.relator = relator;
    }

}
