package br.edu.ifpb.tsi.pweb2.ecollegialis.state;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "estado_julgamento")
public class EstadoEmJulgamento implements EstadoProcesso {
    
    @Override
    public String id() {
        return "Estado Julgamento";
    }

    private Processo processo;

    public EstadoEmJulgamento(Processo processo) {
        this.processo = processo;
    }

    @Override
    public void distribuir(Professor relator) {
        System.out.println("O processo está em julgamento e não pode ser redistribuído.");
    }

    @Override
    public void marcarEmPauta() {
        System.out.println("O processo está em julgamento e não pode ser marcado em pauta novamente.");
    }

    @Override
    public void iniciarJulgamento() {
        System.out.println("O processo já está em julgamento.");
    }

    @Override
    public void concluirJulgamento(boolean decisao) {
        processo.setStatus(StatusEnum.JULGADO);
        processo.setTipoDecisao(decisao ? TipoDecisao.DEFERIDO : TipoDecisao.INDEFERIDO);
        processo.setEstado(new EstadoJulgado(processo));
    }
}
