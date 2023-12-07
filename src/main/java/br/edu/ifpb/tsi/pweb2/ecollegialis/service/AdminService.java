package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    ColegiadoRepository colegiadoRepository;

    @Transactional
    public void registerTeacher(Professor professor) {
        professor.setAdmin(false);

        if (professor.getId() != null) {
            Professor professorBD = professorRepository.findById(professor.getId()).orElse(null);

            if (professorBD != null && professor.getSenha() == null) {
                // Mantém a senha existente do banco de dados
                professor.setSenha(professorBD.getSenha());
            } else {
                // Define uma nova senha ou atualiza a senha existente
                professor.setSenha(professor.getSenha());
            }
        } else {
            // Novo professor, define a senha
            professor.setSenha(professor.getSenha());
        }
    }


    @Transactional
    public void removeTeacher(Long id) {
        professorRepository.delete(professorRepository.findById(id).get());
    }

    @Transactional
    public void updateTeacher(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> allTeachers() {

        return professorRepository.findAll();
    }

    public List<Professor> allTeachersOfCourse(Long colegiadoId) {
        Colegiado colegiado = getColegiado(colegiadoId);
        List<Professor> result = new ArrayList<Professor>();

        professorRepository.findAllByCursoId(colegiado.getCurso().getId()).forEach(professor -> {
            if (!colegiado.getMembros().contains(professor) && !professor.isCoordenador()) {
                result.add(professor);
            }
        });

        return result;
    }

    public Professor getTeacher(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void registerStudent(Aluno aluno) {
        aluno.setAdmin(false);

        if (aluno.getId() != null) {
            Aluno alunoBD = alunoRepository.findById(aluno.getId()).orElse(null);

            if (alunoBD != null && aluno.getSenha() == null) {
                // Mantém a senha existente do banco de dados
                aluno.setSenha(alunoBD.getSenha());
            } else {
                // Define uma nova senha ou atualiza a senha existente
                aluno.setSenha(aluno.getSenha());
            }
        } else {
            // Novo aluno, define a senha
            aluno.setSenha(aluno.getSenha());
        }
    }

    @Transactional
    public void removeStudent(Long id) {
        alunoRepository.delete(alunoRepository.findById(id).get());
    }
    @Transactional
    public void updateStudent(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> allStudent() {
        return alunoRepository.findAll();
    }

    public Aluno getStudent(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    @Transactional
    public void removeCourse(Long id) {
        cursoRepository.delete( cursoRepository.findById(id).get() );

    }

    @Transactional
    public void updateCourse(Curso curso) {
        cursoRepository.save(curso);
    }

    public List<Curso> allCourses() {

        return cursoRepository.findAll();
    }

    public Curso getCourse(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }


    @Transactional
    public void registerSubject(Assunto assunto) {

        assuntoRepository.save(assunto);
    }

    @Transactional
    public void removeSubject(Long id) {

        assuntoRepository.delete(assuntoRepository.findById(id).get());
    }

    @Transactional
    public void updateSubject(Assunto assunto) {

        assuntoRepository.save(assunto);
    }

    public List<Assunto> allSubjects() {
        return this.assuntoRepository.findAll();
    }

    public Assunto getSubject(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }

    public List<Colegiado> allColegiados() {
        return colegiadoRepository.findAll();
    }

    public Colegiado getColegiado(Long id) {
        return colegiadoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void salvarColegiado(Colegiado colegiado) {
        colegiadoRepository.save(colegiado);
    }

    @Transactional
    public void excluir(Long id) {
        colegiadoRepository.deleteById(id);
    }

    @Transactional
    public void adicionarMembro(Long ColegiadoId, Long professorId) {
        Colegiado colegiado = colegiadoRepository.findById(ColegiadoId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        colegiado.getMembros().add(professor);
        professor.setColegiado(colegiado);
        salvarColegiado(colegiado);
    }

    @Transactional
    public void deletarMembro(Long ColegiadoId, Long professorId) {
        Colegiado colegiado = colegiadoRepository.findById(ColegiadoId).orElse(null);
        Professor professor = professorRepository.findById(professorId).orElse(null);
        colegiado.getMembros().remove(professor);
        professor.setColegiado(null);
        professorRepository.save(professor);
        colegiadoRepository.save(colegiado);
    }
}