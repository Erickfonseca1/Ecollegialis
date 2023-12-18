package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.List;
import java.util.Date;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parecerRelator;
    private String numero;
    private Date dataCriacao;
    private Date dataDistribuicao;
    private Date dataParecer;
    private byte[] documento;

    @ManyToOne
    private Professor relator;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Colegiado colegiado;

    @ManyToOne
    @JoinColumn(name = "assunto")
    private Assunto assunto;

    @Enumerated(EnumType.STRING)
    private TipoDecisao tipoDecisao;

    @OneToMany(mappedBy = "processo")
    private List<Voto> listaDeVotos;

    @NotBlank(message="Campo obrigatório!")
    @Size(min=3, max=42 ,message="A senha deverá ter pelo menos 3 caracteres e no máximo 42")
    private String textoRequerimento;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    private Reuniao reuniao;

    public Processo( Aluno aluno, Assunto assunto, String textoRequerimento, Colegiado colegiado) {
        this.aluno = aluno;
        this.numero = Long.toString(this.id);
        this.status = StatusEnum.CRIADO;
        this.dataCriacao = new Date();
        this.assunto = assunto;
        this.textoRequerimento = textoRequerimento;
        this.colegiado = colegiado;
    }

    public Processo(Aluno aluno,Assunto assunto){
        this.aluno = aluno;
        this.assunto = assunto;
    }

}
