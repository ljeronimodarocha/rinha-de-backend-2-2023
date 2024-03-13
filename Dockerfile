# Etapa de build usando a imagem oficial do Maven
FROM maven:3.8.4-openjdk-17 AS build
# Copia o código fonte do projeto para o container
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

# Define o diretório de trabalho dentro do container
WORKDIR /usr/src/app

# Compila o projeto e empacota a aplicação
RUN mvn clean package -DskipTests

# Etapa de execução usando a imagem oficial do OpenJDK
FROM openjdk:17

# Copia o artefato gerado na etapa de build para o diretório de trabalho
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar

# Define o diretório de trabalho
WORKDIR /usr/app

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]


