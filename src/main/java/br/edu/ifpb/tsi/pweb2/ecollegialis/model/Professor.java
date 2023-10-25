package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Usuario{

    @ManyToOne
    private Colegiado colegiado;

    private boolean coordenador;

    @OneToMany(mappedBy = "professor")
    private List<Voto> votos;

    @OneToMany(mappedBy = "professor")
    private List<Processo> processos;
}
