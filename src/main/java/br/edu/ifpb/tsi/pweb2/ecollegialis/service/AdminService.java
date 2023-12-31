package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Authority;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Coordenador;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.User;

import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AuthorityRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.CoordenadorRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.CursoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    // CRUD assunto

    @Transactional
    public void criarAssunto(Assunto assunto) {
        assuntoRepository.save(assunto);
    }

    @Transactional
    public void removerAssunto(Long id) {
        assuntoRepository.deleteById(id);
    }

    public List<Assunto> getAssuntos() {
        return assuntoRepository.findAll();
    }

    public Assunto getAssuntoPorId(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }
    

    // CRUD curso 

    @Transactional
    public void criarCurso(Curso curso) {
        cursoRepository.save(curso);
    }
    
    @Transactional
    public void removerCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public List<Curso> getCursos() {
        return cursoRepository.findAll();
    }

    public Curso getCursoPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    // CRUD aluno

    @Transactional
    public void criarAluno(Aluno aluno) {
        PasswordEncoder hash = new BCryptPasswordEncoder();
        Aluno alunoBD;

        if (aluno.getId() == null) {
            alunoBD = new Aluno();
            String passwordEncrypt = hash.encode(aluno.getSenha());
            User user = new User(aluno.getMatricula(), passwordEncrypt);
            user.setEnabled(true);
            user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_ALUNO")));

            alunoBD.setUser(user);
            alunoBD.setNome(aluno.getNome());
            alunoBD.setCurso(aluno.getCurso());
            alunoBD.setFone(aluno.getFone());
            alunoBD.setMatricula(aluno.getMatricula());
            alunoBD.setSenha(passwordEncrypt);
            alunoRepository.save(alunoBD);
        } else {
            alunoBD = alunoRepository.findByMatricula(aluno.getMatricula());
            BeanUtils.copyProperties(aluno, alunoBD, "senha", "processos", "matricula", "user");
            boolean matchPassword = hash.matches(aluno.getSenha(), alunoBD.getSenha());
            if (!matchPassword) {
                String passwordEncrypt = hash.encode(aluno.getSenha());
                alunoBD.setSenha(passwordEncrypt);
            }
            alunoRepository.save(alunoBD);
        }
    }


    @Transactional
    public void atualizarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    @Transactional
    public void removerAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElse(null);
        aluno.getUser().setEnabled(false);
        alunoRepository.delete(aluno);
    }

    public List<Aluno> getAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno getAlunoPorId(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public List<Aluno> getAlunosComProcessos() {
        List<Aluno> alunos = new ArrayList<>();
        for (Aluno aluno : alunoRepository.findAll()) {
            if (aluno.getListaProcessos().size() > 0) {
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    // CRUD professor

    @Transactional
    public void criarProfessor(Professor professor) {
        PasswordEncoder hash = new BCryptPasswordEncoder();
        Professor professorBD;

        if (professor.getId() == null) {
            professorBD = new Professor();
            String passwordEncrypt = hash.encode(professor.getSenha());
            User user = new User(professor.getMatricula(), passwordEncrypt);
            user.setEnabled(true);
            user.setAuthorities(Collections.singletonList(new Authority(user, "ROLE_PROFESSOR")));

            professorBD.setNome(professor.getNome());
            professorBD.setCurso(professor.getCurso());
            professorBD.setFone(professor.getFone());
            professorBD.setMatricula(professor.getMatricula());
            professorBD.setSenha(passwordEncrypt);
            professorBD.setUser(user);
        } else {
            professorBD = professorRepository.findByMatricula(professor.getMatricula());
            BeanUtils.copyProperties(professor, professorBD, "senha", "processos", "matricula", "user");
            boolean matchPassword = hash.matches(professor.getSenha(), professorBD.getSenha());

            // se a senha que veio do formulário for diferente da senha que está no banco de dados, eu criptografo a senha que veio do formulário e salvo no banco de dados
            if (!matchPassword) {
                String passwordEncrypt = hash.encode(professor.getSenha());
                professorBD.setSenha(passwordEncrypt);
            }
        }
        professorRepository.save(professorBD);
    }

    @Transactional
    public void atualizarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    @Transactional
    public void removerProfessor(Long id) {
        Professor professor = professorRepository.findById(id).orElse(null);
        professor.getUser().setEnabled(false);
        professorRepository.delete(professor);
    }

    public List<Professor> getProfessores() {
        return professorRepository.findAll();
    }

    public Professor getProfessorPorId(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    public List<Professor> getProfessoresPorCurso(Long id) {
        List<Professor> professores = new ArrayList<>();
        for (Professor professor : this.professorRepository.findAll()) {
            if (professor.getCurso().getId() == id) {
                professores.add(professor);
            }
        }
        return professores;
    }

    // CRUD coordenador

    public List<Coordenador> getCoordenadores() {
        return coordenadorRepository.findAll();
    }

    public Coordenador getCoordenadorPorId(Long id) {
        return coordenadorRepository.findById(id).orElse(null);
    }

    @Transactional
    public void criarCoordenador(Coordenador coordenador) {
        Professor professor = coordenador.getProfessor();
        User user = professor.getUser();

        boolean hasCoordenadorRole = user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_COORDENADOR"));
        if (!hasCoordenadorRole) {
            Authority coordenadorAuthority = new Authority(user, "ROLE_COORDENADOR");   
            user.getAuthorities().add(coordenadorAuthority);
        }

        coordenadorRepository.save(coordenador);
    }

    @Transactional
    public void atualizarCoordenador(Coordenador coordenador) {
        coordenadorRepository.save(coordenador);
    }

    @Transactional
    public void deletarCoordenador(Long id) {
        this.coordenadorRepository.deleteById(id);
    }

}
