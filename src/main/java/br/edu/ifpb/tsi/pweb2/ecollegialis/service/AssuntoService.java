package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;

@Service
public class AssuntoService {

    @Autowired
    private AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public Assunto buscarPorId(Long id) {
        return assuntoRepository.findById(id).get();
    }

    public List<Assunto> listar() {
        return assuntoRepository.findAll();
    }

    public Assunto criarAssunto(){
        return this.assuntoRepository.save(new Assunto());
    }

    public Assunto encontrarPorId(Long id){
        return this.assuntoRepository.findById(id).get();
    }

    public Assunto atualizarAssunto(Long id){
        Assunto assunto = encontrarPorId(id);
        return this.assuntoRepository.save(assunto);
    }
}
