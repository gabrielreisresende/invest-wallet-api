# Invest Wallet - API
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/gabrielreisresende/invest-wallet-api/blob/main/LICENSE)

[Read this page in English](https://github.com/gabrielreisresende/invest-wallet-api/blob/main/README.md) <br>

[Leia esta página em Português](https://github.com/gabrielreisresende/invest-wallet-api/blob/main/README-pt.md)

## Descrição
Esta é uma API REST projetada para controlar seus investimentos em 'Fundos Imobiliários'.
A API permite aos usuários terem múltiplas carteiras e gerenciá-las de forma eficiente.
Com a Invest Wallet, você pode supervisionar cada ativo em sua carteira, gerar três relatórios distintos relacionados às suas carteiras e usar os dados para criar um painel para análise abrangente.
Além disso, você pode baixar esse relatório de carteira ou recebê-lo em seu e-mail.

--------------------------------------------------------------------------------------------------------------

## Executar a Aplicação Invest Wallet

Primeiramente, clone o repositório:

```
https://github.com/gabrielreisresende/invest-wallet-api.git
```

Após clonar, navegue até o projeto:

```
cd invest-wallet-api
```

## Docker (recomendado)

#### Variáveis de ambiente

Primeiramente, configure as variáveis de ambiente de acordo com seus dados para executar a aplicação. A aplicação utiliza dois arquivos env (db_env e app_env) com as variáveis de aplicação e as variáveis de banco de dados. Você precisa configurar ambos para executar a aplicação.

--------------------------------------------------------------------------------------------------------------

Execute a aplicação com um simples comando Docker:

```
docker compose up --build
```

## Maven (Segunda opção)

#### Variáveis de ambiente

Primeiramente, configure as variáveis de ambiente de acordo com seus dados para executar a aplicação. Altere o arquivo application.properties com suas variáveis de ambiente.

--------------------------------------------------------------------------------------------------------------

Você precisa compilar o código e baixar as dependências do projeto:

```
mvn clean package
```

Após concluir este passo, vamos iniciar a aplicação:

```
mvn spring-boot:run
```

Pronto. A aplicação agora está disponível em `http://localhost:8080/api/v1`.

```
Tomcat iniciado nas portas: 8080 (http) com o caminho do contexto '/api/v1'
AppConfig iniciado em xxxx segundos (JVM em execução por xxxx)
```

--------------------------------------------------------------------------------------------------------------

## Swagger
Link para testar e visualizar todos os endpoints do Invest Wallet:

[OPEN API - DOCS](http://localhost:8080/api/v1/swagger-ui/index.html#/)

--------------------------------------------------------------------------------------------------------------

## Tecnologias
- Java 21;
- Spring Boot 3.2.2;
- MySQL;
- Docker;
- Postman.

## Segurança
- Spring Security para autorização e autenticação;
- Autenticação JWT;
- Dois papéis de usuários: CLIENTE e ADMINISTRADOR;
- Dados de login criptografados com Basic Auth;
- Endpoints que exigem autorização;
- Processo de recuperação de senha esquecida.

### Permissões do Administrador
- Gerenciar códigos de ativos;
- Gerenciar tipos de ativos;
- Gerenciar setores de ativos.

### Funcionalidades do Cliente
- Registrar no sistema;
- Gerenciar dados pessoais;
- Criar e gerenciar múltiplas carteiras;
- Gerenciar ativos da carteira;
- Gerar relatórios sobre cada carteira;
- Baixar um pdf com seu relatório de carteira detalhado;
- Receber o relatório da carteira em seu e-mail.

## Funcionalidades de E-mail
- Enviar um e-mail de boas-vindas após o registro do usuário;
- Enviar um e-mail com autenticação de código de dois fatores durante o processo de redefinição de senha;
- Enviar um e-mail com o pdf do relatório de carteira;
- Enviar e-mail de forma assíncrona;
--------------------------------------------------------------------------------------------------------------
### Exemplo de um relatório de carteira gerado:
![image](https://github.com/gabrielreisresende/invest-wallet-api/assets/123999571/0550b570-25e4-4593-8c89-6be7768d1a36)

--------------------------------------------------------------------------------------------------------------
### Mensagem de e-mail com o pdf do relatório de carteira:
![image](https://github.com/gabrielreisresende/invest-wallet-api/assets/123999571/bb3cdc59-c3e1-4ae3-8fbf-777d98bf530d)