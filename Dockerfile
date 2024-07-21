FROM tomcat:10.0.27-jdk17

WORKDIR /usr/local/tomcat/webapps/

COPY build/libs/*.war authentication-server.war

EXPOSE 8080

CMD ["catalina.sh", "run"]