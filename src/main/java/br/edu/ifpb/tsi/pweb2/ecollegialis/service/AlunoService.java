package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
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

    //CRUDS

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarAlunosPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
    }

    @Transactional
    public Aluno criarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @Transactional
    public void removerAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        alunoRepository.delete(aluno);
    }

    @Transactional
    public Aluno atualizarAluno(Aluno alunoAtualizado) {
        Aluno alunoExistente = alunoRepository.findById(alunoAtualizado.getId()).orElse(null);
        if (alunoExistente == null) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        alunoExistente.setNome(alunoAtualizado.getNome());
        alunoExistente.setFone(alunoAtualizado.getFone());
        alunoExistente.setMatricula(alunoAtualizado.getMatricula());
        alunoExistente.setLogin(alunoAtualizado.getLogin());
        alunoExistente.setSenha(alunoAtualizado.getSenha());
        return alunoRepository.save(alunoExistente);
    }
}