package br.ufma.filafacil.patterns.strategy;

import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.model.StatusSenha;

import java.util.List;

// STRATEGY - implementacao FIFO (primeiro a chegar, primeiro a ser atendido).
// Escolhe a senha aguardando com o menor numero, ou seja, a mais antiga.
public class EstrategiaFifo implements EstrategiaFila {

    @Override
    public Senha escolherProxima(List<Senha> senhas) {
        Senha escolhida = null;

        for (Senha senha : senhas) {
            if (senha.getStatus() == StatusSenha.AGUARDANDO) {
                if (escolhida == null || senha.getNumero() < escolhida.getNumero()) {
                    escolhida = senha;
                }
            }
        }

        return escolhida;
    }
}
