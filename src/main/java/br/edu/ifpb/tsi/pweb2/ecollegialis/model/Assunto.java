package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Assunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    //Processo possui mapeamento de assunto
    @OneToMany(mappedBy = "assunto")
    private List<Processo> processos;

    // Getters e setters
}
