package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Aluno extends Usuario{

    public Aluno(int id, String nome, String email, String matricula, String phone , Boolean admin) {
        super(id, nome, email, matricula, phone, admin);
        this.setAdmin(false);
    }

}
