# E-Commerce API

Uma API RESTful desenvolvida com Java 21 e Spring Boot 3 para gerenciamento de produtos, pedidos e usuários.

---

## 📌 Tecnologias & Boas Práticas
**Stack Técnico:**
- [Java 21](https://docs.oracle.com/en/java/javase/24/migrate/significant-changes-jdk-21.html);
- [Spring Boot 3](https://docs.spring.io/spring-boot/index.html);
- [MySQL](https://dev.mysql.com/doc/) (rodando em container [Docker](https://docs.docker.com/get-started/));
- [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html);
- [Lombok](https://projectlombok.org/);
- JJWT para geração/validação de tokens [JWT](https://www.jwt.io/introduction#what-is-json-web-token).

**Boas práticas adotadas:**
- Autenticação segura com JWT (com chave forte de 256 bits);
- Separação de camadas (Controller → Service → Repository);
- Uso de DTOs para requisições e respostas;
- Validação de dados de entrada com `@Valid`, `@NotNull`, `@Min`;
- Transações declarativas com `@Transactional`;
- Consultas otimizadas para casos analíticos (top usuários, ticket médio e faturamento);
- Uso de pacotes e nomenclatura em inglês, enquanto mensagens/logs são em português;
- Containerização do banco de dados para ambiente isolado.

---

## 🚀 Como rodar o projeto

### Pré-requisitos
- Docker instalado;
- JDK 21 instalada ou ambiente compatível;
- Maven instalado ou usar o wrapper `mvnw`.

### Passos:

1. Clone este repositório:
   ```bash
   git clone https://github.com/LeonardoMarinelli/api-rest
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd api-rest
   ```
3. Inicie o container MySQL usando o Docker Compose na raiz do projeto:
   ```bash
   docker-compose up -d
   ```
4. Rodar a aplicação com Maven
   ```bash
   mvn spring-boot:run
   ```
5. A API estará disponível em `http://localhost:8080`. Todas as requisições de exemplo se encontram no arquivo `rest-api.postman_collection.json` na raiz deste projeto.

## 🔨 Melhorias Futuras

- Implementar testes unitários e de integração;
- Adicionar documentação automática com Swagger/OpenAPI;
- Implementar controle de versões na API;
- Adicionar suporte a cache para melhorar performance em consultas frequentes;
- Implementar monitoramento e logging centralizado;
- Remover dados sensíveis dos logs em produção e dos arquivos de configuração.