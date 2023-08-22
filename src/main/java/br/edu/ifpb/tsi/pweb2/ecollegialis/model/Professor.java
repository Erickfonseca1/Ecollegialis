package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Professor extends Usuario{
    private Boolean coordenador;

    public Professor(int id, String nome, String email, String matricula, String phone , Boolean admin) {
        super(id,nome, email, matricula, phone, admin);
        this.coordenador = false;
        this.setAdmin(false);
    }
    
    public Boolean getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Boolean coordenador) {
        this.coordenador = coordenador;
    }

}
