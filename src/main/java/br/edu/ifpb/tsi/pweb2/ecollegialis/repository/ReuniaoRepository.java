package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusReuniao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    @Query("SELECT r FROM Reuniao r WHERE r.status = ?1")
    public List<Reuniao> findByStatus(StatusReuniao status);

    @Query("SELECT r FROM Reuniao r WHERE r.status = ?1 AND r.colegiado.id = ?2")
    public List<Reuniao> findByStatusAndColegiado(StatusReuniao status, Long id);

    Reuniao findByCodigo(String codigo);
}
