package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastraNovoProcesso(Processo processo){
        processo.setNumero("" + System.currentTimeMillis());
        Date dataRecepcao = new Date();

        Aluno aluno = alunoRepository.findById(52L).get();
        aluno.addProcesso(processo);

        processo.setStatus(StatusEnum.CRIADO);

        processo.setTipoDecisao(null);

        processo.setInteressado(aluno);

        processo.setDataRecepcao(dataRecepcao);
        processoRepository.save(processo);
    }
    
    
}
