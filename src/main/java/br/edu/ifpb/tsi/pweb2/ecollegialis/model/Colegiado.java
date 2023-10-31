package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Colegiado{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataInicio;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Future(message = "Data deve ser futura")
    private Date dataFim;

    @NotBlank(message = "Campo obrigatório")
    private String descricao;

    @NotBlank(message = "Campo obrigatório")
    private String portaria;

    @NotBlank(message = "Campo obrigatório")
    private String curso;

    @OneToMany(mappedBy = "colegiado")
    private List<Professor> professoresColegiado = new ArrayList<>();

    @OneToMany(mappedBy = "colegiado")
    private List<Reuniao> reunioesColegiado = new ArrayList<>();

    @OneToOne
    private Aluno aluno;

    public void addReuniao(Reuniao reuniao) {
        this.reunioesColegiado.add(reuniao);
    }

    //criar um metodo para buscar todos os professores
    public List<Professor> getProfessores() {
        return professoresColegiado;
    }
}