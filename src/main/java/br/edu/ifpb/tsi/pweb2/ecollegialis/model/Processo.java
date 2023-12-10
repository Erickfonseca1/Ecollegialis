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
    private byte[] documento;

    @ManyToOne
    private Professor professorRelator;

    @ManyToOne
    private Aluno alunoProcesso;

    @ManyToOne
    private Colegiado processosColegiado;

    @ManyToOne
    @JoinColumn(name = "assuntoProcesso")
    private Assunto assunto;

    @Enumerated(EnumType.STRING)
    private TipoDecisao decisaoRelator;

    @OneToMany(mappedBy = "votosProcesso")
    private List<Voto> listaDeVotos;

    @NotBlank(message="Campo obrigatório!")
    private String textoRequerimento;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    private Reuniao reuniaoProcesso;

    public Processo(Aluno aluno, Assunto assunto, String textoRequerimento, Colegiado colegiado){
        this.alunoProcesso = aluno;
        this.numero = Long.toString(this.id);
        this.status = StatusEnum.CRIADO;
        this.dataRecepcao = new Date();
        this.assunto = assunto;
        this.textoRequerimento = textoRequerimento;
        this.processosColegiado = colegiado;
    }

    public Processo(Aluno aluno,Assunto assunto){
        this.alunoProcesso = aluno;
        this.assunto = assunto;
    }
}
