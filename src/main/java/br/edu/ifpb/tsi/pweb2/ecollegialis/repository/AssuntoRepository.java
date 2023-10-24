package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
    Assunto findByNome(String nome);
}