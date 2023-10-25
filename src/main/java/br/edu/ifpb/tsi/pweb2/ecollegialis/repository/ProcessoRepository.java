package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    public List<Processo> findAllById(Long id);

    // REQ: 2 Consulta: Obter processos do aluno por status e assunto ordenados por data de criacao
    @Query("SELECT p FROM Processo p WHERE p.aluno.id = ?1 AND p.status = ?2 AND p.assunto.id = ?3 ORDER BY p.dataRecepcao")
    public List<Processo> findAllByAlunoIdAndStatusAndAssuntoId(Long id, StatusProcesso status, Long idAssunto);

    //REQ:7 Consulta: selecionar todos processos do colegiado, filtrando-os por status aluno ou professor relator.
    @Query("SELECT p FROM Processo p WHERE p.status = ?1 AND (p.aluno.id = ?2 OR p.professor.id = ?3)")
    public List<Processo> findAllByStatusAndAlunoIdAndProfessorId(StatusProcesso status, Long idAluno, Long idProfessor);

}