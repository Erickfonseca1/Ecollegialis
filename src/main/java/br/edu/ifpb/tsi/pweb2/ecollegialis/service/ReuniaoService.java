package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

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

}