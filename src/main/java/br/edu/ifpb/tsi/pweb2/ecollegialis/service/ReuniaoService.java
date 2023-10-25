package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import java.util.List;
import java.util.Optional;


@Service
public class ReuniaoService {

    @Autowired
    ReuniaoRepository reuniaoRepository;

    @Autowired
    ColegiadoRepository colegiadoRepository;

    @Autowired
    ProcessoRepository processoRepository;


    public List<Reuniao> listarReunioes() {
        return reuniaoRepository.findAll();
    }

    public Reuniao buscarReuniaoPorId(Long id) {
        return reuniaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reuniao não encontrada"));
    }

    @Transactional
    public Reuniao criarReuniao(Reuniao reuniao) {

        return reuniaoRepository.save(reuniao);
    }

    @Transactional
    public void deletarReuniao(Long id) {
        Reuniao reuniao = reuniaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reuniao não encontrada"));
        reuniaoRepository.delete(reuniao);
    }

    @Transactional
    public Reuniao atualizarReuniao(Reuniao reuniaoAtualizada, Long id) {
        Reuniao reuniao = reuniaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reuniao não encontrada"));
        reuniao.setDataReuniao(reuniaoAtualizada.getDataReuniao());
        reuniao.setStatus(reuniaoAtualizada.getStatus());
        reuniao.setAta(reuniaoAtualizada.getAta());
        reuniao.setProcessos(reuniaoAtualizada.getProcessos());
        reuniao.setColegiado(reuniaoAtualizada.getColegiado());
        return reuniaoRepository.save(reuniao);
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

}