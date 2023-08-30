package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Assunto {
    private int id;
    private String nome;

    public Assunto(String nome) {
        //this.id = id;
        //auto gerado pelo banco futuramente
        this.nome = nome;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }
}
