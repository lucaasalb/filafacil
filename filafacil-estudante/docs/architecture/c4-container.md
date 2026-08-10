# C4 - Nivel 2: Container

Este diagrama mostra as partes que compoem o sistema por dentro.

```mermaid
flowchart TD
    cliente["Usuario
    Navegador"]

    subgraph filafacil["FilaFacil"]
        web["Interface Web
        HTML, CSS e JavaScript"]
        api["Aplicacao Java 21
        Servidor HTTP + camadas
        Regras e API"]
        memoria[("Repositorio em memoria
        Lista de senhas")]
    end

    cliente -->|HTTP no navegador| web
    web -->|Chamadas JSON| api
    api -->|Interface SenhaRepository| memoria
```

## Explicacao

### Interface Web

Paginas HTML com CSS e JavaScript, servidas pela propria aplicacao. Fazem chamadas
para a API e nao tem regra de negocio.

### Aplicacao Java

Um unico programa que contem as camadas model, repository, service e web. Recebe
as requisicoes, aplica as regras e responde em JSON.

### Repositorio em memoria

Guarda as senhas durante a execucao. Como usamos a interface `SenhaRepository`,
poderia ser trocado por um banco de dados no futuro.
