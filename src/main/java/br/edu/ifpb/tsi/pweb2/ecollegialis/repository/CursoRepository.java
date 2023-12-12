package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
