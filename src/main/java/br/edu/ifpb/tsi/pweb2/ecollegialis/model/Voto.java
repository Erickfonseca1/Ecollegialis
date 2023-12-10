package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Professor professor;

    @ManyToOne
    private Processo votosProcesso;

    @Enumerated(EnumType.STRING)
    private TipoVoto tipoVoto;

    private boolean ausente;
    public Voto(boolean ausente) {
        this.ausente = ausente;
    }

}
