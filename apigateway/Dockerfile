FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8001

ENTRYPOINT ["java","-jar","/app.jar"]