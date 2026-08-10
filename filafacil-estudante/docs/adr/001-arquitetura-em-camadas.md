# ADR 001 - Usar arquitetura em camadas

- **Status:** aceito
- **Data:** 2026-08-04

## Contexto

O trabalho pede que o sistema adote claramente um padrao arquitetural e que
respeite os principios de projeto (alta coesao, baixo acoplamento e divisao de
responsabilidades). Precisavamos de uma organizacao simples de entender e que
separasse bem as partes do sistema.

## Decisao

Decidimos usar arquitetura em camadas, dividindo o codigo em:

- **model**: entidades e regras do dominio;
- **repository**: armazenamento das senhas;
- **service**: casos de uso do sistema;
- **web**: servidor HTTP, API e interface.

Cada camada so depende das camadas mais internas. A camada web chama o service,
o service usa o repository e o model, e o model nao depende de ninguem.

## Consequencias

### Pontos positivos

- fica facil saber onde cada coisa deve ficar;
- as regras de negocio ficam separadas da parte web;
- da para testar o service sem subir o servidor;
- o repositorio pode ser trocado sem mexer no service, pois usamos uma interface.

### Pontos negativos

- gera mais arquivos e classes do que um projeto tudo junto;
- para um sistema pequeno, pode parecer mais organizacao do que o necessario.
