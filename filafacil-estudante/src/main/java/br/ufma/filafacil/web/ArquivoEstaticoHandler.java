package br.ufma.filafacil.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// Serve os arquivos da interface (HTML, CSS e JS) que ficam em resources/static.
public class ArquivoEstaticoHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange troca) throws IOException {
        String caminho = troca.getRequestURI().getPath();

        // Se pedir a raiz, entrega o index.html
        if (caminho.equals("/")) {
            caminho = "/index.html";
        }

        String recurso = "/static" + caminho;
        InputStream entrada = getClass().getResourceAsStream(recurso);

        if (entrada == null) {
            String erro = "Arquivo nao encontrado.";
            byte[] bytes = erro.getBytes(StandardCharsets.UTF_8);
            troca.sendResponseHeaders(404, bytes.length);
            OutputStream saida = troca.getResponseBody();
            saida.write(bytes);
            saida.close();
            return;
        }

        byte[] conteudo = entrada.readAllBytes();
        entrada.close();

        troca.getResponseHeaders().set("Content-Type", descobrirTipo(caminho));
        troca.sendResponseHeaders(200, conteudo.length);
        OutputStream saida = troca.getResponseBody();
        saida.write(conteudo);
        saida.close();
    }

    // Define o tipo do conteudo conforme a extensao do arquivo.
    private String descobrirTipo(String caminho) {
        if (caminho.endsWith(".html")) {
            return "text/html; charset=utf-8";
        }
        if (caminho.endsWith(".css")) {
            return "text/css; charset=utf-8";
        }
        if (caminho.endsWith(".js")) {
            return "application/javascript; charset=utf-8";
        }
        return "text/plain; charset=utf-8";
    }
}
