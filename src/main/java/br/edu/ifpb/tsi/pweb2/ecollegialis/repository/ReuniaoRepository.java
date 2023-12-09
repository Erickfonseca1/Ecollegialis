package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id = ?1 and r.status = ?2")
    public List<Reuniao> AllReunioesByProfessorAndColegiadoAndStatus(Long idProfessor, StatusReuniao status);

    @Query("select r from Professor p join p.colegiado c join c.reunioes r where p.id= ?1")
    public List<Reuniao> AllReunioesByProfessorAndColegiado(Long idProfessor);

    @Query("SELECT r FROM Reuniao r INNER JOIN r.colegiado c WHERE c.id = :colegiadoId")
    List<Reuniao> listareuniaocolegiado (@Param("colegiadoId") Long colegiadoId);

    @Query("SELECT r FROM Reuniao r WHERE r.status = :status AND r.colegiado.id = :idcolegiado")
    List<Reuniao> filtrarStatus(@Param("status") StatusReuniao status, @Param("idcolegiado") Long idcolegiado);

    List<Reuniao> findAllByStatusInAndColegiado(List<StatusReuniao> status, Colegiado colegiado) ;

    List<Reuniao> findByStatus(StatusReuniao status);
}
