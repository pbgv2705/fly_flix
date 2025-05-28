# Usar uma imagem base com Maven e JDK
FROM maven:3.8.4-openjdk-17-slim AS builder

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e o restante dos arquivos do projeto
COPY pom.xml .

# Baixar as dependências do Maven (isso otimiza o processo de build)
RUN mvn dependency:go-offline

# Copiar o restante do código para o container
COPY src ./src

# Executar o build do Maven para gerar o arquivo JAR
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# Usar uma imagem base do OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho no container
WORKDIR /app

# Copiar o JAR gerado pelo Maven (do estágio anterior) para o container
COPY --from=builder /app/target/flyflix-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]