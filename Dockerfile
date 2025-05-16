FROM openjdk:17

WORKDIR /app

COPY target/cotador-0.0.1-SNAPSHOT.jar /app/cotador.jar

EXPOSE 8080

CMD ["java", "-jar", "cotador.jar"]

#docker image build -t cotador .
#docker container run --rm -p 8080:8080 cotador