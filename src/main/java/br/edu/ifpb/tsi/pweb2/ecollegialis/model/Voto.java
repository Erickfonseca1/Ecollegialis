package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    private TipoVoto voto;
    private boolean ausente;

    @ManyToOne
    private Professor professor;

    @OneToOne
    private Processo processo;
}
