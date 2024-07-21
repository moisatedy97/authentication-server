FROM tomcat:latest

RUN rm -rf /usr/local/tomcat/webapps/*
ADD build/libs/authentication-server-1.0.0.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8081

CMD ["catalina.sh", "run"]