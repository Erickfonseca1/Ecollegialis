package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

public class Aluno extends Usuario{

    public Aluno(int id, String nome, String email, String matricula, String phone , Boolean admin) {
        super(id, nome, email, matricula, phone, admin);
        this.setAdmin(false);
    }
    public Processo criarProcesso(String assunto){
        Processo processo = new Processo(assunto);
        // pegar no banco o professor coordenador e chamar a função professor.receberProcesso(processo) passando 
        return processo;
    }

    

}
