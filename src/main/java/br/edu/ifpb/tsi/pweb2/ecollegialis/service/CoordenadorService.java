package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordenadorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Processo> listarProcessos(Long id) {
        return processoRepository.findAllById(id);
    }
    public Processo getProcesso(Long idProcesso) {
        return processoRepository.findById(idProcesso).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
    }

    public void salvarProcesso(Processo processo) {
        Processo processadoSalvo = processoRepository.findById(processo.getId()).get();
        processadoSalvo.setTipoDecisao(processo.getTipoDecisao());
        processadoSalvo.setParecer(processo.getParecer());
        processadoSalvo.setStatus(processo.getStatus());
        processoRepository.save(processadoSalvo);
    }
    public List<Colegiado> listarProcessoPorColegiados() {
        return colegiadoRepository.findAll();
    }

    public List<Professor> listarCoordenadores() {
        return professorRepository.findAllByCoordenadorIsTrue();
    }

    @Transactional
    public Professor criarNovoCoordenador(Professor professor) {
        professor.setCoordenador(true);
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor apagarProfessorCoordenador(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        professor.setCoordenador(false);
        return professorRepository.save(professor);
    }

    @Transactional
    public Professor atualizarProfessorCoordenador(Professor professorAtualizado, Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        professor.setNome(professorAtualizado.getNome());
        professor.setCurso(professorAtualizado.getCurso());
        professor.setCoordenador(professorAtualizado.isCoordenador());
        return professorRepository.save(professor);
    }










}
