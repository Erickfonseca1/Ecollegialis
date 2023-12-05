package br.edu.ifpb.tsi.pweb2.ecollegialis.model;


import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private TipoVoto tipoVoto;
    private boolean ausente;

    @ManyToOne
    private Professor professor;

    private String justificativa;

}
