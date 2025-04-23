FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o JAR da aplicação (ajuste no caminho relativo)
COPY ../target/flyflix.jar app.jar

# Define variáveis de ambiente
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Expõe a porta usada pela aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


