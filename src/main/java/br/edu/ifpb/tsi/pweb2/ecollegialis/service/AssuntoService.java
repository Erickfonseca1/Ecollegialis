package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuntoService {
    @Autowired
    private AssuntoRepository assuntoRepositorio;

   //CRUDS

    @Transactional
    public void criarAssunto(Assunto assunto){
        assuntoRepositorio.save(assunto);
    }
    public List<Assunto> listarAssuntos(){
        return assuntoRepositorio.findAll();
    }

    public Assunto buscarAssuntoPorNome(String nome){
        return assuntoRepositorio.findByNome(nome);
    }

    @Transactional
    public void deletarAssuntoPorNome(String nome){
        Assunto assunto = assuntoRepositorio.findByNome(nome);
        assuntoRepositorio.delete(assunto);
    }

   @Transactional
    public Assunto atualizarAssunto(Assunto assuntoAtualizado){
        Assunto assuntoExistente = assuntoRepositorio.findById(assuntoAtualizado.getId()).orElse(null);
        if(assuntoExistente == null){
            throw new IllegalArgumentException("Assunto não encontrado");
        }
        assuntoExistente.setNome(assuntoAtualizado.getNome());
        assuntoExistente.setNome(assuntoAtualizado.getNome());
        return assuntoRepositorio.save(assuntoExistente);
   }

}