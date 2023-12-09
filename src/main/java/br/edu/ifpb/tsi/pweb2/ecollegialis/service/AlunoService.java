package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovoProcesso(Long id, Processo processo){
        processo.setNumero("" + System.currentTimeMillis());
        Date dataRecepcao = new Date();

        Aluno aluno = alunoRepository.findById(id).get();
        aluno.addProcesso(processo);

        processo.setStatus(StatusEnum.CRIADO);

        processo.setTipoDecisao(null);

        processo.setAlunoProcesso(aluno);

        processo.setCurso(aluno.getCurso());

        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }

    public List<Processo> consultaProcessos(Long id){
        return processoRepository.findAllByalunoProcessoId(id);
    }


    public List<Processo> filtrarProcesso(Long id, String filtro, String order) {
        if (filtro.isBlank()) {
            return consultaProcessos(id);

        }
        try {
            Long assuntoId = Long.parseLong(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorAssunto(id, assuntoId);
            }
            else {
                return consultarProcessosPorAssuntoOrdenados(id, assuntoId);
            }

        } catch (NumberFormatException e) {
            StatusEnum filtroEnum = StatusEnum.valueOf(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorStatus(id, filtroEnum);
            }
            else {
                return consultarProcessosPorStatusOrdenados(id, filtroEnum);
            }
        }
    }

    public List<Processo> consultaProcessosPorAssunto(Long idAluno, Long id){
        return processoRepository.findAllByalunoProcessoIdAndAssuntoId(idAluno, id);
    }

    public List<Processo> consultaProcessosPorStatus(Long id, StatusEnum status) {
        return processoRepository.findAllByalunoProcessoIdAndStatus(id, status);

    }

    public List<Processo> consultarProcessosPorAssuntoOrdenados(Long idAluno, Long id){
        return processoRepository.findAllByalunoProcessoIdAndAssuntoIdOrderByDataRecepcaoDesc(idAluno, id);

    }

    public List<Processo> consultarProcessosPorStatusOrdenados(Long id, StatusEnum status){
        return processoRepository.findAllByalunoProcessoIdAndStatusOrderByDataRecepcaoDesc(id, status);



    }

    public List<Processo> consultarProcessosOrdenados(Aluno aluno){
        return processoRepository.findAllByalunoProcessoIdOrderByDataRecepcaoDesc(6L);
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }

    public void update(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public void save(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno findById(Long id) {
        return alunoRepository.findById(id).get();
    }

    public void delete(Long id) {
        alunoRepository.deleteById(id);
    }
    
    public void deleteById(Long id) {
        alunoRepository.deleteById(id);
    }
}
