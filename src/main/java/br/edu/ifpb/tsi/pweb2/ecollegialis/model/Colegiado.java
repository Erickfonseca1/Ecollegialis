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
    private Date dataInicio;
    private Date dataFim;
    private String descricao;
    private String portaria;
    private String curso;

    @OneToMany(mappedBy = "colegiado")
    private List<Professor> professores;

}