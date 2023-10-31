package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ColegiadoService {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    private Colegiado createColegiado() {
        return this.colegiadoRepository.save(new Colegiado());
    }

    public List<Colegiado> findAll() {
        return colegiadoRepository.findAll();
    }

    public Colegiado findById(Long id) {
        return colegiadoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        colegiadoRepository.deleteById(id);
    }

    public Colegiado removeProfessor(Long idColegiado, Long IdProfessor){
        Professor professor = professorService.findById(IdProfessor);
        Colegiado colegiado = this.findById(idColegiado);
        if(colegiado.getProfessores().contains(professor)){
            colegiado.getProfessores().remove(professor);
        } else {
            throw new RuntimeException("O professor não está associado a este colegiado");
        }
        return this.colegiadoRepository.save(colegiado);
    }

    public Colegiado atualizarColegiado(Long idColegiado){
        Colegiado colegiado = findById(idColegiado);
        colegiado.setDescricao(colegiado.getDescricao());
        colegiado.setPortaria(colegiado.getPortaria());
        colegiado.setCurso(colegiado.getCurso());
        return this.colegiadoRepository.save(colegiado);
    }
}
