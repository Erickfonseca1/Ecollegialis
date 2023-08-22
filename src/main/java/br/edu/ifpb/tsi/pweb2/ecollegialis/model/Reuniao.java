package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.sql.Date;

public class Reuniao {
    private int id;
    private Date dataReuniao;
    private StatusReuniao status;
    private String ata;

    public Reuniao(int id, Date dataReuniao, StatusReuniao status, String ata) {
        this.id = id;
        this.dataReuniao = dataReuniao;
        this.status = status;
        this.ata = ata;
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
