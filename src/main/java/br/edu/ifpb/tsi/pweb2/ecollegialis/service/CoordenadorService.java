package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import java.util.List;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Coordenador;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordenadorService {
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public List<Coordenador> getCoordenadores(){
        return this.coordenadorRepository.findAll();
    }

    public Coordenador getCoordenadorPorId(Long id){
        return this.coordenadorRepository.findById(id).orElse(null);
    }

    public Coordenador salvarCoordenador(Coordenador coordenador){
        return this.coordenadorRepository.save(coordenador);
    }

    public Coordenador getCoordenadorPorCurso(Long id){
        return this.coordenadorRepository.findByCursoId(id);
    }

    public Coordenador getCoordenadorPorProfessor(Long id){
        return this.coordenadorRepository.findByProfessorId(id);
    }

    public void deletarCoordenador(Long id){
        this.coordenadorRepository.deleteById(id);
    }

}
