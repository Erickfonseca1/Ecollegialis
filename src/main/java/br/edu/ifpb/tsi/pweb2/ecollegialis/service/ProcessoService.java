package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoVoto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcessoService {
    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private VotoService votoService;

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

    public Processo atualizarProcesso(Processo processo, Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        processoAtualizado.setParecerRelator(processo.getParecerRelator());
        processoAtualizado.setTipoDecisao(processo.getTipoDecisao());
        return this.processoRepository.save(processoAtualizado);
    }

    public void atualizarProcessoParaJulgado(Processo processo, Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        processoAtualizado.setStatus(StatusEnum.JULGADO);
        processoAtualizado.setDataParecer(new Date());
        processoAtualizado.setParecer(processo.getParecer());
        processoAtualizado.setTipoDecisao(processo.getTipoDecisao());
        this.processoRepository.save(processoAtualizado);
    }

    public Processo salvarProcesso(Processo processo){
        processo.setId(null);
        processo.getAluno().adicionarProcesso(processo);
        alunoService.salvarAluno(processo.getAluno());
        processo.setStatus(StatusEnum.CRIADO);
        processo.setDataCriacao(new Date());
        processo.setNumero(""+new Date().getTime());
        return this.processoRepository.save(processo);
    }

    public Processo atribuirRelator(Processo processo,Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        // esse relator vem do objeto processo que é passado como parâmetro, que é o que vem do nosso form
        processoAtualizado.setRelator(processo.getRelator());
        for (Colegiado colegiado : processo.getRelator().getListaColegiados()){
            if(colegiado.getCurso() == processo.getRelator().getCurso()){
                // como temos a regra de que um curso só pode ter 1 colegiado,
                // então se o curso do relator for igual ao curso do colegiado, atribuímos o colegiado ao processo
                processoAtualizado.setColegiado(colegiado);
                break;
            }
        }
        processoAtualizado.setStatus(StatusEnum.DISTRIBUIDO);
        processoAtualizado.setDataDistribuicao(new Date());
        return this.processoRepository.save(processoAtualizado);
    }
}
