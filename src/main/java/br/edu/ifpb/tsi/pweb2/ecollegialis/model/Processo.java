package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String numero;
    private Date dataCriacao;
    private Date dataDistribuicao;
    private Date dataParecer;
    private byte[] parecer;
    private byte[] documento;

    @ManyToOne
    private Professor professorRelator;

    @ManyToOne
    private Aluno alunoProcesso;

    @ManyToOne
    private Colegiado colegiadoProcesso;

    @ManyToOne
    private Assunto assuntoProcesso;

    @Enumerated(EnumType.STRING)
    private TipoDecisao tipoDecisao;

    @OneToMany(mappedBy = "votoProcesso") // verificar cascade
    private List<Voto> listaDeVotos;

    @NotBlank(message="Campo obrigatório!")
    private String textoRequerimento;

    @Enumerated(EnumType.STRING)
    private StatusEnum estadoProcesso;

    public Processo( Aluno aluno, Assunto assunto, String textoRequerimento, Colegiado colegiado) {
        this.alunoProcesso = aluno;
        this.estadoProcesso = StatusEnum.CRIADO;
        this.dataCriacao = new Date();
        this.assuntoProcesso = assunto;
        this.textoRequerimento = textoRequerimento;
        this.colegiadoProcesso = colegiado;
    }

    public Processo(Aluno aluno,Assunto assunto){
        this.alunoProcesso = aluno;
        this.assuntoProcesso = assunto;
    }

}
