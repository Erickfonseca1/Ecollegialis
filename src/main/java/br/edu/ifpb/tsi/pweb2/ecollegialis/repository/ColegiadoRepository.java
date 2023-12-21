package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColegiadoRepository extends JpaRepository<Colegiado,Long>{
    public List<Colegiado> findByCoordenador(Coordenador coordenador);
}
