FROM tomcat:8
#MAINTAINER PRAGATI BHALLA

# setup
COPY java-hello-world-webapp-1.0.0.war /usr/local/tomcat/webapps/
RUN java -version


# Expose the ports we're interested in
EXPOSE 8080

# Set the default command to run on boot
CMD ["catalina.sh", "run"]
