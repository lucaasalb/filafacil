// Lógica do Dashboard de Gestão & Métricas - FilaFácil (Ana Luiza)

async function carregarMetricas() {
    try {
        const resposta = await fetch('/api/metricas');
        if (!resposta.ok) return;

        const dados = await resposta.json();
        atualizarDashboard(dados);
    } catch (e) {
        console.error("Erro ao carregar métricas analíticas:", e);
    }
}

function atualizarDashboard(dados) {
    // 1. Formatar Tempos Médios (de segundos para texto amigável ex: 02m 15s ou 45s)
    document.getElementById('tempoEspera').innerText = formatarSegundos(dados.tempoMedioEsperaSegundos);
    document.getElementById('tempoAtendimento').innerText = formatarSegundos(dados.tempoMedioAtendimentoSegundos);

    // 2. Total de Senhas Finalizadas
    document.getElementById('totalAtendidas').innerText = dados.totalFinalizadas;

    // 3. Taxa de Cancelamento
    const totalEncerradas = dados.totalFinalizadas + dados.totalCanceladas;
    let taxaCancelamento = 0;
    if (totalEncerradas > 0) {
        taxaCancelamento = ((dados.totalCanceladas / totalEncerradas) * 100).toFixed(1);
    }
    document.getElementById('taxaCancelamento').innerText = `${taxaCancelamento}%`;

    // 4. Comparativo por Prioridade
    document.getElementById('esperaPrioritaria').innerText = formatarSegundos(dados.tempoMedioEsperaPrioritariaSegundos);
    document.getElementById('esperaNormal').innerText = formatarSegundos(dados.tempoMedioEsperaNormalSegundos);

    // Barras de progresso proporcionais
    const maxEspera = Math.max(dados.tempoMedioEsperaNormalSegundos, dados.tempoMedioEsperaPrioritariaSegundos, 1);
    const pctPrioritaria = Math.min(100, Math.round((dados.tempoMedioEsperaPrioritariaSegundos / maxEspera) * 100));
    const pctNormal = Math.min(100, Math.round((dados.tempoMedioEsperaNormalSegundos / maxEspera) * 100));

    document.getElementById('barPrioritaria').style.width = `${pctPrioritaria}%`;
    document.getElementById('barNormal').style.width = `${pctNormal}%`;

    // 5. Contagem por Tipo de Serviço
    document.getElementById('countTecnico').innerText = dados.atendidosTecnico;
    document.getElementById('countFinanceiro').innerText = dados.atendidosFinanceiro;
    document.getElementById('countGeral').innerText = dados.atendidosGeral;
}

function formatarSegundos(totalSegundos) {
    if (!totalSegundos || totalSegundos <= 0) return '0s';
    const min = Math.floor(totalSegundos / 60);
    const seg = Math.round(totalSegundos % 60);

    if (min === 0) {
        return `${seg}s`;
    }
    return `${min}m ${seg}s`;
}

// Inicia busca das métricas a cada 3 segundos
setInterval(carregarMetricas, 3000);
carregarMetricas();
