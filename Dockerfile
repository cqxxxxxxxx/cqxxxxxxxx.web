FROM java:openjdk-8-jdk

MAINTAINER cqxxxxxxxx@gmail.com

VOLUME /tmp
ARG APP_MODULE
ENV APP_MODULE ${APP_MODULE}
ADD $APP_MODULE/target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar