package br.ufma.filafacil.patterns.factory;

import br.ufma.filafacil.model.Senha;

// PADRAO FACTORY METHOD - o "produto" criado pela fabrica.
// Cada tipo de notificacao (console, email...) implementa esta interface.
public interface Notificacao {

    void enviar(Senha senha, String mensagem);
}
