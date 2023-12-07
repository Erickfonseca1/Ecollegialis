package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coordenador{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo obrigatório!")
    private String curso;

    @OneToOne
    private Professor professorRelator;

    public Coordenador(Professor professor, String curso){
        this.professorRelator = professor;
        this.curso = curso;
    }

    /* public void delegarProcesso(Processo processo, Professor professor){
        professor.adicionarProcesso(processo);
    } */

}
