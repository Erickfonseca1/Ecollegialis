package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    //CRUDS
    @Transactional
    public Professor criarProfessor(Professor professor) {

        return professorRepository.save(professor);
    }

    public List<Professor> listarProfessores(){

        return professorRepository.findAll();
    }
    public Professor buscarProfessorPorId(Long id){
        return professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
    }

    @Transactional
    public void deletarProfessor(Long id){
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        professorRepository.delete(professor);
    }

    @Transactional
    public Professor atualizarProfessor(Professor professorAtualizado) {
        Professor professorExistente = professorRepository.findById(professorAtualizado.getId()).orElse(null);
        if (professorExistente == null) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        professorExistente.setNome(professorAtualizado.getNome());
        return professorRepository.save(professorExistente);
    }

}