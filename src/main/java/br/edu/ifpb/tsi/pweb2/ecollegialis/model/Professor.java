package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo obrigatório!")
    protected String nome;

    @NotBlank(message="Campo obrigatório!")
    protected String fone;

    @NotBlank(message="Campo obrigatório!")
    @Pattern(regexp= "[0-9]{6}", message="Matrícula deve conter exatamente 6 números!")
    protected String matricula;

    @Size(min=3, max=42 ,message="A senha deverá ter pelo menos 3 caracteres e no máximo 42")
    protected String senha;

    @OneToMany(mappedBy = "professorRelator")
    protected List<Processo> listaDeProcessos;

    @ManyToMany(mappedBy = "professorColegiado")
    protected List<Colegiado> listaColegiados;

    public Professor(){}

    public void adicionarProcesso(Processo processo){
        this.listaDeProcessos.add(processo);
    }

    public void adicionarColegiado(Colegiado colegiado){
        this.listaColegiados.add(colegiado);
    }

}
