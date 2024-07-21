FROM tomcat:latest

RUN rm -rf /usr/local/tomcat/webapps/*
ADD build/libs/spring-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]