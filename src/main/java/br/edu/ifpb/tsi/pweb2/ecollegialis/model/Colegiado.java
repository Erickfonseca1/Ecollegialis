package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Colegiado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataInicio;
    private Date dataFim;

    @NotBlank(message="Campo obrigatório!")
    private String descricao;

    private String portaria;

    @NotBlank(message="Campo obrigatório!")
    private String curso;

    @ManyToMany
    private List<Professor> professorColegiado;

    @OneToMany(mappedBy = "colegiadoProcesso")
    private List<Processo> processos;

    @OneToMany(mappedBy = "reuniaoColegiado")
    private List<Reuniao> reunioes = new ArrayList<>();

    public Colegiado(){}

    public Colegiado(List<Professor> professores){
        this.professorColegiado = professores;
    }

}