package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.util.ArrayList;
import java.util.Date;

public class Colegiado {
    private ArrayList<Professor> professores;
    private ArrayList<Reuniao> reunioes;

    public ArrayList<Reuniao> getReunioes() {
        return reunioes;
    }

    public void julgarProcesso(String assunto) throws Exception{
        Reuniao reuniaoDoJulgamento = null;
        Date dataDeHoje = new Date();
        Processo processo = null;
        for(Reuniao reuniao :reunioes){
            if(reuniao.getDataReuniao() == dataDeHoje){
                reuniaoDoJulgamento = reuniao;
            }
        }
        for(Processo processoDaReuniao : reuniaoDoJulgamento.getProcessos()){
            if(processoDaReuniao.getAssunto().getNome() == assunto){
                processo = processoDaReuniao;
            }
        }
        if(processo == null){
            throw new Exception("Processo não encontrado");
        }
        //Como será a decisão do relator
        Boolean decisaoDoRelator = null;
        //garantir o primeiro voto do relator (observaçao feita após a criação do seguinte código)
        for(Professor professor : professores){
            if(professor.getRelator() == true){
                professor.julgamentoDoRelator(processo, decisaoDoRelator);
            } else if(professor.getCoordenador() == false && professor.getRelator() == false){
                Voto voto = new Voto(null, null);
                professor.julgarProcesso(processo, voto);
            }
        }

    }

    public void setReunioes(ArrayList<Reuniao> reunioes) {
        this.reunioes = reunioes;
    }

    public ArrayList<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(ArrayList<Professor> professores) {
        this.professores = professores;
    }
    
}
