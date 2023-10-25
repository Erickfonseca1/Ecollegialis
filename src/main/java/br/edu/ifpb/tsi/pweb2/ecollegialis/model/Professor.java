package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Professor extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "relator")
    private List<Processo> processosRelatados;

    @ManyToMany(mappedBy = "coordenador")
    private List<Processo> processosCoordenados;

    @ManyToMany
    private List<Processo> processosJulgados;

    @ManyToMany
    private List<Processo> processosDistribuidos;

    private boolean coordenador;

    // Getters e setters
}
