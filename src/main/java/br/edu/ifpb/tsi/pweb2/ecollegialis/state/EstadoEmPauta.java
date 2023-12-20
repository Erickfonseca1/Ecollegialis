package br.edu.ifpb.tsi.pweb2.ecollegialis.state;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado_pauta")
public class EstadoEmPauta implements EstadoProcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String id() {
        return "Estado em Pauta";
    }

    private Processo processo;

    public EstadoEmPauta(Processo processo) {
        this.processo = processo;
    }

    @Override
    public void distribuir(Professor relator) {
        System.out.println("O processo já está em pauta e não pode ser redistribuído.");
    }

    @Override
    public void marcarEmPauta() {
        System.out.println("O processo já está em pauta.");
    }

    @Override
    public void iniciarJulgamento() {
        processo.setStatus(StatusEnum.EM_JULGAMENTO);
        processo.setEstado(new EstadoEmJulgamento(processo));
    }

    @Override
    public void concluirJulgamento(boolean decisao) {
        System.out.println("O processo precisa ser julgado antes de concluir o julgamento.");
    }
}
