package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusEnum;
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

        processo.setStatus(StatusEnum.CRIADO);
        processo.setTipoDecisao(null);
        processo.setInteressado(aluno);
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

    public List<Processo> consultaProcessosPorAssunto(Long idInteressado){
        return processoRepository.findByInteressado(idInteressado);
    }

    public List<Processo> consultaProcessosPorStatus(Long idInteressado, StatusEnum status) {
        return processoRepository.findByInteressadoAndStatus(idInteressado, status);
    }

    @Transactional
    public void adicionarAnexo(Processo processo, byte[] anexo) {
        processo.addAnexos(anexo);
        processoRepository.save(processo);
    }

}