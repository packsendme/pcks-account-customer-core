
FROM openjdk:8-jdk-alpine
EXPOSE 9094
COPY /target/pcks-account-customer-core-0.0.1-SNAPSHOT.jar pcks-account-customer-core-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/pcks-account-customer-core-0.0.1-SNAPSHOT.jar"]