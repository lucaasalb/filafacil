// Lógica do Painel do Cliente (TV da Sala de Espera) - FilaFácil

let ultimaSenhaChamadaNumero = null;
let audioAtivo = false;
let audioContext = null;

// Relógio em tempo real
function atualizarRelogio() {
    const agora = new Date();
    const horas = String(agora.getHours()).padStart(2, '0');
    const minutos = String(agora.getMinutes()).padStart(2, '0');
    const segundos = String(agora.getSeconds()).padStart(2, '0');
    document.getElementById('relogio').innerText = `${horas}:${minutos}:${segundos}`;
}
setInterval(atualizarRelogio, 1000);
atualizarRelogio();

// Ativar Áudio (necessário interação inicial no navegador para liberar autoplay de som)
function ativarAudio() {
    if (!audioContext) {
        audioContext = new (window.AudioContext || window.webkitAudioContext)();
    }
    if (audioContext.state === 'suspended') {
        audioContext.resume();
    }
    audioAtivo = true;
    tocarChime();
    const btn = document.getElementById('btnAudio');
    btn.innerHTML = '✅ Áudio de Chamada Ativo';
    btn.style.background = 'rgba(34, 197, 94, 0.2)';
    btn.style.borderColor = '#22c55e';
    btn.style.color = '#4ade80';
}

// Emite um sinal sonoro (Chime) usando a Web Audio API nativa
function tocarChime() {
    if (!audioAtivo) return;
    try {
        if (!audioContext) {
            audioContext = new (window.AudioContext || window.webkitAudioContext)();
        }
        const now = audioContext.currentTime;
        
        // Primeira nota (La - 440Hz)
        const osc1 = audioContext.createOscillator();
        const gain1 = audioContext.createGain();
        osc1.type = 'sine';
        osc1.frequency.setValueAtTime(440, now);
        gain1.gain.setValueAtTime(0.3, now);
        gain1.gain.exponentialRampToValueAtTime(0.001, now + 0.6);
        osc1.connect(gain1);
        gain1.connect(audioContext.destination);
        osc1.start(now);
        osc1.stop(now + 0.6);

        // Segunda nota (Mi - 659.25Hz)
        const osc2 = audioContext.createOscillator();
        const gain2 = audioContext.createGain();
        osc2.type = 'sine';
        osc2.frequency.setValueAtTime(659.25, now + 0.25);
        gain2.gain.setValueAtTime(0.4, now + 0.25);
        gain2.gain.exponentialRampToValueAtTime(0.001, now + 1.2);
        osc2.connect(gain2);
        gain2.connect(audioContext.destination);
        osc2.start(now + 0.25);
        osc2.stop(now + 1.2);
    } catch (e) {
        console.log("Erro ao reproduzir áudio:", e);
    }
}

// Carrega as senhas da API nativa GET /api/senhas
async function carregarSenhas() {
    try {
        const resposta = await fetch('/api/senhas');
        if (!resposta.ok) return;
        
        const senhas = await resposta.json();
        atualizarPainel(senhas);
    } catch (erro) {
        console.error("Erro ao buscar senhas da API:", erro);
    }
}

function atualizarPainel(senhas) {
    // 1. Identificar a senha em atendimento (Status CHAMADA)
    // Pega a mais recente com status CHAMADA
    const senhasChamadas = senhas.filter(s => s.status === 'CHAMADA');
    
    // Ordena por horário de chamada (ou por número se empatar)
    senhasChamadas.sort((a, b) => b.numero - a.numero);
    const senhaAtual = senhasChamadas[0] || null;

    const containerDestaque = document.getElementById('conteudoSenhaAtual');
    const cardDestaque = document.getElementById('cardDestaque');

    if (senhaAtual) {
        // Se mudou a senha chamada, dispara efeito sonoro e visual
        if (ultimaSenhaChamadaNumero !== senhaAtual.numero) {
            ultimaSenhaChamadaNumero = senhaAtual.numero;
            tocarChime();
            
            // Aplica classe de animação conforme a prioridade
            cardDestaque.classList.remove('chamando-normal', 'chamando-prioridade');
            void cardDestaque.offsetWidth; // Reflow
            
            if (senhaAtual.prioridade === 'PRIORITARIA') {
                cardDestaque.classList.add('chamando-prioridade');
            } else {
                cardDestaque.classList.add('chamando-normal');
            }
        }

        const isPrioritaria = senhaAtual.prioridade === 'PRIORITARIA';
        const badgeClass = isPrioritaria ? 'PRIORITARIA' : 'NORMAL';
        const textoPrioridade = isPrioritaria ? '⭐ PRIORITÁRIA' : 'NORMAL';

        containerDestaque.innerHTML = `
            <div class="paciente-info">
                <div class="numero-senha-container">
                    <span class="senha-numero">#${String(senhaAtual.numero).padStart(3, '0')}</span>
                </div>
                <h2 class="paciente-nome">${escaparHTML(senhaAtual.nomeCliente)}</h2>
                <div class="detalhes-atendimento">
                    <span class="badge-servico">Serviço: ${escaparHTML(senhaAtual.tipoServico)}</span>
                    <span class="badge-prioridade ${badgeClass}">${textoPrioridade}</span>
                </div>
            </div>
        `;
    } else {
        cardDestaque.classList.remove('chamando-normal', 'chamando-prioridade');
        containerDestaque.innerHTML = `
            <div class="sem-senha">
                <span class="icon-empty">⏳</span>
                <h2>Aguardando próxima chamada...</h2>
                <p>Fique atento a este painel.</p>
            </div>
        `;
    }

    // 2. Renderizar Histórico de Chamadas Recentes (Exclui a atual)
    const historico = senhas
        .filter(s => s.status === 'CHAMADA' || s.status === 'FINALIZADA')
        .sort((a, b) => b.numero - a.numero);

    const historicoRecente = senhaAtual 
        ? historico.filter(s => s.numero !== senhaAtual.numero).slice(0, 4) 
        : historico.slice(0, 4);

    document.getElementById('countChamadas').innerText = historicoRecente.length;
    const listaHistorico = document.getElementById('listaHistorico');

    if (historicoRecente.length === 0) {
        listaHistorico.innerHTML = '<div class="lista-vazia">Nenhuma chamada recente.</div>';
    } else {
        listaHistorico.innerHTML = historicoRecente.map(s => `
            <div class="item-card">
                <div class="item-info">
                    <span class="item-numero">#${String(s.numero).padStart(3, '0')}</span>
                    <span class="item-nome">${escaparHTML(s.nomeCliente)}</span>
                </div>
                <span class="badge-prioridade ${s.prioridade}" style="font-size:0.75rem; padding:0.25rem 0.75rem;">
                    ${s.prioridade === 'PRIORITARIA' ? 'PRIORITÁRIA' : 'NORMAL'}
                </span>
            </div>
        `).join('');
    }

    // 3. Renderizar Próximos da Fila (Status AGUARDANDO)
    const aguardando = senhas
        .filter(s => s.status === 'AGUARDANDO')
        .sort((a, b) => a.numero - b.numero);

    document.getElementById('countAguardando').innerText = aguardando.length;
    const listaAguardando = document.getElementById('listaAguardando');

    if (aguardando.length === 0) {
        listaAguardando.innerHTML = '<div class="lista-vazia">Nenhum cliente aguardando na fila.</div>';
    } else {
        listaAguardando.innerHTML = aguardando.slice(0, 5).map(s => `
            <div class="item-card">
                <div class="item-info">
                    <span class="item-numero">#${String(s.numero).padStart(3, '0')}</span>
                    <span class="item-nome">${escaparHTML(s.nomeCliente)}</span>
                </div>
                <div class="item-meta">
                    <span>${escaparHTML(s.tipoServico)}</span>
                </div>
            </div>
        `).join('');
    }
}

function escaparHTML(texto) {
    if (!texto) return '';
    return texto.replace(/[&<>"']/g, function(m) {
        return {
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            '"': '&quot;',
            "'": '&#039;'
        }[m];
    });
}

// Inicia polling a cada 2 segundos
setInterval(carregarSenhas, 2000);
carregarSenhas();
