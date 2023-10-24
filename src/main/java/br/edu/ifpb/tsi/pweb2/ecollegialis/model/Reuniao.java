package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dataReuniao;

    @Enumerated(EnumType.STRING)
    private StatusReuniao status;

    private byte[] ata;

    @OneToMany
    @JoinColumn(name = "reuniao_id")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public void addProcesso(Processo processo) {
        this.processos.add(processo);
    }
}