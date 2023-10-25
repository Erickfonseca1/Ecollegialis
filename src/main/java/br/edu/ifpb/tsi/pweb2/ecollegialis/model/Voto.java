package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TipoVoto voto;
    private boolean ausente;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Processo processo;

    // Getters e setters
}
