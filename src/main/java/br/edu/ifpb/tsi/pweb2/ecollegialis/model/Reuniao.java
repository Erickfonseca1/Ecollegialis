package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.ArrayList;
import java.util.Date;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
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
    private StatusEnum status;

    @OneToMany(mappedBy = "reuniao")
    private ArrayList<Processo> processos;

    @ManyToOne
    private Colegiado colegiado;

    public void adicionarProcesso(Processo processo) {
        this.processos.add(processo);
    }
}
