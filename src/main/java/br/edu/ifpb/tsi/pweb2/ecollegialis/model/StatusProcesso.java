package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public enum StatusProcesso {
    CRIADO("Criado"), DISTRIBUIDO("Distribuido"), EM_PAUTA("Em pauta"), EM_JULGAMENTO("Em julgamento"), JULGADO("Julgado");

    private String status;

    StatusProcesso(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
