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

    public List<Colegiado> getColegiados() {
        return this.colegiadoRepository.findAll();
    }

    public Colegiado getColegiadoPorId(Long id) {
        return this.colegiadoRepository.findById(id).orElse(null);
    }

    public Colegiado getColegiadoPorCoordenador(Coordenador coordenador) {
        List<Colegiado> colegiados = this.colegiadoRepository.findByCoordenador(coordenador);
    
        if (!colegiados.isEmpty()) {
            return colegiados.get(0);
        } else {
            return null;
        }
    }


    public Colegiado salvarColegiado(Colegiado colegiado){
        Coordenador coordenador = colegiado.getCoordenador();
        if (coordenador != null) {
            Professor professorCoordenador = coordenador.getProfessor();
            if (professorCoordenador != null) {
                professorCoordenador.adicionarColegiado(colegiado);
            }
        }
        for(Professor professor : colegiado.getMembros() ){
            professor.adicionarColegiado(colegiado);
        }
        colegiado.setDataInicio(new Date());
        return this.colegiadoRepository.save(colegiado);
    }

    public void deletarColegiado(Long id) {
        this.colegiadoRepository.deleteById(id);
    }
}
