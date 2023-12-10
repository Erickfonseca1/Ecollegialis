package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Reuniao salvarReuniao(Reuniao reuniao){
        return this.reuniaoRepository.save(reuniao);
    }

    public void apagarReuniao(Long id){
        this.reuniaoRepository.deleteById(id);
    }


}
