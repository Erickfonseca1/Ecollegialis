package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    // Consulta: Obter todos os processos relacionados a um relator específico e a um colegiado específico
    @Query("SELECT p FROM Colegiado c JOIN c.reunioes r JOIN r.processos p WHERE c.id = ?1 AND p.relator.id = ?2")
    public List<Processo> findAllByColegiadoAndRelator(Long idColegiado, Long idRelator);

    // Consulta: Obter um processo pelo número
    @Query("SELECT p FROM Processo p WHERE p.numero = ?1")
    public Processo findByNumero(String numero);

    // Consulta: Obter todos os processos relacionados a um relator específico
    @Query("SELECT p FROM Processo p WHERE p.relator.id = ?1")
    public List<Processo> findAllByRelatorId(Long idRelator);

    // Consulta: Obter processos em curso, votação ou espera, ordenados pelo status
    @Query("SELECT p FROM Processo p WHERE p.status IN ('EM_CURSO', 'EM_VOTACAO', 'EM_ESPERA') ORDER BY p.status ASC")
    public List<Processo> findProcessosEmCurso();

    // Consulta: Obter todos os processos relacionados a um colegiado
    @Query("SELECT p FROM Colegiado c JOIN c.reunioes r JOIN r.processos p WHERE c.id = ?1")
    public List<Processo> findAllByColegiado(Long idColegiado);
    

}
