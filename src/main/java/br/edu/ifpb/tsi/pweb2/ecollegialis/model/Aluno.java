package br.edu.ifpb.pweb2.ecollegialis.model;



import javax.persistence.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity


public class Aluno extends Usuario {}