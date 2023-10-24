package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusProcesso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastrarNovoProcesso(Processo processo) {
        String numeroProcesso = gerarNumeroProcesso();
        processo.setNumero(numeroProcesso);

        Date dataRecepcao = new Date();

        Aluno aluno = alunoRepository.findById(52L).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        aluno.addProcesso(processo);

        processo.setStatus(StatusProcesso.CRIADO);
        processo.setTipoDecisao(null);
        processo.setAluno(aluno);
        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }

    private String gerarNumeroProcesso() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataFormatada = dateFormat.format(new Date());

        int parteAleatoria = (int) (Math.random() * 1000);
        String numeroProcesso = dataFormatada + parteAleatoria;

        return "P" + numeroProcesso;
    }

    public Processo consultarProcessoPorNumero(String numeroProcesso) {
        return processoRepository.findByNumero(numeroProcesso);
    }

    public List<Processo> consultaProcessosPorStatus(Long id, StatusProcesso status) {
        return processoRepository.findByIdAndStatus(id, status);
    }

    public List<Processo> consultaProcessosPorAssunto(Long idAluno, Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoId(idAluno, id);
    }

    public List<Processo> consultaProcessos(Long id){
        return processoRepository.findAllById(id);
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
                return consultaProcessosPorAssunto(id, assuntoId);
            }

        } catch (NumberFormatException e) {
            StatusProcesso filtroEnum = StatusProcesso.valueOf(filtro);
            if (order.isBlank()) {
                return consultaProcessosPorStatus(id, filtroEnum);
            }
            else {
                return consultaProcessosPorStatus(id, filtroEnum);
            }
        }
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }

}
