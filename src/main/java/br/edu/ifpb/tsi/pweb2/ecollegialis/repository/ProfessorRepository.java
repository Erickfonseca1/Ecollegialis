package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    //REQ: 3 Consulta: O professor consulta todos os processos que lhe foram designados pelo coordenador.
    @Query("SELECT p FROM Processo p WHERE p.professor = ?1")
    public List<Processo> findAllByProfessor(Professor professor);

}

