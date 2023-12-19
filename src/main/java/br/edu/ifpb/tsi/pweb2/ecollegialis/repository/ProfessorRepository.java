package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

    Professor findByMatricula(String matricula);
}
