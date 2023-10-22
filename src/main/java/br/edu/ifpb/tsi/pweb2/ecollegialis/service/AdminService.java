package br.edu.ifpb.tsi.pweb2.ecollegialis.service;


import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final AssuntoRepository assuntoRepository;
    private final ColegiadoRepository colegiadoRepository;

    @Autowired
    public AdminService(
            ProfessorRepository professorRepository,
            AlunoRepository alunoRepository,
            CursoRepository cursoRepository,
            AssuntoRepository assuntoRepository,
            ColegiadoRepository colegiadoRepository
    ) {
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.assuntoRepository = assuntoRepository;
        this.colegiadoRepository = colegiadoRepository;
    }

    @Transactional
    public void registerTeacher(Professor professor) {
        //TODO verificar se o professor a ser salvo é coordenador,
        // se for, remover esse status do coordenador atual e atribuir
        // ao professor que está sendo registrado.

        professorRepository.save(professor);
    }

    @Transactional
    public void removeTeacher(Long id) {
        professorRepository.deleteById(id);
    }

    @Transactional
    public void updateTeacher(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> getAllTeachers() {
        return professorRepository.findAll();
    }
}
