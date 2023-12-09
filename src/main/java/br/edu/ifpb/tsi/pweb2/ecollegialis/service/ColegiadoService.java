package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColegiadoService {
    @Autowired
    ColegiadoRepository colegiadoRepository;

    @Autowired
    ProfessorService professorService;

    public List<Colegiado> findAll() {
        return colegiadoRepository.findAll();
    }

    public void deletarColegiado(Long id){
        var colegiado = findById(id);
        colegiado.getMembros().clear();
        colegiadoRepository.save(colegiado);
        this.colegiadoRepository.delete(colegiado);
    }

    public boolean temcolegiado(Long id){
        Professor verificarprofessor = professorService.findById(id);
        if(verificarprofessor.getColegiado() != null){
            return true;
        }
        return false;
    }

    public Colegiado removerProfessor(Long idColegiado, Long idProfessor){
        Professor professor = professorService.findById(idProfessor);
        Colegiado colegiado = findById(idColegiado);
        if (colegiado.getMembros().contains(professor)) {
            colegiado.getMembros().remove(professor);
        } else {
            throw new RuntimeException("O professor não está associado a este colegiado");
        }
        return this.colegiadoRepository.save(colegiado);
    }

    public Colegiado atualizarColegiado(Long id, Colegiado colegiadoObj) {
        Colegiado colegiado = findById(id);
        colegiado.setDescricao(colegiadoObj.getDescricao());
        colegiado.setPortaria(colegiadoObj.getPortaria());
        colegiado.setCurso(colegiadoObj.getCurso());
        return colegiadoRepository.save(colegiado);
    }

    public Colegiado adicionarProfessor(Long idColegiado, Long idProfessor){
        Professor professor = professorService.findById(idProfessor);
        Colegiado colegiado = findById(idColegiado);
        colegiado.getMembros().add(professor);
        professor.setColegiado(colegiado);
        return this.colegiadoRepository.save(colegiado);
    }

    public Colegiado cadastrarColegiado(Colegiado colegiado) {
        return colegiadoRepository.save(colegiado);
    }

    public Colegiado findById(Long id) {
        return colegiadoRepository.findById(id).get();
    }
}
