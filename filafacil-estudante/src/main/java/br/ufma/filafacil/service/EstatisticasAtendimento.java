package br.ufma.filafacil.service;

// DTO imutavel com os resultados analiticos do tempo de espera e atendimento.
public class EstatisticasAtendimento {

    private final int totalCriadas;
    private final int totalChamadas;
    private final int totalFinalizadas;
    private final int totalCanceladas;

    private final double tempoMedioEsperaSegundos;
    private final double tempoMedioAtendimentoSegundos;

    private final double tempoMedioEsperaNormalSegundos;
    private final double tempoMedioEsperaPrioritariaSegundos;

    private final int atendidosGeral;
    private final int atendidosFinanceiro;
    private final int atendidosTecnico;

    public EstatisticasAtendimento(int totalCriadas, int totalChamadas, int totalFinalizadas, int totalCanceladas,
                                   double tempoMedioEsperaSegundos, double tempoMedioAtendimentoSegundos,
                                   double tempoMedioEsperaNormalSegundos, double tempoMedioEsperaPrioritariaSegundos,
                                   int atendidosGeral, int atendidosFinanceiro, int atendidosTecnico) {
        this.totalCriadas = totalCriadas;
        this.totalChamadas = totalChamadas;
        this.totalFinalizadas = totalFinalizadas;
        this.totalCanceladas = totalCanceladas;
        this.tempoMedioEsperaSegundos = tempoMedioEsperaSegundos;
        this.tempoMedioAtendimentoSegundos = tempoMedioAtendimentoSegundos;
        this.tempoMedioEsperaNormalSegundos = tempoMedioEsperaNormalSegundos;
        this.tempoMedioEsperaPrioritariaSegundos = tempoMedioEsperaPrioritariaSegundos;
        this.atendidosGeral = atendidosGeral;
        this.atendidosFinanceiro = atendidosFinanceiro;
        this.atendidosTecnico = atendidosTecnico;
    }

    public int getTotalCriadas() { return totalCriadas; }
    public int getTotalChamadas() { return totalChamadas; }
    public int getTotalFinalizadas() { return totalFinalizadas; }
    public int getTotalCanceladas() { return totalCanceladas; }

    public double getTempoMedioEsperaSegundos() { return tempoMedioEsperaSegundos; }
    public double getTempoMedioAtendimentoSegundos() { return tempoMedioAtendimentoSegundos; }

    public double getTempoMedioEsperaNormalSegundos() { return tempoMedioEsperaNormalSegundos; }
    public double getTempoMedioEsperaPrioritariaSegundos() { return tempoMedioEsperaPrioritariaSegundos; }

    public int getAtendidosGeral() { return atendidosGeral; }
    public int getAtendidosFinanceiro() { return atendidosFinanceiro; }
    public int getAtendidosTecnico() { return atendidosTecnico; }
}
