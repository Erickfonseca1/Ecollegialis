package br.edu.ifpb.pweb2.ecollegialis.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.ecollegialis.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
}
