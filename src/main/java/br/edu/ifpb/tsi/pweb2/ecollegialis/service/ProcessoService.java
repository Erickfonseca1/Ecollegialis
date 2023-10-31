package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssuntoService assuntoService;

    public Processo criarProcesso(Processo processo){
        var assunto = assuntoService.findById(processo.getAssunto().getId());
        var aluno = alunoService.findById(processo.getAluno().getId());
        processo.setStatus(StatusEnum.CRIADO);
        return processoRepository.save(new Processo());
    }

    public Processo atribuirProcesso(Processo processo, Long idProfessor){
        var professor = professorService.findById(idProfessor);
        processo.setProfessor(professor);
        processo.setStatus(StatusEnum.DISTRIBUIDO);
        return processoRepository.save(processo);
    }

    public List<Processo> findAllProcessos() {
        return processoRepository.findAll();
    }

    public List<Processo> findAllProcessoWithAluno(Aluno aluno) {
        return processoRepository.findAllByAluno(aluno);
    }

    public Processo findAllProcessoById(Long id){
        return this.processoRepository.findById(id).orElse(null);
    }

    public List<Processo> findAllProcessoWithProfessor(Professor professor) {
        return processoRepository.findByProfessorRelator(professor);
    }

}
