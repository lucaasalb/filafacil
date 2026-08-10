package br.ufma.filafacil.patterns.observer;

import br.ufma.filafacil.model.Senha;

import java.util.ArrayList;
import java.util.List;

// OBSERVER - o "sujeito" que guarda os observadores e avisa todos eles.
// O servico usa esta classe para notificar quando uma senha muda de estado,
// sem precisar conhecer quem sao os observadores.
public class PublicadorEventos {

    private List<ObservadorSenha> observadores = new ArrayList<>();

    public void inscrever(ObservadorSenha observador) {
        observadores.add(observador);
    }

    public void notificar(String tipoEvento, Senha senha) {
        for (ObservadorSenha observador : observadores) {
            observador.aoOcorrerEvento(tipoEvento, senha);
        }
    }
}
