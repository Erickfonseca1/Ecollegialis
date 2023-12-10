package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReuniaoService {

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovaReuniao(List<Processo> processos, Reuniao reuniao){
        for (Processo processo : processos) {
            processo.setReuniao(reuniao);
        }

        for(Processo processo : processos){
            processoRepository.save(processo);
        }
    }

    public Optional<Reuniao> encontrarPorId(Long id){
        return this.reuniaoRepository.findById(id);
    }

    public List<Reuniao> encontrarTodas(){
        return this.reuniaoRepository.findAll();
    }

    public void deletarReuniao(Reuniao reuniao){
        this.reuniaoRepository.delete(reuniao);
    }

    public Enum obterStatus(Long idReuniao){
        Optional<Reuniao> reuniao = this.reuniaoRepository.findById(idReuniao);
        if (reuniao.isPresent()) {
            Reuniao reuniaoValidada = reuniao.get();
            return reuniaoValidada.getStatus();
        }
        else{
            throw new RuntimeException("reunião não encontrada");
        }

    }
    public void adicionarColegiado(Long idReuniao, Long idColegiado) {
        Optional<Reuniao> reuniaoOptional = reuniaoRepository.findById(idReuniao);
        Optional<Colegiado> colegiadoOptional = colegiadoRepository.findById(idColegiado);

        if (reuniaoOptional.isPresent() && colegiadoOptional.isPresent()) {
            Reuniao reuniao = reuniaoOptional.get();
            Colegiado colegiado = colegiadoOptional.get();
            reuniao.setColegiado(colegiado);
            reuniaoRepository.save(reuniao);
        } else {
            throw new RuntimeException("Reunião ou Colegiado não encontrado");
        }
    }

    public void adicionarProcesso(Long idReuniao, Long idProcesso) {
        Optional<Reuniao> reuniaoOptional = reuniaoRepository.findById(idReuniao);
        Optional<Processo> processoOptional = processoRepository.findById(idProcesso);

        if (reuniaoOptional.isPresent() && processoOptional.isPresent()) {
            Reuniao reuniao = reuniaoOptional.get();
            Processo processo = processoOptional.get();

            reuniao.getProcessos().add(processo);

            reuniaoRepository.save(reuniao);
        } else {
            throw new RuntimeException("Reunião ou Processo não encontrado");
        }
    }

    public List<Reuniao> listarReunioes(){
        return this.reuniaoRepository.findAll();
    };


    public List<Reuniao> listarReuniaoPorStatusOuColegiado(List<StatusReuniao> status, Long idColegiado){
        Optional<Colegiado> colegiadoOptional = colegiadoRepository.findById(idColegiado);
        if (colegiadoOptional.isPresent()){
            return reuniaoRepository.findAllByStatusInAndColegiado(status, colegiadoOptional.get());
        } else {
            throw new RuntimeException("Colegiado não encontrado");
        }
    }

    public void iniciarReuniao(Long idReuniao){
        if(reuniaoRepository.findByStatus(StatusReuniao.INICIADA).isEmpty()){
            Optional<Reuniao> reuniaoOptional = reuniaoRepository.findById(idReuniao);

            if (reuniaoOptional.isPresent()) {
                Reuniao reuniao = reuniaoOptional.get();
                reuniao.setStatus(StatusReuniao.INICIADA);
                reuniaoRepository.save(reuniao);
            } else {
                throw new RuntimeException("Reunião não encontrada");
            }
        }else {
            throw new RuntimeException("Já existe Reunião em andamento");
        }
    }

    public void encerrarReuniao(Long idReuniao){
        Optional<Reuniao> reuniaoOptional = reuniaoRepository.findById(idReuniao);

        if (reuniaoOptional.isPresent()) {
            Reuniao reuniao = reuniaoOptional.get();
            if(reuniao.getStatus().equals(StatusReuniao.INICIADA)){
                reuniao.setStatus(StatusReuniao.ENCERRADA);
                reuniaoRepository.save(reuniao);
            }
        } else {
            throw new RuntimeException("Reunião não encontrada");
        }
    }

    public List<Reuniao> reunioesdocolegiado (Long idColegiado){
        return reuniaoRepository.listareuniaocolegiado(idColegiado);
    }

    public List<Reuniao> filtrarreuniao (StatusReuniao status, Long idcolegiado){
        System.out.println(status);

        if (status == null) {
            return reunioesdocolegiado(idcolegiado);
        }

        return reuniaoRepository.filtrarStatus(status, idcolegiado);
    }
}
