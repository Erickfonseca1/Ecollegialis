package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoVoto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean ausente;

    @OneToOne
    private Professor professor;

    @ManyToOne
    private Processo processo;

    @Enumerated(EnumType.STRING)
    private TipoVoto tipoVoto;

    public Voto(boolean ausente) {
        this.ausente = ausente;
    }

}