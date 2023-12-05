package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void cadastrarProcesso(Long idAluno, Processo idProcesso) {
        idProcesso.setNumero(idProcesso.getNumero() + 1);
        Date dataRecepcao = new Date();

        Aluno aluno = alunoRepository.findById(idAluno).get();
        aluno.adicionarProcesso(idProcesso);

        idProcesso.setEstadoProcesso(StatusEnum.CRIADO);
        idProcesso.setTipoDecisao(null);
        idProcesso.setAlunoProcesso(aluno);
        idProcesso.setDataCriacao(dataRecepcao);
        processoRepository.save(idProcesso);
    }

    public List<Aluno> findAlunosWithProcesso() {
        List<Aluno> alunos = new ArrayList<>();
        for (Aluno aluno : alunoRepository.findAll()) {
            if (!aluno.getListaProcessos().isEmpty()) {
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    // CRUDS

    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno findById(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public void update(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public void deleteById(Long id) {
        alunoRepository.deleteById(id);
    }
}
