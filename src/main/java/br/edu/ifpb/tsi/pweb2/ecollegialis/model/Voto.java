package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private TipoVoto tipoVoto;
    private boolean ausente;

    @ManyToOne
    @JoinColumn(name="id_professor")
    private Professor professor;

    @ManyToOne
    private Processo processo;
    private String justificativa;

}