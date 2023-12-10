package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Coordenador;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deletarCoordenador(Long id){
        this.coordenadorRepository.deleteById(id);
    }
}
