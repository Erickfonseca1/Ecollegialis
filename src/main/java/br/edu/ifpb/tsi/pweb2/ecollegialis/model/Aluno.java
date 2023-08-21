package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Aluno extends Usuario{

    public Aluno(String nome, String email, String matricula, Boolean admin) {
        super(nome, email, matricula, admin);
        this.setAdmin(false);
    }

}
