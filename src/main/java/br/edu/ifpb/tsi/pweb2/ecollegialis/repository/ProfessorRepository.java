package br.edu.ifpb.tsi.pweb2.ecollegialis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    //Consulta: Obter todos os professores que são coordenadores de algum colegiado
    @Query("SELECT DISTINCT p.coordenador FROM Colegiado c JOIN c.coordenador p")
    public List<Professor> findAllCoordenadores();
    //Consulta: Obter professor que tenham matricula
    @Query("SELECT p FROM Professor p WHERE p.matricula = ?1")
    Professor findByMatricula(String matricula);

    //Consulta: Obter professor que seja coordenador
    @Query("SELECT p FROM Professor p WHERE p.coordenador = ?1")
    Professor findCoordenador(Long id);

    //Consulta: Obter professor que esteja vinculado a algum curso
    @Query("SELECT p FROM Professor p WHERE p.curso = ?1")
    Professor findByCurso(String curso);
    
    //Consulta: Achar professor pelo nome
    @Query("SELECT p FROM Professor p WHERE p.nome = ?1")
    List<Professor> findByNome(String nome);

    //Consulta: Obter todos os professores que são relatores de algum processo
    @Query("SELECT DISTINCT p.relator FROM Processo p")
    public List<Processo> findAllRelatores();
}
