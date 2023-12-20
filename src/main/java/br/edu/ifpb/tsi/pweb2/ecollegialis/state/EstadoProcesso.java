package br.edu.ifpb.tsi.pweb2.ecollegialis.state;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public interface EstadoProcesso {

    @Id
    String id();

    void distribuir(Professor relator);
    void marcarEmPauta();
    void iniciarJulgamento();
    void concluirJulgamento(boolean decisao);
}
