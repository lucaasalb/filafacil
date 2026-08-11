package br.ufma.filafacil.patterns.observer;

import br.ufma.filafacil.model.Prioridade;
import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.model.TipoServico;
import br.ufma.filafacil.service.EstatisticasAtendimento;

import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// OBSERVER - Observador responsavel por coletar dados estatisticos e metricas
// do sistema sem acoplar a logica no SenhaService.
public class ObservadorMetricas implements ObservadorSenha {

    private List<Long> temposEsperaSegundos = new ArrayList<>();
    private List<Long> temposAtendimentoSegundos = new ArrayList<>();

    private Map<Prioridade, List<Long>> temposEsperaPorPrioridade = new EnumMap<>(Prioridade.class);
    private Map<TipoServico, Integer> totalAtendidosPorServico = new EnumMap<>(TipoServico.class);

    private int totalCriadas = 0;
    private int totalChamadas = 0;
    private int totalFinalizadas = 0;
    private int totalCanceladas = 0;

    public ObservadorMetricas() {
        for (Prioridade p : Prioridade.values()) {
            temposEsperaPorPrioridade.put(p, new ArrayList<>());
        }
        for (TipoServico t : TipoServico.values()) {
            totalAtendidosPorServico.put(t, 0);
        }
    }

    @Override
    public synchronized void aoOcorrerEvento(String tipoEvento, Senha senha) {
        if ("CRIADA".equals(tipoEvento)) {
            totalCriadas++;
        } else if ("CHAMADA".equals(tipoEvento)) {
            totalChamadas++;
            if (senha.getCriadaEm() != null && senha.getChamadaEm() != null) {
                long segundosEspera = Duration.between(senha.getCriadaEm(), senha.getChamadaEm()).toSeconds();
                temposEsperaSegundos.add(segundosEspera);
                temposEsperaPorPrioridade.get(senha.getPrioridade()).add(segundosEspera);
            }
        } else if ("FINALIZADA".equals(tipoEvento)) {
            totalFinalizadas++;
            if (senha.getChamadaEm() != null && senha.getFinalizadaEm() != null) {
                long segundosAtendimento = Duration.between(senha.getChamadaEm(), senha.getFinalizadaEm()).toSeconds();
                temposAtendimentoSegundos.add(segundosAtendimento);
            }
            TipoServico ts = senha.getTipoServico();
            totalAtendidosPorServico.put(ts, totalAtendidosPorServico.getOrDefault(ts, 0) + 1);
        } else if ("CANCELADA".equals(tipoEvento)) {
            totalCanceladas++;
        }
    }

    public synchronized EstatisticasAtendimento gerarEstatisticas() {
        double tempoMedioEspera = calcularMedia(temposEsperaSegundos);
        double tempoMedioAtendimento = calcularMedia(temposAtendimentoSegundos);

        double esperaNormal = calcularMedia(temposEsperaPorPrioridade.get(Prioridade.NORMAL));
        double esperaPrioritaria = calcularMedia(temposEsperaPorPrioridade.get(Prioridade.PRIORITARIA));

        int atendidosGeral = totalAtendidosPorServico.getOrDefault(TipoServico.GERAL, 0);
        int atendidosFinanceiro = totalAtendidosPorServico.getOrDefault(TipoServico.FINANCEIRO, 0);
        int atendidosTecnico = totalAtendidosPorServico.getOrDefault(TipoServico.TECNICO, 0);

        return new EstatisticasAtendimento(
                totalCriadas,
                totalChamadas,
                totalFinalizadas,
                totalCanceladas,
                tempoMedioEspera,
                tempoMedioAtendimento,
                esperaNormal,
                esperaPrioritaria,
                atendidosGeral,
                atendidosFinanceiro,
                atendidosTecnico
        );
    }

    private double calcularMedia(List<Long> lista) {
        if (lista == null || lista.isEmpty()) {
            return 0.0;
        }
        long soma = 0;
        for (Long val : lista) {
            soma += val;
        }
        return (double) soma / lista.size();
    }
}
