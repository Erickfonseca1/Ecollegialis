package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import jakarta.transaction.Transactional;
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
        return colegiadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Colegiado não encontrado"));
    }

    @Transactional
    public Colegiado criarColegiado(Colegiado colegiado) {
        return colegiadoRepository.save(colegiado);
    }

    @Transactional
    public void deletarColegiado(Long id) {
        Colegiado colegiado = colegiadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Colegiado não encontrado"));
        colegiadoRepository.delete(colegiado);
    }

    @Transactional
    public Colegiado atualizarColegiado(Colegiado colegiadoAtualizado, Long id) {
        Colegiado colegiado = colegiadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Colegiado não encontrado"));
        colegiado.setDataInicio(colegiadoAtualizado.getDataInicio());
        colegiado.setDataFim(colegiadoAtualizado.getDataFim());
        colegiado.setDescricao(colegiadoAtualizado.getDescricao());
        colegiado.setPortaria(colegiadoAtualizado.getPortaria());
        colegiado.setMembros(colegiadoAtualizado.getMembros());
        colegiado.setReunioes(colegiadoAtualizado.getReunioes());
        return colegiadoRepository.save(colegiado);
    }

    @Transactional
    public Colegiado adicionarProfessor(Long idColegiado, Long idProfessor){
        Professor professor = professorService.buscarProfessorPorId(idProfessor);
        Colegiado colegiado = colegiadoRepository.findById(idColegiado).orElseThrow(() -> new IllegalArgumentException("Colegiado não encontrado"));
        colegiado.getMembros().add(professor);
        professor.setColegiado(colegiado);
        return colegiadoRepository.save(colegiado);
    }

    @Transactional
    public Colegiado removerProfessor(int idColegiado, int idProfessor){
        Professor professor = professorService.buscarProfessorPorId((long) idProfessor);
        Colegiado colegiado = colegiadoRepository.findById((long) idColegiado).orElseThrow(() -> new IllegalArgumentException("Colegiado não encontrado"));
        colegiado.getMembros().remove(professor);
        professor.setColegiado(null);
        return colegiadoRepository.save(colegiado);
    }
}