package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.List;
import java.util.Date;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.state.EstadoCriado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.state.EstadoProcesso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private byte[] parecer;
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

    @NotBlank(message = "Campo obrigatório!")
    private String textoRequerimento;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    private Reuniao reuniao;

    @ManyToOne
    private EstadoProcesso estado;  // Adicionando o atributo estado

    public Processo(Aluno aluno, Assunto assunto, String textoRequerimento, Colegiado colegiado) {
        this.aluno = aluno;
        this.numero = Long.toString(this.id);
        this.status = StatusEnum.CRIADO;
        this.dataCriacao = new Date();
        this.assunto = assunto;
        this.textoRequerimento = textoRequerimento;
        this.colegiado = colegiado;
        this.estado = new EstadoCriado(this);  // Definindo o estado inicial
    }

    public Processo(Aluno aluno, Assunto assunto) {
        this.aluno = aluno;
        this.assunto = assunto;
        this.estado = new EstadoCriado(this);  // Definindo o estado inicial
    }

    public void setEstado(EstadoProcesso novoEstado) {
        this.estado = novoEstado;
    }

    @Override
    public String toString() {
        return "" + this.numero + "," + this.aluno;
    }
}
