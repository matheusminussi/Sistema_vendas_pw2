### **Introdução**

Este documento tem como objetivo estabelecer uma baseline para essa aplicação Java que foi desenvolvida com **Spring Boot** no backend e **JSF (PrimeFaces)** no frontend. A aplicação implementa um **CRUD de um sistema de vendas** como prova de conceito.

O projeto utiliza o banco de dados **H2 em memória**, com persistência via **Spring Data JPA**, interface construída com **PrimeFaces**, e organização em camadas (`model`, `repository`, `controller`) e o  build é feito com **Maven.**

### **Objetivos do Projeto**

O objetivo deste projeto é desenvolver uma **aplicação web para uma loja online**, permitindo o gerenciamento de produtos, incluindo funcionalidades de **cadastro, listagem, atualização e remoção de produtos**.

A aplicação também tem como metas:

- Demonstrar o uso do **Spring Boot** para a lógica de negócios e persistência de dados.
- Utilizar **JSF com PrimeFaces** para criar uma interface web interativa.
- Estabelecer uma base estável e bem estruturada para futuras melhorias ou ampliações do sistema.

### **Itens de Configuração**

A baseline do projeto inclui todos os artefatos necessários para compilar, executar e manter a aplicação de forma consistente. Abaixo estão os principais itens de configuração identificados:

### **Código-fonte**

Localizado em: `src/main/java/`

- **Pacote `model`**: Contém as classes de entidade (ex.: `Produto.java`) que representam os dados da aplicação.
- **Pacote `repository`**: Contém as interfaces que estendem `JpaRepository`, responsáveis pelas operações com o banco de dados.
- **Pacote `controller`**: Contém os beans JSF responsáveis por intermediar a interação entre a interface e a lógica da aplicação.

### **Arquivos de Configuração**

- **`application.properties`** (`src/main/resources/`):
    
    Arquivo de configuração principal do Spring Boot. Define o banco de dados, configurações do JPA e ativação do console H2.
    
- **`web.xml`** (`src/main/webapp/WEB-INF/`):
    
    Define o comportamento do servlet JSF (FacesServlet), mapeia o carregamento da aplicação JSF e configura parâmetros de inicialização.
    
- **`faces-config.xml`** (`src/main/webapp/WEB-INF/`):
    
    Arquivo de configuração do JSF. Pode ser usado para definir beans gerenciados, navegadores de páginas e componentes personalizados. Atualmente pode estar vazio, pois desde JSF 2.0 muitas configurações são feitas via anotações.
    

### **Interface do Usuário**

- **Arquivos `.xhtml`** (`src/main/webapp/pages/`):
    
    Páginas web da aplicação, construídas com JSF + PrimeFaces. Exemplo: `index.xhtml` ou `produtos.xhtml` para exibir a lista de produtos e permitir ações.
    

### **Dependências**

- Gerenciadas no arquivo `pom.xml`, incluindo:
    - Spring Boot
    - Spring Data JPA
    - PrimeFaces
    - H2 Database
    - JSF (Jakarta Faces)

## Estrutura do Projeto
```
pw2_final-main/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.demo/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       └── repository/
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       └── pages/
│   │           └── index.xhtml
├── pom.xml
└── README.md
```

**Observações:**

- Os pacotes Java estão organizados por responsabilidade: `controller`, `model` e `repository`.
- As páginas JSF estão localizadas em `/webapp/pages`.
- O arquivo `pom.xml` gerencia as dependências do projeto.

### **Controle de Versão**

- Repositório no GitHub.
- Estrutura de branches:
    - `main`: versão estável do projeto
    - `dev` : para desenvolvimento de novas features

### **Configuração do Banco de Dados**

- **Banco utilizado**: H2 (memória)
- **Configuração em `application.properties`:**

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

- Acesse o console do H2 via: `http://localhost:8080/h2-console`

## Dependências Essenciais (`pom.xml`)

```

<dependencies>
<!-- Spring Boot Starter -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- Spring Boot JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- JSF -->
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>jakarta.faces</artifactId>
    <version>4.0.1</version>
</dependency>

<!-- PrimeFaces -->
<dependency>
    <groupId>org.primefaces</groupId>
    <artifactId>primefaces</artifactId>
    <version>13.0.0</version>
</dependency>

</dependencies>
```
