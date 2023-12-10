package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Professor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 40)
    private String nome;

    @NotBlank(message = "Campo obrigatório")
    @Pattern(regexp = "[0-9]{11}" , message = "Telefone inválido")
    private String fone;

    @NotBlank(message = "Campo obrigatório")
    private String matricula;

    @NotBlank(message="Campo obrigatório")
    @Size(min = 3, max = 60, message = "Senha deve ter entre 3 e 60 caracteres")
    private String senha;

    @OneToMany(mappedBy = "professorRelator")
    protected List<Processo> listaDeProcessos;

    @ManyToMany(mappedBy = "membrosColegiado")
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
