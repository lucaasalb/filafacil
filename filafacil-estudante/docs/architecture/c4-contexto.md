# C4 - Nivel 1: Contexto

Este diagrama mostra o FilaFacil como uma unica caixa e quem interage com ele.

```mermaid
flowchart TD
    atendente["Atendente
    Gera e chama senhas"]
    gestor["Gestor
    Acompanha o painel"]
    cliente["Cliente
    Recebe e ve sua senha"]

    sistema["FilaFacil
    Sistema de gestao de filas"]

    atendente -->|Gera, chama, finaliza e cancela senhas| sistema
    gestor -->|Consulta o painel| sistema
    sistema -->|Mostra o numero e o estado da senha| cliente
```

## Explicacao

O FilaFacil cuida de todo o ciclo de uma senha: da criacao ate a finalizacao ou
o cancelamento. O atendente opera o sistema, o gestor acompanha os numeros e o
cliente ve a sua senha ser chamada.

## Fora do escopo

- login e controle de acesso;
- banco de dados;
- envio real de e-mail ou SMS;
- varias unidades de atendimento ao mesmo tempo.
