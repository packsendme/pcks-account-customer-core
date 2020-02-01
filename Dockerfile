
FROM openjdk:8-jdk-alpine
EXPOSE 9094
COPY /target/packsendme-account-server-0.0.1-SNAPSHOT.jar packsendme-account-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/packsendme-account-server-0.0.1-SNAPSHOT.jar"]