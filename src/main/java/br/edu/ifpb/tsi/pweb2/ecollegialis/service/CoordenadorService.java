package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CoordenadorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Processo> listarTodosProcessosDoColegiado(Long id) {

        Professor coordenador = professorRepository.findById(id).get();
        return processoRepository.findAllByCursoId(coordenador.getCurso().getId());

    }

    public List<Professor> listarTodosProfessoresDoColegiado(Long id) {

        Professor coordenador = professorRepository.findById(id).get();
        Colegiado colegiado = colegiadoRepository.findByCursoId(coordenador.getCurso().getId());
        return colegiado.getMembros();

    }

    public List<Aluno> listarTodosAlunosProcesso(Long id) {
        return alunoRepository.findAllByAlunoAndProcessDistinct(id);


    }

    public List<Processo> filtro(Long cursoId, String filtro) {
        if (filtro.isBlank()) {
            return processoRepository.findAllByCursoId(cursoId);


        }
        try {
            Long usuarioId = Long.parseLong(filtro);

            Optional<Professor> professor = professorRepository.findById(usuarioId);

            if (professor.isPresent()) {
                return processoRepository.findAllByRelatorId(professor.get().getId());
            } else {
                return processoRepository.findAllByInteressadoId(usuarioId);
            }

        } catch (NumberFormatException e) {
            StatusEnum filtroEnum = StatusEnum.valueOf(filtro);
            return processoRepository.findAllByCursoIdAndStatus(cursoId, filtroEnum);

        }

    }

    public Processo getProcesso(Long idProcesso) {
        return processoRepository.findById(idProcesso).get();
    }

    public void salvarProcesso(Processo processo) {
        Processo processoBD = processoRepository.findById(processo.getId()).get();
        processoBD.setRelator(processo.getRelator());
        processoBD.setStatus(StatusEnum.DISTRIBUIDO);
        processoBD.setDataDistribuicao(new Date());
        processoRepository.save(processoBD);
    }
}
