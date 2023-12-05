package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Usuario;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;


    public Map<Usuario, String> login(String matricula, String senha, String tipo) {
        Map<Usuario, String> usuario = null;
        switch (tipo) {
            case "aluno":
                usuario =  loginAluno(matricula, senha);
                break;
            case "professor":
                usuario =  loginProfessor(matricula, senha);
                break;
            case "coordenador":
                usuario = loginCoordenador(matricula, senha);
                break;

        }
        return usuario;

    }

    private Map<Usuario, String> loginAluno(String matricula, String senha) {
        Aluno aluno = alunoRepository.findByMatricula(matricula);
        if (aluno != null) {
            Map<Usuario, String> result =  new HashMap<>();
            result.put(aluno, "/aluno/processo");
            return result;
        }
        else {
            return null;
        }
    }

    private Map<Usuario, String> loginProfessor(String matricula, String senha) {
        Professor professor = professorRepository.findByMatricula(matricula);
        if (professor != null) {
            Map<Usuario, String> result =  new HashMap<>();
            result.put(professor, "/professor/home");
            return result;
        }
        else {
            return null;
        }
    }

    private Map<Usuario, String> loginCoordenador(String matricula, String senha) {
        Professor professor = professorRepository.findByMatricula(matricula);
        if (professor != null) {
            Map<Usuario, String> result =  new HashMap<>();
            result.put(professor, "/coordenador/processo");
            return result;
        }
        else {
            return null;
        }
    }
}
