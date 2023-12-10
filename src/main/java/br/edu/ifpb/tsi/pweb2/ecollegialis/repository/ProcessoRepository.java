package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import java.util.List;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo,Long>{
    public List<Processo> findByAluno(Aluno aluno);

    public List<Processo> findByRelator(Professor professor);
}
