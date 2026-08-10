package br.ufma.filafacil.patterns.observer;

import br.ufma.filafacil.model.Senha;

// OBSERVER - observador que simula o painel/telao do local.
// So reage quando uma senha e chamada, mostrando o aviso ao cliente.
public class ObservadorPainel implements ObservadorSenha {

    @Override
    public void aoOcorrerEvento(String tipoEvento, Senha senha) {
        if (tipoEvento.equals("CHAMADA")) {
            System.out.println("[PAINEL] Senha " + senha.getNumero()
                    + " - " + senha.getNomeCliente()
                    + ", dirija-se ao atendimento.");
        }
    }
}
