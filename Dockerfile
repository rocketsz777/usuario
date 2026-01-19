FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/agendador_tarefas-0.0.1-SNAPSHOT.jar /app/agendador_tarefas.jar

EXPOSE 8081

CMD ["java" , "-jar" , "/app/agendador_tarefas.jar"]
