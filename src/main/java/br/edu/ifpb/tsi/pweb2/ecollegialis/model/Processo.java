package br.edu.ifpb.tsi.pweb2.ecollegialis.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numero;
    private Date dataRecepcao;
    private Date dataDistribuicao;
    private String textoRelator;
    private Date dataParecer;
    private String textoAluno;
    private byte[] parecer;

    @Enumerated(EnumType.STRING)
    private StatusProcesso status;

    @Enumerated(EnumType.ORDINAL)
    private TipoDecisao decisaoRelator;

    @OneToOne
    @JoinColumn(name = "assunto_id")
    private Assunto assunto;

    @OneToMany
    @JoinColumn(name = "processo_id")
    private List<Voto> votos;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    private boolean emPauta = false;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor relator;

    @ElementCollection
    private List<byte[]> anexos;

    public TipoDecisao getTipoDecisao() {
        return this.decisaoRelator;
    }

    public void addAnexos(byte[] anexo) {
        this.anexos.add(anexo);
    }

    public void addVoto(Voto voto) {
        this.votos.add(voto);
    }

    public void setTipoDecisao(TipoDecisao decisaoRelator) {
        this.decisaoRelator = decisaoRelator;
    }

}
