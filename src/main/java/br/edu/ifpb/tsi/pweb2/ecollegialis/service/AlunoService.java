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

    // função que gera um número de processo aleatório
    private String gerarNumeroProcesso() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataFormatada = dateFormat.format(new Date());

        int parteAleatoria = (int) (Math.random() * 1000);
        String numeroProcesso = dataFormatada + parteAleatoria;

        return "P" + numeroProcesso;
    }

    // função que consulta todos os processos de um aluno
    public List<Processo> consultaProcessos(Long id){
        return processoRepository.findAllById(id);
    }

    // função que consulta um processo pelo seu número
    public Processo consultarProcessoPorNumero(String numeroProcesso) {
        return processoRepository.findByNumero(numeroProcesso);
    }

    // função que consulta os processos de um aluno que possuem um determinado status
    public List<Processo> consultaProcessosPorStatus(Long idAluno, StatusProcesso status) {
        return processoRepository.findByIdAndStatus(idAluno, status);
    }

    // função que consulta os processos de um aluno que possuem um determinado assunto
    public List<Processo> consultaProcessosPorAssunto(Long idAluno, Long id){
        return processoRepository.findAllByInteressadoIdAndAssuntoId(idAluno, id);
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }

}
