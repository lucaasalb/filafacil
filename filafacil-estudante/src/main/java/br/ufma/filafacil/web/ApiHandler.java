package br.ufma.filafacil.web;

import br.ufma.filafacil.model.PoliticaFila;
import br.ufma.filafacil.model.Prioridade;
import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.model.TipoServico;
import br.ufma.filafacil.service.SenhaService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// Camada web: recebe as requisicoes HTTP, chama o servico e devolve JSON.
// Esta classe nao contem regra de negocio, so traduz HTTP para chamadas de metodo.
public class ApiHandler implements HttpHandler {

    private SenhaService servico;

    public ApiHandler(SenhaService servico) {
        this.servico = servico;
    }

    @Override
    public void handle(HttpExchange troca) throws IOException {
        String caminho = troca.getRequestURI().getPath();
        String metodo = troca.getRequestMethod();

        try {
            if (caminho.equals("/api/senhas") && metodo.equals("GET")) {
                responder(troca, 200, JsonHelper.listaParaJson(servico.listarSenhas()));

            } else if (caminho.equals("/api/senhas") && metodo.equals("POST")) {
                criarSenha(troca);

            } else if (caminho.equals("/api/senhas/proxima") && metodo.equals("POST")) {
                chamarProxima(troca);

            } else if (caminho.equals("/api/painel") && metodo.equals("GET")) {
                responder(troca, 200, JsonHelper.resumoParaJson(servico.gerarResumo()));

            } else if (caminho.startsWith("/api/senhas/") && metodo.equals("POST")) {
                acaoNaSenha(troca, caminho);

            } else {
                responder(troca, 404, JsonHelper.mensagem("Endpoint nao encontrado."));
            }

        } catch (IllegalArgumentException e) {
            responder(troca, 400, JsonHelper.mensagem(e.getMessage()));
        } catch (IllegalStateException e) {
            responder(troca, 400, JsonHelper.mensagem(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            responder(troca, 500, JsonHelper.mensagem("Erro interno no servidor."));
        }
    }

    private void criarSenha(HttpExchange troca) throws IOException {
        Map<String, String> dados = lerCorpo(troca);

        String nome = dados.get("nomeCliente");
        String tipoTexto = dados.getOrDefault("tipoServico", "GERAL");
        String prioridadeTexto = dados.getOrDefault("prioridade", "NORMAL");

        TipoServico tipo = TipoServico.valueOf(tipoTexto.toUpperCase());
        Prioridade prioridade = Prioridade.valueOf(prioridadeTexto.toUpperCase());

        Senha senha = servico.criarSenha(nome, tipo, prioridade);
        responder(troca, 201, JsonHelper.senhaParaJson(senha));
    }

    private void chamarProxima(HttpExchange troca) throws IOException {
        Map<String, String> dados = lerCorpo(troca);

        String politicaTexto = dados.getOrDefault("politica", "FIFO");
        String canal = dados.getOrDefault("canal", "CONSOLE");

        PoliticaFila politica = PoliticaFila.valueOf(politicaTexto.toUpperCase());

        Senha senha = servico.chamarProxima(politica, canal);
        responder(troca, 200, JsonHelper.senhaParaJson(senha));
    }

    // Trata /api/senhas/{numero}/finalizar e /api/senhas/{numero}/cancelar
    private void acaoNaSenha(HttpExchange troca, String caminho) throws IOException {
        String[] partes = caminho.split("/");
        // partes: ["", "api", "senhas", "{numero}", "{acao}"]
        if (partes.length != 5) {
            responder(troca, 404, JsonHelper.mensagem("Endpoint nao encontrado."));
            return;
        }

        int numero = Integer.parseInt(partes[3]);
        String acao = partes[4];

        Senha senha;
        if (acao.equals("finalizar")) {
            senha = servico.finalizar(numero);
        } else if (acao.equals("cancelar")) {
            senha = servico.cancelar(numero);
        } else {
            responder(troca, 404, JsonHelper.mensagem("Acao desconhecida."));
            return;
        }

        responder(troca, 200, JsonHelper.senhaParaJson(senha));
    }

    // Le o corpo da requisicao e transforma em um mapa campo -> valor.
    // Faz uma leitura bem simples de JSON, suficiente para este projeto.
    private Map<String, String> lerCorpo(HttpExchange troca) throws IOException {
        InputStream entrada = troca.getRequestBody();
        String corpo = new String(entrada.readAllBytes(), StandardCharsets.UTF_8);

        Map<String, String> mapa = new HashMap<>();
        corpo = corpo.replace("{", "").replace("}", "").replace("\"", "");

        if (corpo.trim().isEmpty()) {
            return mapa;
        }

        String[] pares = corpo.split(",");
        for (String par : pares) {
            String[] chaveValor = par.split(":");
            if (chaveValor.length == 2) {
                mapa.put(chaveValor[0].trim(), chaveValor[1].trim());
            }
        }
        return mapa;
    }

    private void responder(HttpExchange troca, int codigo, String corpo) throws IOException {
        byte[] bytes = corpo.getBytes(StandardCharsets.UTF_8);
        troca.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        troca.sendResponseHeaders(codigo, bytes.length);
        OutputStream saida = troca.getResponseBody();
        saida.write(bytes);
        saida.close();
    }
}
