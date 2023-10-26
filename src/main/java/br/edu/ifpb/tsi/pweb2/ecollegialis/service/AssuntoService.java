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

    //CRUD de Assunto
    public Assunto save(Assunto assunto) {
        return assuntoRepository.save(assunto);
    }

    public List<Assunto> findAll() {
        return assuntoRepository.findAll();
    }

    public Assunto findById(Long id) {
        return assuntoRepository.findById(id).get();
    }

    public void update(Assunto assunto) {
        assuntoRepository.save(assunto);
    }

    public void deleteById(Long id) {
        assuntoRepository.deleteById(id);
    }
}
