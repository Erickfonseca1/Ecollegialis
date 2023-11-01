package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
public class Assunto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Size(min=5, max = 40, message = "O nome deve ter entre 5 e 40 caracteres!")
    private String nome;
    public Assunto(){}

    @OneToMany(mappedBy = "assuntoProcesso") // verificar cascade
    private List<Processo> processos;
}
