
FROM java:8
EXPOSE 9094
ADD /target/packsendme-account-server-0.0.1-SNAPSHOT.jar packsendme-account-server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/packsendme-account-server-0.0.1-SNAPSHOT.jar"]