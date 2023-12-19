package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class Colegiado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataInicio;
    private Date dataFim;

    @NotBlank(message="Campo obrigatório!")
    @Size(min=3, max=42 ,message="A senha deverá ter pelo menos 3 caracteres e no máximo 42")
    private String descricao;

    private String portaria;

    @OneToOne
    @JoinColumn(name = "curso")
    private Curso curso;

    @OneToOne
    @JoinColumn(name="coordenador")
    private Coordenador coordenador;

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioes;

    @ManyToMany
    private List<Professor> membros;

    @OneToMany(mappedBy = "colegiado")
    private List<Processo> processos;

    public Colegiado(Date dataInicio, Date dataFim, String descricao, String portaria, Curso curso, Coordenador coordenador) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.portaria = portaria;
        this.curso = curso;
        this.coordenador = coordenador;
    }

    public Colegiado(List<Professor> professores){
        this.membros = professores;
    }

    public void adicionarReuniao(Reuniao reuniao){
        this.reunioes.add(reuniao);
    }

    public void adicionarProcesso(Processo processo){
        this.processos.add(processo);
    }

    @Override
    public String toString(){
        return "Colegiado do " + this.curso;
    }


}