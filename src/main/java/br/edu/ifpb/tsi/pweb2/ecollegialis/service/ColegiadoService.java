package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColegiadoService {
    ColegiadoRepository colegiadoRepository;

    public List<Colegiado> findAll() {
        return colegiadoRepository.findAll();
    }

    public Colegiado findById(Long id) {
        return colegiadoRepository.findById(id).get();
    }
}
