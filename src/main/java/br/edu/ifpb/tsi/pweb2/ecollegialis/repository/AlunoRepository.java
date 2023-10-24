package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}