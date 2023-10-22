package br.edu.ifpb.tsi.pweb2.ecollegialis.service;


import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
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

    public Professor getTeacher(Long id){
        return professorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void registerStudent(Aluno aluno){
        alunoRepository.save(aluno);
    }

    @Transactional
    public void removeStudent(Long id) {
        alunoRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> getAllStudents() {
        return alunoRepository.findAll();
    }

    public Aluno getStudent(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void AddCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    @Transactional
    public void removeCourse(Long id){
        cursoRepository.deleteById(id);
    }

    @Transactional
    public void updateCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    public List<Curso> getAllCourses() {
        return cursoRepository.findAll();
    }
}