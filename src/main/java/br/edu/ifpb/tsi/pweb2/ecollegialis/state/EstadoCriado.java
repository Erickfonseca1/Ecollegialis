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
@Table(name = "estado_processo")
public class EstadoCriado implements EstadoProcesso {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String id() {
        return "Estado em Processo";
    }


    private Processo processo;

    public EstadoCriado(Processo processo) {
        this.processo = processo;
    }

    @Override
    public void distribuir(Professor relator) {
        processo.setRelator(relator);
        processo.setDataDistribuicao(new Date());
        processo.setStatus(StatusEnum.DISTRIBUIDO);
        processo.setEstado(new EstadoDistribuido(processo));
    }

    @Override
    public void marcarEmPauta() {
        System.out.println("O processo precisa ser distribuído antes de ser colocado em pauta.");
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
