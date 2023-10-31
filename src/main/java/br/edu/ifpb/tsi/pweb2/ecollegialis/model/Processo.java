package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
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
    private Date dataParecer;
    private byte[] parecer;


    @Size(min = 0, max = 300, message = "O texto deve ter no máximo 300 caracteres")
    private String textoRelator;

    @NotBlank(message = "O texto não pode ser vazio")
    @Size(min = 0, max = 300, message = "O texto deve ter entre 6 e 300 caracteres")
    private String textoAluno;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.ORDINAL)
    private TipoDecisao decisaoRelator;

    @ManyToOne
    @JoinColumn(name = "assunto_id")
    private Assunto assunto;

    @OneToMany
    @JoinColumn(name = "processo_id")
    private List<Voto> votos;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno interessado;

    private boolean emPauta = false;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor relator;

    @ElementCollection
    private List<byte[]> anexos;

    public void addAnexos(byte[] anexo) {
        this.anexos.add(anexo);
    }

    public void addVoto(Voto voto) {
        this.votos.add(voto);
    }

    public void setTipoDecisao(TipoDecisao decisaoRelator) {
        this.decisaoRelator = decisaoRelator;
    }

    public TipoDecisao getTipoDecisao() {
        return this.decisaoRelator;
    }
}
