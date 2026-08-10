package br.ufma.filafacil.service;

// Classe simples para levar os numeros do painel ate a tela.
// Guarda quantas senhas existem em cada situacao.
public class ResumoPainel {

    private int total;
    private int aguardando;
    private int chamadas;
    private int finalizadas;
    private int canceladas;

    public ResumoPainel(int total, int aguardando, int chamadas, int finalizadas, int canceladas) {
        this.total = total;
        this.aguardando = aguardando;
        this.chamadas = chamadas;
        this.finalizadas = finalizadas;
        this.canceladas = canceladas;
    }

    public int getTotal() {
        return total;
    }

    public int getAguardando() {
        return aguardando;
    }

    public int getChamadas() {
        return chamadas;
    }

    public int getFinalizadas() {
        return finalizadas;
    }

    public int getCanceladas() {
        return canceladas;
    }
}
