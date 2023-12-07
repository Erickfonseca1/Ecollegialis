package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColegiadoRepository extends JpaRepository<Colegiado, Long> {
    Colegiado findByCursoId(Long id);
}