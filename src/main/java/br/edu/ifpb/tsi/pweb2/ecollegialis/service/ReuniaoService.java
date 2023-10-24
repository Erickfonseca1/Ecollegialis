package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import java.util.List;


@Service
public class ReuniaoService {
    
    @Autowired
    ReuniaoRepository reuniaoRepository;

    @Autowired
    ColegiadoRepository colegiadoRepository;

    @Autowired
    ProcessoRepository processoRepository;


    public List<Reuniao> listarReuniao(){
        return reuniaoRepository.findAll();
    }

    public Reuniao buscarPorId(Long id) {
        return reuniaoRepository.findById(id).get();
    }

    public Reuniao criarReuniao(){
        return this.reuniaoRepository.save(new Reuniao());
    }

    public void deletarReuniao(Reuniao reuniao){
        if (reuniao.getId() != null && this.reuniaoRepository.existsById(reuniao.getId())) {
            this.reuniaoRepository.delete(reuniao);
        } else {
            throw new RuntimeException("A reunião não existe");
        }
    }

    public Reuniao alteraReuniao(Reuniao reuniao){
        if (reuniao.getId() != null && this.reuniaoRepository.existsById(reuniao.getId())) {
            return this.reuniaoRepository.save(reuniao);
        } else {
            throw new RuntimeException("A reunião não existe");
        }
    }
    
    public StatusReuniao buscarStatus(Long id) {
        Reuniao reuniao = reuniaoRepository.findById(id).get();
        return reuniao.getStatus();
    }

    public void adicionarProcesso(Long idReuniao, Long idProcesso) {
        Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
        reuniao.getProcessos().add(processoRepository.findById(idProcesso).get());
        reuniaoRepository.save(reuniao);
    }

    public void adicionarColegiado(Long idReuniao, Long idColegiado) {
        Reuniao reuniao = reuniaoRepository.findById(idReuniao).orElseThrow(() -> new RuntimeException("A reunião não existe"));
        Colegiado colegiado = colegiadoRepository.findById(idColegiado).orElseThrow(() -> new RuntimeException("O colegiado não existe"));
    
        reuniao.setColegiado(colegiado);
        reuniaoRepository.save(reuniao);
    }

    public void removerProcesso(Long idReuniao, Long idProcesso) {
        Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
        reuniao.getProcessos().remove(processoRepository.findById(idProcesso).get());
        reuniaoRepository.save(reuniao);
    }
    
}
