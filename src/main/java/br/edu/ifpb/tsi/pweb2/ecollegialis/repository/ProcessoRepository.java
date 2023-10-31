package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    List<Processo>findByStatusAndAluno(boolean status, Aluno aluno);

    List<Processo>findByProfessorRelator(Professor professor);

    List<Processo>findAllByAluno(Aluno aluno);

}
