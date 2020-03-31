FROM openjdk:8-jdk
VOLUME /tmp
ARG JAR_FILE=./build/libs/GoreIT-0.0.1.jar
ENV JAVA_OPTS="-XX:+UseG1GC -Xmx1024m"
ENV TZ=Europe/Warsaw
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ${JAR_FILE} app.jar/
COPY ./entrypoint.sh entrypoint.sh
#COPY ./jmxremote.password /opt/tomcat/bin/jmxremote.password
#COPY ./jmxremote.access /opt/tomcat/bin/jmxremote.access
HEALTHCHECK --start-period=3m CMD curl --fail http://localhost:8080/actuator/health
ENTRYPOINT ["/entrypoint.sh"]