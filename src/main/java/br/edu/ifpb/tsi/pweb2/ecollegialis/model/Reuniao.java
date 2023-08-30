package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.sql.Date;
import java.util.ArrayList;

public class Reuniao {
    private int id;
    private Date dataReuniao;
    private StatusReuniao status;
    private String ata;
    private ArrayList<Processo> processos;

    public ArrayList<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(ArrayList<Processo> processos) {
        this.processos = processos;
    }

    public Reuniao(Date dataReuniao, String ata, ArrayList<Processo> processos) {
        this.dataReuniao = dataReuniao;
        this.ata = ata;
        this.processos = processos;
    }



    public int getId() {
        return this.id;
    }

    public Date getDataReuniao() {
        return this.dataReuniao;
    }

    public StatusReuniao getStatus() {
        return this.status;
    }

    public String getAta() {
        return this.ata;
    }

    public void setDataReuniao(Date dataReuniao) {
        this.dataReuniao = dataReuniao;
    }

    public void setStatus(StatusReuniao status) {
        this.status = status;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }
    
}

enum StatusReuniao {
    PROGRAMADA, FECHADA
}
