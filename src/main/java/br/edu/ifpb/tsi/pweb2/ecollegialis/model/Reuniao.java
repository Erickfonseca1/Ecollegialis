package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import jakarta.persistence.*;
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
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dataReuniao;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao reuniao;

    private byte[] ata;

    @ManyToOne
    private Colegiado colegiado;

    @OneToMany
    @JoinColumn(name = "reuniao_id")
    private List<Processo> processos = new ArrayList<>();

    public void addProcesso(Processo processo) {
        this.processos.add(processo);

    }
}
