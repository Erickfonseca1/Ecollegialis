package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    public void save(Processo processo) {
        processoRepository.save(processo);
    }

    public void update(Processo processo) {
        processoRepository.save(processo);
    }

    public Processo findById(Long id) {
        return processoRepository.findById(id).get();
    }

    public List<Processo> findAll() {
        return processoRepository.findAll();
    }
}
