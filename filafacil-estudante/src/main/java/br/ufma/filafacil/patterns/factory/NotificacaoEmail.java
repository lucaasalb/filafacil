package br.ufma.filafacil.patterns.factory;

import br.ufma.filafacil.model.Senha;

// FACTORY METHOD - produto concreto: notificacao por email (simulada).
// Aqui apenas imprimimos no console para simular o envio de um email.
public class NotificacaoEmail implements Notificacao {

    @Override
    public void enviar(Senha senha, String mensagem) {
        System.out.println("[EMAIL] Para: " + senha.getNomeCliente()
                + " | Assunto: Sua senha foi chamada | " + mensagem);
    }
}
