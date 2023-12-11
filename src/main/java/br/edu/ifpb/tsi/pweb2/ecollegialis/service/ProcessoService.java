package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProcessoService {
    @Autowired
    private ProcessoRepository processoRepository;

    public List<Processo> getProcessos(){
        return this.processoRepository.findAll();
    }

    public List<Processo> getProcessosPorAluno(Aluno aluno){
        return this.processoRepository.findByAluno(aluno);
    }

    public List<Processo> getProcessosPorProfessor(Professor professor){
        return this.processoRepository.findByRelator(professor);
    }

    public Processo getProcessoPorId(Long id){
        return this.processoRepository.findById(id).orElse(null);
    }

    public Processo salvarProcesso(Processo processo){
        processo.getAluno().adicionarProcesso(processo);
        processo.setStatus(StatusEnum.CRIADO);
        processo.setDataCriacao(new Date());
        processo.setNumero(""+new Date().getTime());
        return this.processoRepository.save(processo);
    }

    public Processo atribuirProcesso(Processo processo,Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        processoAtualizado.setRelator(processo.getRelator());
        processoAtualizado.setStatus(StatusEnum.DISTRIBUIDO);
        processoAtualizado.setDataDistribuicao(new Date());
        return this.processoRepository.save(processoAtualizado);
    }

}
