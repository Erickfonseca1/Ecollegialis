package br.edu.ifpb.tsi.pweb2.ecollegialis.services;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;
    private final AlunoService alunoService; // Se você ainda não criou, pode criar um serviço de Aluno

    @Autowired
    public ProcessoService(ProcessoRepository processoRepository, AlunoService alunoService) {
        this.processoRepository = processoRepository;
        this.alunoService = alunoService;
    }

    public Processo cadastrarNovoProcesso(Aluno aluno, Assunto assunto, String textoRequerimento) {
        Processo processo = new Processo();
        processo.setAluno(aluno);
        processo.setAssunto(assunto);
        processo.setTextoRequerimento(textoRequerimento);
        return processoRepository.save(processo);
    }
}

