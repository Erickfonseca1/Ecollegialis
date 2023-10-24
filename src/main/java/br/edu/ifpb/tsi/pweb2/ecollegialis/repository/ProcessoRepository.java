package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    public List<Processo> findAllById(Long id);

    // Consulta: Obter todos os processos relacionados a um relator específico e a um colegiado específico
    @Query("SELECT p FROM Colegiado c JOIN c.reunioes r JOIN r.processos p WHERE c.id = ?1 AND p.relator.id = ?2")
    public List<Processo> findAllByColegiadoAndRelator(Long idColegiado, Long idRelator);

    // Consulta: Obter um processo pelo número
    @Query("SELECT p FROM Processo p WHERE p.numero = ?1")
    public Processo findByNumero(String numero);

    // Consulta: Obter todos os processos relacionados a um relator específico
    @Query("SELECT p FROM Processo p WHERE p.relator.id = ?1")
    public List<Processo> findAllByRelatorId(Long idRelator);

    // Consulta: Obter processos ordenados pelos status
    @Query("SELECT p FROM Processo p ORDER BY p.status")
    public List<Processo> findAllByStatus();

    // Consulta: Obter todos os processos relacionados a um colegiado
    @Query("SELECT p FROM Colegiado c JOIN c.reunioes r JOIN r.processos p WHERE c.id = ?1")
    public List<Processo> findAllByColegiado(Long idColegiado);

    //Consulta: Obter o status de um processo
    @Query("SELECT p.status FROM Processo p WHERE p.id = ?1")
    public StatusProcesso findStatusById(Long id);

    //Consulta: Obter processos que tenham assunto
    @Query("SELECT p FROM Processo p WHERE p.assunto = ?1")
    public List<Processo> findAllByAssunto(Long idAssunto);

    //Consulta: Obter processos que tenham status
    @Query("SELECT p FROM Processo p WHERE p.status = ?1")
    public List<Processo> findByIdAndStatus  (Long id, StatusProcesso status);

    //Consulta: Obter Processo por coordenador e status
    @Query("SELECT p FROM Processo p WHERE p.colegiado.coordenador.id = ?1 AND p.status = ?2")
    public List<Processo> findAllByCoordenadorAndStatus(Long idCoordenador, StatusProcesso status);

    //Consulta: listar Todos Processos Do Colegiado Por Status
    @Query("SELECT p FROM Processo p WHERE p.colegiado.id = ?1 AND p.status = ?2")
    public List<Processo> findAllByColegiadoAndStatus(Long idColegiado, StatusProcesso status);

    //Consulta: listar Todos Processos Do Colegiado Por Aluno
    @Query("SELECT p FROM Processo p WHERE p.colegiado.id = ?1 AND p.interessado.id = ?2")
    public List<Processo> findAllByColegiadoAndInteressado(Long idColegiado, Long idInteressado);

    public List<Processo> findAllByInteressadoIdAndAssuntoId(Long id, Long idAssunto);




    

}
