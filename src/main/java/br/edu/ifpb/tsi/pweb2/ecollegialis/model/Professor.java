package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class Professor {
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
    @Matricula
    private String matricula;

    @NotBlank(message="Campo obrigatório")
    @Size(min = 3, max = 60, message = "Senha deve ter entre 3 e 60 caracteres")
    private String senha;

    @ManyToOne
    @JoinColumn(name = "curso")
    protected Curso curso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "relator")
    protected List<Processo> listaDeProcessos;

    @ManyToMany(mappedBy = "membros")
    protected List<Colegiado> listaColegiados;


    public Professor(Long id, String nome, String fone, String matricula, String senha){
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.matricula = matricula;
        this.senha = senha;
    }

    public void adicionarProcesso(Processo processo){
        this.listaDeProcessos.add(processo);
    }

    public void adicionarColegiado(Colegiado colegiado){
        this.listaColegiados.add(colegiado);
    }

    @Override
    public String toString(){
        return "Professor " + this.nome;
    }

}