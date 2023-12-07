package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Usuario;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProfessorRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Processo> listarProcessosDesignados(Usuario professor){
        return processoRepository.findAllByRelatorId(professor.getId());
    }

    public void save(Professor professor) {
        professorRepository.save(professor);
    }

    public void update(Professor professor) {
        professorRepository.save(professor);
    }


    public Professor findById(Long id) {
        return professorRepository.findById(id).get();
    }
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public void deleteById(Long id) {
        professorRepository.deleteById(id);
    }

    @Transactional
    public void votar(Processo processo) {
        Processo processpBD = processoRepository.findById(processo.getId()).get();
        processpBD.setTipoDecisao(processo.getTipoDecisao());
        processpBD.setTextoRelator(processo.getTextoRelator());
        processoRepository.save(processpBD);

    }


    public List<Reuniao> listarReunioes(Usuario professor) {
        return reuniaoRepository.AllReunioesByProfessorAndColegiado(professor.getId());

    }

    public List<Reuniao> listarReunioesByStatus(Usuario professor, StatusReuniao status) {
        if (status.equals(StatusReuniao.SEM_FILTRO)) {
            return listarReunioes(professor);
        }
        return reuniaoRepository.AllReunioesByProfessorAndColegiadoAndStatus(professor.getId(), status);
    }


    public Processo buscarProcesso(Long id) {
        return processoRepository.findById(id).get();
    }


    @Transactional
    public void votar(Long id, String voto, String justificativa) {
        Processo processo = processoRepository.findById(id).get();
        TipoDecisao tipoDecisao = null;
        if (voto.equals("deferir")) {
            tipoDecisao = TipoDecisao.DEFERIMENTO;
        }
        else {
            tipoDecisao = TipoDecisao.INDEFERIMENTO;
        }

        processo.setTipoDecisao(tipoDecisao);
        processo.setTextoRelator(justificativa);
        processoRepository.save(processo);

    }
}
