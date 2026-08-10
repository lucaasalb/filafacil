// JavaScript da interface do FilaFacil.
// Conversa com a API Java usando fetch e atualiza a tela.

// Carrega a lista de senhas e o painel quando a pagina abre
window.onload = function () {
    atualizarTudo();
};

function atualizarTudo() {
    carregarSenhas();
    carregarPainel();
}

// Busca as senhas na API e monta a tabela
function carregarSenhas() {
    fetch("/api/senhas")
        .then(function (resposta) {
            return resposta.json();
        })
        .then(function (senhas) {
            var corpo = document.getElementById("corpoTabela");
            corpo.innerHTML = "";

            senhas.forEach(function (senha) {
                var linha = document.createElement("tr");

                var acoes = "";
                if (senha.status === "AGUARDANDO" || senha.status === "CHAMADA") {
                    if (senha.status === "CHAMADA") {
                        acoes += "<button class='btn-acao btn-finalizar' onclick='finalizar(" + senha.numero + ")'>Finalizar</button>";
                    }
                    acoes += "<button class='btn-acao btn-cancelar' onclick='cancelar(" + senha.numero + ")'>Cancelar</button>";
                }

                linha.innerHTML =
                    "<td>" + senha.numero + "</td>" +
                    "<td>" + senha.nomeCliente + "</td>" +
                    "<td>" + senha.tipoServico + "</td>" +
                    "<td>" + senha.prioridade + "</td>" +
                    "<td>" + senha.status + "</td>" +
                    "<td>" + acoes + "</td>";

                corpo.appendChild(linha);
            });
        });
}

// Busca os numeros do painel
function carregarPainel() {
    fetch("/api/painel")
        .then(function (resposta) {
            return resposta.json();
        })
        .then(function (resumo) {
            document.getElementById("total").innerText = resumo.total;
            document.getElementById("aguardando").innerText = resumo.aguardando;
            document.getElementById("chamadas").innerText = resumo.chamadas;
            document.getElementById("finalizadas").innerText = resumo.finalizadas;
            document.getElementById("canceladas").innerText = resumo.canceladas;
        });
}

// Cria uma nova senha
function criarSenha() {
    var nome = document.getElementById("nomeCliente").value;
    var tipo = document.getElementById("tipoServico").value;
    var prioridade = document.getElementById("prioridade").value;

    if (nome.trim() === "") {
        alert("Digite o nome do cliente.");
        return;
    }

    var dados = {
        nomeCliente: nome,
        tipoServico: tipo,
        prioridade: prioridade
    };

    fetch("/api/senhas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dados)
    }).then(function () {
        document.getElementById("nomeCliente").value = "";
        atualizarTudo();
    });
}

// Chama a proxima senha usando a politica escolhida
function chamarProxima() {
    var politica = document.getElementById("politica").value;
    var canal = document.getElementById("canal").value;

    var dados = {
        politica: politica,
        canal: canal
    };

    fetch("/api/senhas/proxima", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dados)
    }).then(function (resposta) {
        if (!resposta.ok) {
            return resposta.json().then(function (erro) {
                alert(erro.mensagem);
            });
        }
        atualizarTudo();
    });
}

// Finaliza uma senha
function finalizar(numero) {
    fetch("/api/senhas/" + numero + "/finalizar", { method: "POST" })
        .then(function () {
            atualizarTudo();
        });
}

// Cancela uma senha
function cancelar(numero) {
    fetch("/api/senhas/" + numero + "/cancelar", { method: "POST" })
        .then(function () {
            atualizarTudo();
        });
}
