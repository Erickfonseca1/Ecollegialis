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
    private boolean ausente;

    @OneToOne
    private Professor votoProfessor;

    @ManyToOne
    private Processo votoProcesso;

    @Enumerated(EnumType.STRING)
    private TipoVoto tipoVoto;

    public Voto(boolean ausente) {
        this.ausente = ausente;
    }

}
