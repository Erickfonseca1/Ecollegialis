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

    public Processo salvarProcesso(Processo processo){
        processo.setId(null);
        processo.getAluno().adicionarProcesso(processo);
        alunoService.salvarAluno(processo.getAluno());
        processo.setStatus(StatusEnum.CRIADO);
        processo.setDataCriacao(new Date());
        processo.setNumero(""+new Date().getTime());
        return this.processoRepository.save(processo);
    }

    public Processo atribuirProcesso(Processo processo,Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        processoAtualizado.setRelator(processo.getRelator());
        for (Colegiado colegiado : processo.getRelator().getListaColegiados()){
            if(colegiado.getCurso() == processo.getRelator().getCurso()){
                processoAtualizado.setColegiado(colegiado);
                break;
            }
        }
        processoAtualizado.setStatus(StatusEnum.DISTRIBUIDO);
        processoAtualizado.setDataDistribuicao(new Date());
        return this.processoRepository.save(processoAtualizado);
    }

    public Processo atribuirProcessoColegiado(Processo processo,Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        processoAtualizado.setColegiado(processo.getColegiado());
        processoAtualizado.setDataDistribuicao(new Date());
        return this.processoRepository.save(processoAtualizado);
    }

    public Processo julgarProcesso(Processo processo, Long id){
        Processo processoAtualizado = this.processoRepository.findById(id).orElse(new Processo());
        List<Voto> novaListaVotos = new ArrayList<Voto>();
        int comRelator = 1;
        int divergente = 0;
        for(Voto voto: processo.getListaDeVotos()){
            novaListaVotos.add(voto);
            if (voto.getTipoVoto() == TipoVoto.DIVERGENTE) {
                divergente+=1;
            }
            if(voto.getTipoVoto() == TipoVoto.COM_RELATOR){
                comRelator+=1;
            }
            votoService.salvarVoto(voto);
        }
        processoAtualizado.setListaDeVotos(novaListaVotos);
        if (divergente>comRelator) {
            if (processoAtualizado.getTipoDecisao() == TipoDecisao.DEFERIDO) {
                processoAtualizado.setTipoDecisao(TipoDecisao.INDEFERIDO);
            }
            if (processoAtualizado.getTipoDecisao() == TipoDecisao.INDEFERIDO) {
                processoAtualizado.setTipoDecisao(TipoDecisao.DEFERIDO);
            }
        }
        processoAtualizado.setStatus(StatusEnum.JULGADO);;
        return this.processoRepository.save(processoAtualizado);
    }

}
