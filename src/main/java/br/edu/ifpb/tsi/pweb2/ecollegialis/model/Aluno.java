package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
public class Aluno extends Usuario {

    @OneToMany(mappedBy = "aluno")
    private List<Processo> processos = new ArrayList<>();

    @OneToOne
    private Colegiado colegiado;

    public Aluno(Long id, String nome, String fone, String matricula, String senha, boolean admin) {
        super(id, nome, fone, matricula, senha, admin);
    }

    public Aluno() {}

    public void addProcesso(Processo processo) {
        this.processos.add(processo);
    }
}