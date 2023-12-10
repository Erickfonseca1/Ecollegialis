package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public abstract class Usuario {
    private Integer id;
    private String nome;
    private String fone;
    private String matricula;
    private String login;
    private String senha;

    public Usuario(String nome, String fone, String matricula, String login, String senha) {
        this.nome = nome;
        this.fone = fone;
        this.matricula = matricula;
        this.login = login;
        this.senha = senha;
    }
}
