package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColegiadoService {
    

    ColegiadoRepository colegiadoRepository;
    ProfessorService professorService;


    public List<Colegiado> listarColegiados() {
        return colegiadoRepository.findAll();
    }

    public Colegiado buscarPorId(Long id) {
        return colegiadoRepository.findById(id).orElse(null);
    }

    public Colegiado criarColegiado(Colegiado colegiado) {
        return colegiadoRepository.save(colegiado);
    }

    public void deletarColegiado(Colegiado colegiado) {
        colegiadoRepository.delete(colegiado);
    }

    public Colegiado alterarColegiado(Colegiado colegiado) {
        return colegiadoRepository.save(colegiado);
    }   

    public Colegiado atualizarColegiado(Long id, Colegiado colegiado) {
        colegiado.setId(id);
        return colegiadoRepository.save(colegiado);
    }

}
