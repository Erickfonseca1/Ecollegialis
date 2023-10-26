package br.edu.ifpb.tsi.pweb2.ecollegialis.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String nome;
    private String fone;
    @NotBlank(message = "Campo obrigatório")
    private String matricula;
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 60, message = "Senha deve ter entre 3 e 60 caracteres")
    private String senha;
}
