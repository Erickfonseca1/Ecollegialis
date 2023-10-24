package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Colegiado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataInicio;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataFim;
    private String descricao;
    private String portaria;

    @OneToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "colegiado")
    private List<Professor> membros;

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioes;

    public void addReuniao(Reuniao reuniao) {
        this.reunioes.add(reuniao);
    }
}