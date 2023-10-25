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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Date dataRecepcao;
    private Date dataDistribuicao;
    private Date dataParecer;
    private TipoDecisao parecer;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Aluno aluno;

    @OneToOne
    private Assunto assunto;

    @ManyToOne
    private Reuniao reuniao;

    @OneToMany
    private List<Voto> votos;

    private String requerimento;
    private StatusProcesso status;

}