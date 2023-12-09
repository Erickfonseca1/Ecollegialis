package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    public List<Processo> findAllByalunoProcessoId(Long id);

    public List<Processo> findAllByalunoProcessoIdAndAssuntoId(Long id, Long idAssunto);

    public List<Processo> findAllByalunoProcessoIdAndEmPauta(Long id, Boolean pauta);

    public List<Processo> findAllByalunoProcessoCursoId(Long id);



    public List<Processo> findAllByRelatorId(Long idRelator);

    public List<Processo> findAllByEmPauta(Boolean pauta);

    public List<Processo> findAllByalunoProcessoIdAndStatus(Long id, StatusEnum status);

    public Processo findByNumero(String numero);


    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1")
    public List<Processo> findAllByColegiado(Long id);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.status = ?2")
    public List<Processo> findAllByColegiadoAndStatus(Long id, StatusEnum status);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.alunoProcesso.id = ?2")
    public List<Processo> findAllByColegiadoAndalunoProcesso(Long id, Long idalunoProcesso);

    @Query("select p from Colegiado c join c.reunioes r join r.processos p where c.id = ?1 and p.relator.id = ?2")
    public List<Processo> findAllByColegiadoAndRelator(Long id, Long idRelator);


    List<Processo> findAllByalunoProcessoIdAndAssuntoIdOrderByDataRecepcaoDesc(Long id, Long idAssunto);

    List<Processo> findAllByalunoProcessoIdAndStatusOrderByDataRecepcaoDesc(Long id, StatusEnum status);

    List<Processo> findAllByalunoProcessoIdOrderByDataRecepcaoDesc(Long id);

    List<Processo> findAllByCursoId(Long id);

    List<Processo> findAllByCursoIdAndStatus(Long id, StatusEnum filtroEnum);
}

