package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ColegiadoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ReuniaoRepository;

import java.util.Date;
import java.util.List;

@Service
public class CoordenadorService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ColegiadoRepository colegiadoRepository;

    @Autowired
    private ReuniaoRepository reuniaoRepository;

    public List<Processo> listarTodosProcessosDoColegiado(Professor coordenador, Colegiado colegiado) {
        if (coordenador.isCoordenador()) {
            return processoRepository.findAllByColegiado(colegiado.getId());
        } else {
            throw new RuntimeException("Oops! Parece que você não tem permissão para acessar essa consulta. Apenas coordenadores podem realizá-la.");
        }
    }

    public List<Processo> listarTodosProcessosDoColegiadoPorStatus(Professor coordenador, Colegiado colegiado, StatusProcesso status) {
        if (coordenador.isCoordenador()) {
            return processoRepository.findAllByColegiadoAndStatus(colegiado.getId(), status);
        } else {
            throw new RuntimeException("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }

    public List<Processo> listarTodosProcessosDoColegiadoPorAluno(Professor coordenador, Colegiado colegiado, Aluno interessado) {
        if (coordenador.isCoordenador()) {
            return processoRepository.findAllByColegiadoAndInteressado(colegiado.getId(), interessado.getId());
        } else {
            throw new RuntimeException("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }

    public List<Processo> listarTodosProcessosDoColegiadoPorRelator(Professor coordenador, Colegiado colegiado, Professor relator) {
        if (coordenador.isCoordenador()) {
            return processoRepository.findAllByColegiadoAndRelator(colegiado.getId(), relator.getId());
        } else {
            throw new RuntimeException("Este professor não pode realizar esta consulta, pois não é coordenador!");
        }
    }

    @Transactional
    public void distribuirProcessoParaProfessorRelator(Professor coordenador, Professor relator, Processo processo) {
        if (coordenador.isCoordenador()) {
            if (processo.getRelator() == null) {
                processo.setRelator(relator);
            } else {
                throw new RuntimeException("Ops! Este processo já tem um relator designado. Não é possível atribuir outro.");
            }
            processoRepository.save(processo);
        } else {
            throw new RuntimeException("Oops! Você não tem permissão para executar esta ação. Apenas coordenadores podem distribuir processos.");
        }
    }

    @Transactional
    public void criarReuniao(List<Processo> processos, Date data, Colegiado colegiado) {
        Reuniao reuniao = new Reuniao(data, StatusReuniao.PROGRAMADA, colegiado);

        for (Processo processo : processos) {
            reuniao.addProcesso(processo);
        }

        colegiado.addReuniao(reuniao);

        colegiadoRepository.save(colegiado);

        reuniaoRepository.save(reuniao);
    }

    @Transactional
    public void iniciarSessao(Reuniao reuniao) {
        List<Reuniao> reunioesEmAndamento = reuniaoRepository.findByStatus(StatusReuniao.EM_ANDAMENTO);

        if (reunioesEmAndamento.isEmpty()) {
            reuniao.setStatus(StatusReuniao.EM_ANDAMENTO);
            reuniaoRepository.save(reuniao);
        } else {
            throw new RuntimeException("Oops! Já existe uma reunião em andamento. Não é possível iniciar uma nova enquanto a atual estiver em progresso.");
        }
    }
}
