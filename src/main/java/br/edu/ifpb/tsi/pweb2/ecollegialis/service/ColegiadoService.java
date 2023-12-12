package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ColegiadoService {
    @Autowired
    private ColegiadoRepository colegiadoRepository;

    public List<Colegiado> getColegiados(){
        return this.colegiadoRepository.findAll();
    }

    public Colegiado getColegiadoPorId(Long id){
        return this.colegiadoRepository.findById(id).orElse(null);
    }

    public Colegiado getColegiadoPorCoordenador(Coordenador coordenador){
        return this.colegiadoRepository.findByCoordenador(coordenador).get(0);
    }

    public Colegiado salvarColegiado(Colegiado colegiado){
        for(Professor professor : colegiado.getMembros() ){
            professor.adicionarColegiado(colegiado);
        }
        colegiado.setDataInicio(new Date());
        return this.colegiadoRepository.save(colegiado);
    }

    public void deletarColegiado(Long id){
        this.colegiadoRepository.deleteById(id);
    }
}
