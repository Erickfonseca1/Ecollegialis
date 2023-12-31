package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Voto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;
    
    public List<Voto> getVotos(){
        return this.votoRepository.findAll();
    }

    public Voto getVotoPorId(Long id){
        return this.votoRepository.findById(id).orElse(null);
    }

    public Voto salvarVoto(Voto voto){
        return this.votoRepository.save(voto);
    }

    public void deletarVoto(Long id){
        this.votoRepository.deleteById(id);
    }
}
