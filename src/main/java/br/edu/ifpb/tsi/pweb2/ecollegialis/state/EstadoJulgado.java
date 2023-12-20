package br.edu.ifpb.tsi.pweb2.ecollegialis.state;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "estado_julgado")
public class EstadoJulgado implements EstadoProcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String id() {
        return "Estado Julgado";
    }

    private Processo processo;

    public EstadoJulgado(Processo processo) {
        this.processo = processo;
    }

    @Override
    public void distribuir(Professor relator) {
        System.out.println("O processo já foi julgado e não pode ser redistribuído.");
    }

    @Override
    public void marcarEmPauta() {
        System.out.println("O processo já foi julgado e não pode ser marcado em pauta novamente.");
    }

    @Override
    public void iniciarJulgamento() {
        System.out.println("O processo já foi julgado e não pode ser julgado novamente.");
    }

    @Override
    public void concluirJulgamento(boolean decisao) {
        System.out.println("O processo já foi julgado.");
    }
}
