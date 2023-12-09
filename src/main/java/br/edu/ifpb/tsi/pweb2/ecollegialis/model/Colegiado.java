package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Colegiado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Campo obrigatório")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent(message = "A data de início deve ser a data atual")
    private LocalDate dataInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "A data de fim deve ser a data atual ou posterior")
    private LocalDate dataFim;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 5, max = 500, message = "A descrição deve ter entre 5 e 500 caracteres")
    private String descricao;

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "[0-9]+", message = "O número da portaria deve conter apenas números")
    private String portaria;

    @OneToOne
    @JoinColumn(name = "cursoID")
    private Curso curso;

    @OneToMany(mappedBy = "colegiado")
    private List<Professor> membros;

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioes;

    public Colegiado(Colegiado colegiado) {
    }

    public void addReuniao(Reuniao reuniao) {
        this.reunioes.add(reuniao);
    }
}


