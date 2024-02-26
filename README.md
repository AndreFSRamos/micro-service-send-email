# micro-service-send-email

## Descrição.

Micro serviço desenvolvido com a linguagem Java 17 LTS e o framework Spring Boot 3.2.2, para o envio de e-mails de forma síncrona e assíncrona.
O envio de e-mail de forma síncrona é feito por meio de solicitações HTTP, já de forma assíncrona é utilizado o serviço de mensageira RabbitMQ.
É importante mencionar que esse projeto trabalha junto a este outro projeto para a [autenticação](https://github.com/AndreFSRamos/micro-service-to-authentication), pois seja para um envio de e-mail síncrono ou assíncrono, é feita a autenticação do token JWT.

## Requisitos

### Funcionais.
- RF001: enviar e-mail.
- RF002: buscar e-mail por ID.
- RF003: buscar todos os e-mails.

### Não Funcionais.
- RNF001: toda requisição deve ser validada com autenticação de token.
- RNF002: para enviar um e-mail, deve ser informado o nome de quem está enviando, seu username, seu e-mail, o e-mail para quem deseja enviar, o assunto, a origem (URL), a mensagem e o token de autenticação.
- RNF003: enviar e-mail de forma síncrona e assíncrona.
- 
## Recursos Necessários

Para executar a aplicação localmente, você vai precisar:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [RabbitMQ](https://www.rabbitmq.com/docs/download)
- [micro-service-to-authentication](https://github.com/AndreFSRamos/micro-service-to-authentication)
- [Maven 3.9.6](https://maven.apache.org)
- [Docker](https://www.docker.com/) (OBS: O Docker é usado apenas para executar os testes, e o rabbit caso seja opte por instalar dessa forma)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)

## Rodando a aplicação localmente.

- Clone o projeto para sua máquina.
- Importe para a sua IDE.
- Faça o download das dependências.
- Com o MySQL já instalado, crie uma database para o projeto, com um mome de usa preferência.
- Rode [micro-service-to-authentication](https://github.com/AndreFSRamos/micro-service-to-authentication)
- Crie um usuário de integração para a autenticação.
- Configure as variáveis de ambiente da aplicação na sua IDE conforme o arquivo "application-local.yml" e também o "application-test.yml".
- Faça o RUN da aplicação.
- Para rodar os testes de integração, é necessário que o Docker esteja instalado e rodando.

Você pode consultar a documentação dos endpoints no Swagger-UI no link "http://localhost:{A PORTA QUE VOCÊ DEFINIU}/swagger-ui/index.html

## Deploy da aplicação.

Para efetuar o deploy é bem simples, basta executar o comando maven abaixo na pasta raiz do projeto.
```shell
mvn package
```
Porém, há alguns detalhes para verificar antes.
- O Docker deve estar ligado, pois no momento build, todos os testes de integração são validados.
- O comando "mvn package" deve ser executado no terminal da sua IDE, pois as variáveis de ambiente estão nela, e elas serão necessárias para os testes de integração. 
- Para executar o comando, é necessário que as suas variáveis de ambiente do MAVEN e JAVA estejam configuradas corretamente no seu sistema operacional.

Se tudo correr bem, será gerado o arquivo "email-0.0.1-SNAPSHOT.jar" dentro da pasta "target" na raiz do projeto.

## Rodando a aplicação do Docker:

NECESSÁRIO A ETAPA DE "Deploy da aplicação" TER SIDO CONCLUÍDA COM SUCESSO.

- Inicie o seu Docker.
- Execute o comando abaixo no terminal da sua IDE, na pasta raiz do projeto, para gerar uma imagem.
```docker
docker build -t NOME_QUE_VOCE_QUIZER .
```
- Agora execute o comando a baixo para criar um contêiner, lembre-se de preencher as variáveis de ambiente.
```docker
docker run  --name NOME_DO_CONTAINNER \
            --restart always \
            -e HOST_URL_DATABASE= IP DA DATABASE \
            -e PORT_DATABASE= PORTA DA DATBASE \
            -e DATABASE= NOME DATABASE \
            -e DATA_BASE_USERNAME= USERNAME DATABASE \
            -e DATA_BASE_PASS= PASSWORD DATABASE \
            -e EMAIL_HOST_SMTP= HOST DO SERVISO SMTP \
            -e EMAIL_PORT_SMTP= PORTA DO SERVISO SMTP \
            -e EMAIL_USERNAME= USERNAME \
            -e EMAIL_PASS= PASSWORD \
            -e HOST_RABBITMQ= IP DO RABBITMQ \
            -e PORT_RABBITMQ= PORTA DO RABBITMQ \
            -e USERNAME_RABBITMQ= USERNAMME RAABBITMQ \
            -e PASS_RABBITMQ= PASSWORD RABBITMQ \
            -e SERVER_PORT=3390 \
            -e AUTH_SERVICE_URL= URL DO SERVIÇO AUTENTICAÇÃO PARA VALIDAR O TOKEN \
            -e SERVER_PORT= PORTA QUE VAI FICAR DISPONIVEL A APLICAÇÃO \
            -p 3130:3100 <- DEVE SER O MESMMO QUE O SERVER_PORT \
            -d \
            NOME DA IMAGEM QUE VOCÊ INFORMOR NO docker build
```

## Copyright

Desenvolvido por [André Ramos](https://andrefsramos.tech/) | [Linkedin](https://www.linkedin.com/in/andrefsramos-tech/).
 
