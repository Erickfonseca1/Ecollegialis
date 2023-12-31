package br.edu.ifpb.tsi.pweb2.ecollegialis.enums;

public enum StatusEnum {
    CRIADO("Criado"), DISTRIBUIDO("Distribuido"), EM_PAUTA("Em pauta"), EM_JULGAMENTO("Em julgamento"), JULGADO("Julgado");

    private String status;

    StatusEnum(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public String setStatus(String status){
        return this.status = status;
    }
}
