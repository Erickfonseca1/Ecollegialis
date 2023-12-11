package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> getCursos(){
        return this.cursoRepository.findAll();
    }

    public Curso getCursoPorId(Long id){
        return this.cursoRepository.findById(id).orElse(null);
    }

    public Curso salvarCurso(Curso assunto){
        return this.cursoRepository.save(assunto);
    }

    public void deletarCurso(Long id){
        this.cursoRepository.deleteById(id);
    }
}
