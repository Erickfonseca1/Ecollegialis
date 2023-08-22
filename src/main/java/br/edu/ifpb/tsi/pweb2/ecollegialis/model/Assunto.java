package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Assunto {
    private int id;
    private String nome;

    public Assunto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }
}
