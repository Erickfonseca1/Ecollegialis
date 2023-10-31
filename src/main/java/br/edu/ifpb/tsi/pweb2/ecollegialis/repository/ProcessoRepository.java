package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    List<Processo>findByStatusAndAluno(boolean status, Aluno aluno);

    List<Processo>findByProfessorRelator(Professor professor);

    List<Processo>findAllByAluno(Aluno aluno);

    @Query("SELECT p FROM Processo p WHERE p.aluno = ?1 AND p.status = ?2 AND p.assunto = ?3")
    List<Processo>findAllByAlunoAndStatusAndAssunto(Aluno aluno, boolean status, Assunto assunto);

    @Query("SELECT p FROM Processo p WHERE p.professor = ?1 AND p.status = ?2 AND p.assunto = ?3 AND p.professor = ?4")
    List<Processo> findAllByProfessorAndStatusAndAssunto(Professor professor, boolean status, Assunto assunto);

    List<Professor> findAllByDataRecepcao();

    @Query("SELECT p FROM Processo p WHERE p.professor = ?1 AND p.status = ?2 AND p.assunto = ?3 AND p.professor = ?4")
    List<Processo> findAllByAlunoAndProfessorAndStatusOrderByDataRecepcao(Aluno aluno, Professor professor, boolean status);

}
