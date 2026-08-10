package br.ufma.filafacil.patterns.observer;

import br.ufma.filafacil.model.Senha;

// PADRAO OBSERVER
// Interface que todo observador precisa implementar.
// Quando algo acontece com uma senha, o metodo abaixo e chamado.
public interface ObservadorSenha {

    void aoOcorrerEvento(String tipoEvento, Senha senha);
}
