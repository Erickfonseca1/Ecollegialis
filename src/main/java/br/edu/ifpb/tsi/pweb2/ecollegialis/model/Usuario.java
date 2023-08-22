package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String matricula;
    private String phone;
    private Boolean admin;

    public Usuario(int id, String nome, String email, String matricula, String phone, Boolean admin) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.phone = phone;
        this.admin = admin;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

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

    public String getPhone() {
        return phone;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
