package br.edu.ifpb.tsi.pweb2.ecollegialis.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assunto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Campo Obrigatório!")
    @Size(min=3, max = 40, message = "O nome deve ter entre 3 e 40 caracteres!")
    private String nome;

    @OneToMany (mappedBy = "assunto")
    private List<Processo> processos;


}
