package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssuntoService {
    @Autowired
    private AssuntoRepository assuntoRepository;

    public List<Assunto> getAssuntos(){
        return this.assuntoRepository.findAll();
    }

    public Assunto getAssuntoPorId(Long id){
        return this.assuntoRepository.findById(id).orElse(null);
    }

    public Assunto salvarAssunto(Assunto assunto){
        return this.assuntoRepository.save(assunto);
    }

    public void deletarAssunto(Long id){
        this.assuntoRepository.deleteById(id);
    }
}
