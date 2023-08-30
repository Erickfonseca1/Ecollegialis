package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.ArrayList;

public class Processo {
    //por enquanto deixar o assunto livre em formato de string, mas
    //futuramente trocar por um enum com valores pre determinados (?)
    private Assunto assunto;
    private Professor relator;
    private Boolean decisaoDoRelator;
    private ArrayList<Voto> votos;

    public Boolean getDecisaoDoRelator() {
        return decisaoDoRelator;
    }

    public Boolean gerarResultado() {
        Integer votosComRelator = 0;
        Integer votosDivergentes = 0;
        for(Voto voto: votos){
            if(voto.getTipoVoto() == TIPOVOTO.COM_RELATOR){
                votosComRelator++;
            } else if(voto.getTipoVoto() == TIPOVOTO.DIVERGENTE){
                votosDivergentes++;
            }
        }
        return (votosComRelator > votosDivergentes) == decisaoDoRelator;
    }

    public Voto adicionarVoto(Voto voto){
        votos.add(voto);
        return voto;
    }

    public void setDecisaoDoRelator(Boolean decisaoDoRelator) {
        this.decisaoDoRelator = decisaoDoRelator;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public Processo(String assunto) {
        this.assunto = new Assunto(assunto);
    }
    
}
