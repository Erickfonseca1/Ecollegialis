package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;

    private String textoRequerimento;
    private Date dataRecepcao;
    private Date dataDistribuicao;
    private Date dataParecer;
    private byte[] parecer;
    private TipoDecisao decisaoRelator;

    @ManyToOne
    private Professor relator;

    @ManyToMany
    private List<Professor> coordenador;

    @ManyToOne
    private Assunto assunto;

    @ManyToOne
    private Aluno aluno;

    // Getters e setters
}

