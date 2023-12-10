package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent(message = "A data da reunião deve ser no presente ou no futuro")
    private Date dataReuniao;

    @Enumerated(EnumType.ORDINAL)
    private StatusReuniao status;

    @OneToMany(mappedBy = "reuniaoProcesso")
    private ArrayList<Processo> processos;

    public void adicionarProcesso(Processo processo) {
        this.processos.add(processo);
    }
}
