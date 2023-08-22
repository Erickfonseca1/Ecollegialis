package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

// criar enum do tipo de voto
// COM_RELATOR, DIVERGENTE

enum TIPOVOTO {
    COM_RELATOR, DIVERGENTE
}


public class Voto {
    private int id;
    private TIPOVOTO tipoVoto;
    private Boolean ausente;

    public Voto(int id, TIPOVOTO tipoVoto, Boolean ausente) {
        this.id = id;
        this.tipoVoto = tipoVoto;
        this.ausente = ausente;
    }

    public int getId() {
        return id;
    }

    public TIPOVOTO getTipoVoto() {
        return tipoVoto;
    }

    public Boolean getAusente() {
        return ausente;
    }

    public void setTipoVoto(TIPOVOTO tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public void setAusente(Boolean ausente) {
        this.ausente = ausente;
    }
}
