package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusEnum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    public List<Processo> findByInteressado(Long idInteressado);
    public List<Processo> findByAssuntoAndInteressado(Long idAssunto, Long idInteressado);
    public List<Processo> findByInteressadoAndStatus(Long idInteressado, StatusEnum status);
    public Processo findByNumero(String numero); 

    @Query("SELECT p FROM Processo p WHERE p.status = 'EM_CURSO' OR p.status = 'EM_VOTACAO' OR p.status = 'EM_ESPERA' ORDER BY p.status ASC")
    public List<Processo> findProcessosEmCurso();

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1")
    public List<Processo> findAllByColegiado(Long id);

}
