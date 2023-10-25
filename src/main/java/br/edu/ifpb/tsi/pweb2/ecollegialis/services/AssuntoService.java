package br.edu.ifpb.tsi.pweb2.ecollegialis.services;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    @Autowired
    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public Assunto cadastrarAssunto(Assunto assunto) {
        // Implemente a lógica necessária para cadastrar um novo assunto, como validações e outros processamentos.
        return assuntoRepository.save(assunto);
    }

    public List<Assunto> listarAssuntos() {
        return assuntoRepository.findAll();
    }

    public Assunto obterAssuntoPorId(Long id) {
        return assuntoRepository.findById(id).orElse(null);
    }
}

