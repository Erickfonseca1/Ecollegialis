package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
public class Professor extends Usuario{

    private boolean coordenador;

    @ManyToOne
    private Colegiado colegiado;
    @OneToMany
    private List<Processo> processos;

    public Professor(Long id, String nome, String fone, String matricula, String senha, boolean coordenador) {
        super(id, nome, fone, matricula, senha);
        this.coordenador = coordenador;
    }
    public Professor() {

    }
}
