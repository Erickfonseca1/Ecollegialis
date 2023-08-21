package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Usuario {
    private String nome;
    private String email;
    private String matricula;
    private Boolean admin;

    public Usuario(String nome, String email, String matricula, Boolean admin) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.admin = admin;
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricula() {
        return matricula;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        }
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

}
