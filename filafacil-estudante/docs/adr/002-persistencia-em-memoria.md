# ADR 002 - Guardar os dados em memoria

- **Status:** aceito
- **Data:** 2026-08-04

## Contexto

O foco do trabalho e arquitetura e padroes de projeto, nao banco de dados. Usar
um banco de verdade exigiria instalar e configurar mais coisas, o que dificultaria
rodar o projeto e tiraria o foco do que esta sendo avaliado.

## Decisao

Guardar as senhas em uma lista na memoria, dentro da classe
`SenhaRepositorioMemoria`, que implementa a interface `SenhaRepository`.

## Consequencias

### Pontos positivos

- so precisa do JDK 21 para rodar, sem instalar banco;
- o projeto inicia rapido;
- os testes ficam simples e sempre dao o mesmo resultado.

### Pontos negativos

- os dados somem quando o programa fecha;
- nao serve para uso real em producao.

## Evolucao futura

Como criamos a interface `SenhaRepository`, seria possivel criar depois uma classe
que salva em banco de dados sem precisar mudar o service.
