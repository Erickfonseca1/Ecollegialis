package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @FutureOrPresent(message = "A data da reunião deve ser no presente ou no futuro")
    private Date dataReuniao;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao status;

    private byte[] ata;

    @OneToMany()
    @JoinColumn(name = "reuniaoID")
    private List<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public Reuniao(Colegiado colegiado, List<Processo> processos, StatusReuniao statusReuniao) {
    }

    public void addProcesso(Processo processo) {
        this.processos.add(processo);

    }
}
