package br.edu.ifpb.tsi.pweb2.ecollegialis.state;

import java.util.Date;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado_distribuido")
public class EstadoDistribuido implements EstadoProcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String id() {
        return "Estado Distribuido";
    }

    private Processo processo;

    public EstadoDistribuido(Processo processo) {
        this.processo = processo;
    }

    @Override
    public void distribuir(Professor relator) {
        System.out.println("O processo já foi distribuído a um relator.");
    }

    @Override
    public void marcarEmPauta() {
        processo.setDataParecer(new Date());
        processo.setStatus(StatusEnum.EM_PAUTA);
        processo.setEstado(new EstadoEmPauta(processo));
    }

    @Override
    public void iniciarJulgamento() {
        System.out.println("O processo precisa estar em pauta antes de iniciar o julgamento.");
    }

    @Override
    public void concluirJulgamento(boolean decisao) {
        System.out.println("O processo ainda não foi julgado.");
    }
}
