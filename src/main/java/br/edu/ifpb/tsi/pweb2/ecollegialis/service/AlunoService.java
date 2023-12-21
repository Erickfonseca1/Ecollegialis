package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> getAlunos(){
        return this.alunoRepository.findAll();
    }

    public Aluno getAlunoPorId(Long id){
        return this.alunoRepository.findById(id).orElse(null);
    }

    public Aluno getAlunoPorMatricula(String matricula){
        return this.alunoRepository.findByMatricula(matricula);
    }

    public List<Aluno> getAlunosComProcessos(){
        List<Aluno> alunos = new ArrayList<Aluno>();
        for (Aluno aluno : this.alunoRepository.findAll()){
            if(aluno.getListaProcessos() != null){
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    public Aluno salvarAluno(Aluno aluno){
        return this.alunoRepository.save(aluno);
    }

    public void apagarAluno(Long id){
        this.alunoRepository.deleteById(id);
    }
}