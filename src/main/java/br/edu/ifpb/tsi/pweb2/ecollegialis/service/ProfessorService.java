package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> findAllProfessores() {
        return professorRepository.findAll();
    }

    public List<Professor> findProfessoresWithProcesso() {
        List<Professor> professores = new ArrayList<Professor>();
        for (Professor professor : professorRepository.findAll()) {
            if (!professor.getProcessos().isEmpty()) {
                professores.add(professor);
            }
        }
        return professores;
    }

    public List<Professor> findProfessorWithColegiado(){
        List<Professor> professores = new ArrayList<Professor>();
        for (Professor professor : professorRepository.findAll()) {
            if (professor.getColegiado() != null) {
                professores.add(professor);
            }
        }
        return professores;
    }

    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor findById(Long id) {
        return professorRepository.findById(id).orElse(null);
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
