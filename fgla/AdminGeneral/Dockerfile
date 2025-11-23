#FROM nexusavl.sat.gob.mx:8084/openjdk/openjdk-11-focal
#FROM openjdk:22-ea-17-jdk
#RUN mkdir -p /home/jboss/src/app
#WORKDIR /home/jboss/src/app
#ENV JAVA_HOME /etc/alternatives/jre
#ADD target/*.jar /home/jboss/src/app/AdminPlantilla.jar
#EXPOSE 8787
#CMD ["java", "-jar","AdminPlantilla.jar"]
#FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17
FROM quay.io/wildfly/wildfly:31.0.0.Final-jdk17
# change active user to root
USER root
RUN mkdir -p /sisecofi/logs
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
RUN mkdir -p /sisecofi/header
RUN chmod 777 /sisecofi
RUN chmod 777 /sisecofi/logs
RUN chmod 777 /sisecofi/header
USER jboss
ADD postgresql-42.7.4.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
ADD module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
ADD /src/main/resources/configuracion-administracion.properties /sisecofi
ADD standalone.xml /opt/jboss/wildfly/standalone/configuration

ADD standalone.conf /opt/jboss/wildfly/bin/

ADD target/*.war /opt/jboss/wildfly/standalone/deployments/AdminGeneral.war
