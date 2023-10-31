package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor findById(Long id) {
        return professorRepository.findById(id).get();
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public void deleteById(Long id) {
        professorRepository.deleteById(id);
    }

    public void update(Professor professor) {
        professorRepository.save(professor);
    }
}
