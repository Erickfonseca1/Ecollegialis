package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Voto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;

import java.util.List;

@Service
public class ProfessorService {
    
    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    public List<Reuniao> listarReuniao(){
        return reuniaoRepository.findAll();
    }

    public List<Processo> listarProcessos() {
        return processoRepository.findAll();
    }

    // buscar professor por ID
    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id).get();
    }


    @Transactional
    public void votar(Long id, String voto, String justificativa) {
        Processo processo = processoRepository.findById(id).get();
        TipoDecisao tipoDecisao = null;
        if (voto.equals("deferir")) {
            tipoDecisao = TipoDecisao.DEFERIDO;
        }
        else {
            tipoDecisao = TipoDecisao.INDEFERIDO;
        }

        processo.setTipoDecisao(tipoDecisao);
        processo.setTextoRelator(justificativa);
        processoRepository.save(processo);

    }

    public boolean login(Professor professor) {
        Professor professorBanco = professorRepository.findByMatricula(professor.getMatricula());
        
        boolean is_valid = false;
        
        if(professorBanco != null) {
            if (professor.getSenha().equals(professorBanco.getSenha())) {
                is_valid = true;
            }
        }
        return is_valid;
    }

    public void votar(Voto voto) {
        votoRepository.save(voto);
    }
}
