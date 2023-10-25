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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataReuniao;
    private StatusReuniao status;
    @Column ()
    private byte[] ata;

    @OneToMany
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public void addProcesso(Processo processo) {
        this.processos.add(processo);
    }
}