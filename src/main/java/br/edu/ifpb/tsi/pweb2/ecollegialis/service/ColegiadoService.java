package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColegiadoService {

    final ColegiadoRepository colegiadoRepository;
    @Autowired
    public ColegiadoService(ColegiadoRepository colegiadoRepository) {
        this.colegiadoRepository = colegiadoRepository;
    }
}
