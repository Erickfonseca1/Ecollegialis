package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo obrigatório")
    @Size(min = 3, max = 40)
    @Pattern(regexp = "[a-zA-ZÀ-ÖØ-öø-ÿ\s]+", message = "Nome inválido")
    private String nome;

    @NotBlank(message="Campo obrigatório")
    @Pattern(regexp = "[0-9]{11}" , message = "Telefone inválido")
    private String fone;

    @NotBlank(message="Campo obrigatório")
    @Pattern(regexp = "[0-9]{11}" , message = "Matrícula inválida")
    private String matricula;

    @NotBlank(message="Campo obrigatório")
    @Size(min = 3, max = 60, message = "Senha deve ter entre 3 e 60 caracteres")
    private String senha;

    @OneToMany(mappedBy = "aluno")
    private List<Processo> listaProcessos;

    @ManyToOne
    @JoinColumn(name="curso")
    private Curso curso;

    //preferi não inserir atributo Boolean admin, pois não vejo situações onde aluno é admin

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;

    public Aluno(String nome, String fone, String matricula, String senha) {
        this.nome = nome;
        this.fone = fone;
        this.matricula = matricula;
        this.senha = senha;
    }

    public void adicionarProcesso(Processo processo){
        this.listaProcessos.add(processo);
    }

    @Override
    public String toString(){
        return "Aluno "+this.nome;
    }
}
