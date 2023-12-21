package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReuniaoService {
    @Autowired
    private ReuniaoRepository reuniaoRepository;
    
    public List<Reuniao> getReunioes(){
        return this.reuniaoRepository.findAll();
    }

    public Reuniao getReuniaoPorId(Long id){
        return this.reuniaoRepository.findById(id).orElse(null);
    }

    public void apagarReuniao(Long id){
        this.reuniaoRepository.deleteById(id);
    }

    public Reuniao iniciarReuniao(Reuniao reuniao, Long id) throws Exception{
        for(Reuniao reuniao2 : this.reuniaoRepository.findAll()){
            if(reuniao2.getStatus().equals(StatusReuniao.EM_ANDAMENTO)){
                throw new Exception("Já existe uma reunião em andamento!");
            }
        }
        Reuniao reuniaoAtualizada = this.reuniaoRepository.findById(id).orElse(null);
        reuniaoAtualizada.setStatus(StatusReuniao.EM_ANDAMENTO);
        for(Processo processo : reuniaoAtualizada.getProcessos()){
            processo.setStatus(StatusEnum.EM_JULGAMENTO);
        }
        return this.reuniaoRepository.save(reuniaoAtualizada);
    }

    public Reuniao salvarReuniao(Reuniao reuniao){
        List<Processo> processosSelecionados = new ArrayList<>();

        if (reuniao.getProcessos() != null) {
            for (Processo processo : reuniao.getProcessos()){
                if (processo != null) {
                    processo.setStatus(StatusEnum.EM_PAUTA);
                    processo.setReuniao(reuniao);
                    processosSelecionados.add(processo);
                }
            }
            reuniao.setProcessos(processosSelecionados);
        }

        if (reuniao.getColegiado() != null) {
            reuniao.getColegiado().adicionarReuniao(reuniao);
        }

        return this.reuniaoRepository.save(reuniao);
    }

    public Reuniao encerrarReuniao(Reuniao reuniao, Long id){
        Reuniao reuniaoAtualizada = this.reuniaoRepository.findById(id).orElse(null);
        reuniaoAtualizada.setStatus(StatusReuniao.ENCERRADA);
        return this.reuniaoRepository.save(reuniaoAtualizada);
    }

    public List<Reuniao> getReunioesAgendadasDoColegiado(Colegiado colegiado) {
        return reuniaoRepository.findByColegiadoAndStatus(colegiado, StatusReuniao.PROGRAMADA);
    }

    public List<Reuniao> getReunioesFinalizadasDoColegiado(Colegiado colegiado) {
        return reuniaoRepository.findByColegiadoAndStatus(colegiado, StatusReuniao.ENCERRADA);
    }

    public List<Reuniao> getReunioesPorColegiado(Colegiado colegiado) {
        return reuniaoRepository.findByColegiadoId(colegiado.getId());
    }

    // método que verifica se todos os processos da reunião já foram julgados
    public boolean todosProcessosJulgados(Reuniao reuniao) {
        for (Processo processo : reuniao.getProcessos()) {
            if (!processo.getStatus().equals(StatusEnum.JULGADO)) {
                return false;
            }
        }
        return true;
    }

    public boolean temReuniaoEmAndamento(Colegiado colegiado) {
        List<Reuniao> reunioes = this.getReunioesPorColegiado(colegiado);
        System.out.println(reunioes);
        for (Reuniao reuniao : reunioes) {
            if (reuniao.getStatus().equals(StatusReuniao.EM_ANDAMENTO)) {
                return true;
            }
        }
        return false;
    }
}
