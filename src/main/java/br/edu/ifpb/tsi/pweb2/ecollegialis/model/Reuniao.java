package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.Date;

import java.util.List;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo obrigatório!")
    private Date dataReuniao;

    @Enumerated(EnumType.STRING)
    private StatusReuniao status;

    @OneToMany(mappedBy = "reuniao")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public Reuniao(Colegiado colegiado,List<Processo> processos){
        this.colegiado = colegiado;
        this.processos = processos;
        this.status = StatusReuniao.PROGRAMADA;
    }

    public void adicionarProcesso(Processo processo) {
        this.processos.add(processo);
    }

    @Override
    public String toString(){
        return "Reunião de "+ this.colegiado+" - "+ this.dataReuniao;
    }
}
