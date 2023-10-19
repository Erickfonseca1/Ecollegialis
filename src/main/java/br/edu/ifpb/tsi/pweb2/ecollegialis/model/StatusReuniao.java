package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public enum StatusReuniao {
    ENCERRADA("Encerrada"), PROGRAMADA("Programada"), EM_ANDAMENTO("Em Andamento");

    private String status;

    StatusReuniao(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
