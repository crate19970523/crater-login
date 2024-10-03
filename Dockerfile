FROM amazoncorretto:21-alpine3.20
RUN addgroup -g 1024 mygroup \
    && adduser -D -u 1024 -G mygroup myuser
RUN mkdir -p /opt/login/log
USER myuser
COPY build/libs/*.jar /opt/login/app/login.jar
VOLUME ["/opt/login/conf", "/opt/login/log:Z"]
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test", "-Dspring.config.additional-location=/opt/login/conf/application-test.yml", "/opt/login/app/login.jar"]