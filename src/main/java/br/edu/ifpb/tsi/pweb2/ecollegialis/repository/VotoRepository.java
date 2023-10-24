package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
}