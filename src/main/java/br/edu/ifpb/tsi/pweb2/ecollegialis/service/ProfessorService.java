package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
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

    //CRUDS
    @Transactional
    public void criarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    public List<Professor> listarProfessores(){
        return professorRepository.findAll();
    }
    public Professor buscarProfessorPorId(Long id){
        return professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
    }

    @Transactional
    public void deletarProfessor(Long id){
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        professorRepository.delete(professor);
    }

    @Transactional
    public Professor atualizarProfessor(Professor professorAtualizado) {
        Professor professorExistente = professorRepository.findById(professorAtualizado.getId()).orElse(null);
        if (professorExistente == null) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        professorExistente.setNome(professorAtualizado.getNome());
        professorExistente.setCurso(professorAtualizado.getCurso());
        return professorRepository.save(professorExistente);
    }

    //Criar metodo que faça a votação
    @Transactional
    public void votar(Long id, String voto, String justificativa) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));

        TipoDecisao tipoDecisao;

        switch (voto.toLowerCase()) {
            case "deferir":
                tipoDecisao = TipoDecisao.DEFERIDO;
                break;
            case "indeferir":
                tipoDecisao = TipoDecisao.INDEFERIDO;
                break;
            case "pendente":
                tipoDecisao = TipoDecisao.PENDENTE;
                break;
            default:
                throw new IllegalArgumentException("Voto inválido: " + voto);
        }

        processo.setTipoDecisao(tipoDecisao);
        processo.setTextoRelator(justificativa);
        processoRepository.save(processo);
    }

    public List<Reuniao> listarReunioes(Usuario professor) {
        return reuniaoRepository.AllReunioesByProfessorAndColegiado(professor.getId());

    }

    public List<Reuniao> listarReunioesByStatus(Usuario professor, StatusReuniao status) {
        if (status.equals(StatusReuniao.EM_ANDAMENTO)) {
            return listarReunioes(professor);
        }
        return reuniaoRepository.AllReunioesByProfessorAndColegiadoAndStatus(professor.getId(), status);
    }

}