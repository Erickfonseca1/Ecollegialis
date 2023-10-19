package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Professor findCoordenador(Long id);
    Professor findByMatricula(String matricula);
    Professor findByCurso(String curso);
    List<Professor> findByNome(String nome);
}
