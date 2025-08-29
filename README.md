# Induflow - Sistema de Gestão Industrial

## Sobre o Projeto

O **Induflow** é um projeto de portfólio que demonstra um sistema web para gestão e monitoramento de processos industriais em tempo real. Ele foi desenhado para oferecer uma visão clara e controle sobre o chão de fábrica, abrangendo desde o lançamento de ordens de produção até a análise de qualidade.

Este projeto é inspirado em um sistema real que desenvolvi de forma autônoma e implementei com sucesso em uma indústria de transformação de plásticos no Polo Industrial de Manaus. O objetivo do Induflow é adaptar e exibir as funcionalidades centrais daquele sistema, comprovado em ambiente de produção, em um formato ideal para demonstração de competências técnicas.

Uma característica marcante do projeto original é a sua **integração direta com o ERP legado** da empresa. O sistema operava conectado a dois bancos de dados distintos simultaneamente — o seu próprio e o do ERP — para sincronizar informações vitais e manter a consistência dos dados entre o chão de fábrica e o sistema de gestão corporativo.

## Principais Funcionalidades

- **Dashboard em Tempo Real:** Visualização centralizada dos principais indicadores de produção.
- **Controle de Ordens de Produção (OP):** Criação, acompanhamento e finalização de OPs.
- **Apontamento de Produção:** Registro de início e parada de produção nas linhas.
- **Gestão de Ocorrências:** Cadastro e acompanhamento de eventos e não conformidades durante o processo.
- **Relatórios de Fechamento:** Geração de relatórios detalhados ao final de cada ordem de produção.

## Tecnologias Utilizadas

- **Backend:**
  - Java
  - Spring Boot
  - Spring Security
  - Maven

- **Frontend:**
  - HTML5
  - CSS3 / SCSS
  - JavaScript
  - Thymeleaf
  - Bootstrap

- **Banco de Dados:**
  - SQL

## Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-repositorio>
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd compol
    ```
3.  **Execute a aplicação com o Maven Wrapper:**
    - No Windows:
      ```bash
      mvnw.cmd spring-boot:run
      ```
    - No Linux/macOS:
      ```bash
      ./mvnw spring-boot:run
      ```
4.  **Acesse a aplicação:**
    Abra o seu navegador e acesse `http://localhost:8080`.
