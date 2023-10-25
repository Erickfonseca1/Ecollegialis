package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataReuniao;
    private StatusReuniao status;
    private byte[] ata;

    @ManyToMany
    private List<Processo> pauta;

    @ManyToOne
    private Colegiado colegiado;

    // Getters e setters
}

