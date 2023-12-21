package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao,Long>{
    List<Reuniao> findByColegiadoAndStatus(Colegiado colegiado, StatusReuniao status);

    List<Reuniao> findByColegiado(Colegiado colegiado);

    List<Reuniao> findByColegiadoId(Long id);
}
