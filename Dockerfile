FROM openjdk:12
ARG JAR_FILE=target/crypto-1.0.jar
COPY ${JAR_FILE} app.jar
COPY target/classes/prices/* /tmp/prices/
ENTRYPOINT ["java","-jar","/app.jar"]
LABEL maintainer="Akash Mohapatra" email="akash.zlatan@gmail.com"