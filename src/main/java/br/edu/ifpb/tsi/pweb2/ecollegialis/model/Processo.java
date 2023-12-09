package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private Date dataParecer;
    private byte[] parecer;


    @Size(min = 0, max = 500, message = "O texto deve ter no máximo 500 caracteres")
    private String textoRelator;

    @NotBlank(message = "O texto não pode ser vazio")
    @Size(min = 6, max = 500, message = "O texto deve ter entre 6 e 500 caracteres")
    private String textoAluno;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.ORDINAL)
    private TipoDecisao decisaoRelator;

    @ManyToOne
    @JoinColumn(name = "assuntoID")
    private Assunto assunto;

    @OneToMany
    @JoinColumn(name = "processoID")
    private List<Voto> votos;

    @ManyToOne
    @JoinColumn(name = "reuniaoID")
    private Reuniao reuniao;

    @ManyToOne
    @JoinColumn(name = "alunoID")
    private Aluno alunoProcesso;

    private boolean emPauta = false;

    @ManyToOne
    @JoinColumn(name = "professorID")
    private Professor relator;

    @ManyToOne
    @JoinColumn(name = "cursoID")
    private Curso curso;

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
