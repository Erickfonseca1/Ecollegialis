package br.edu.ifpb.tsi.pweb2.ecollegialis.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subject {

    private List<AbstractObserver> observers = new ArrayList<>();

    @Autowired
    public Subject(AlunoObserver alunoObserver, CoordenadorObserver coordenadorObserver, ProfessorObserver professorObserver) {
        attach(alunoObserver);
        attach(coordenadorObserver);
        attach(professorObserver);
    }

    public void attach(AbstractObserver observer) {
        observers.add(observer);
    }

    public void detach(AbstractObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (AbstractObserver observer : observers) {
            observer.update(message);
        }
    }

    public List<AbstractObserver> getObservers() {
        return observers;
    }

    public String obterMensagemAtualizada() {
        return null;
    }

}