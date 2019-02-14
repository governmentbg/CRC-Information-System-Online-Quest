FROM java:8-jre

ADD ./target/crc-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/crc-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080