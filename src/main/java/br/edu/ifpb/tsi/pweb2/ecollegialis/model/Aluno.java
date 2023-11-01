package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo obrigatório!")
    private String nome;

    @NotBlank(message="Campo obrigatório!")
    private String fone;

    @NotBlank(message="Campo obrigatório!")
    @Pattern(regexp= "[0-9]{11}", message="Por favor, insira uma matrícula válida! (11 dígitos)")
    private String matricula;

    @NotBlank(message="Campo obrigatório!")
    @Size(min=3, max=25 ,message="A senha deverá ter pelo menos 3 caracteres e no máximo 25")
    private String senha;

    @OneToMany(mappedBy = "alunoProcesso", cascade = {CascadeType.REMOVE, CascadeType.MERGE}) // verificar o cascade
    private List<Processo> listaProcessos;

    public Aluno(){}

    public void adicionarProcesso(Processo processo){
        this.listaProcessos.add(processo);
    }

}