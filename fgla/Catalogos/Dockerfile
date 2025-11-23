FROM quay.io/wildfly/wildfly:31.0.0.Final-jdk17
#ENV JAVA_OPTS = JAVA_OPTS '-Xms256m -Xmx1024m'
#ENV JAVA_TOOL_OPTIONS "-XX:MaxRAMPercentage=80 -Xms256m -Xmx1024m"
#ENV JBOSS_JAVA_SIZING = "-Xms256m -Xmx1024m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m"
#ENV GC_MAX_METASPACE_SIZE = 256
#ENV GC_METASPACE_SIZE = 96

USER root
RUN mkdir -p /sisecofi/logs
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
RUN chmod 777 /sisecofi
RUN chmod 777 /sisecofi/logs
USER jboss
ADD postgresql-42.7.4.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
ADD module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main
ADD /src/main/resources/configuracion-catalogos.properties /sisecofi
ADD standalone.xml /opt/jboss/wildfly/standalone/configuration

ADD standalone.conf /opt/jboss/wildfly/bin/

ADD target/*.war /opt/jboss/wildfly/standalone/deployments/Catalogos.war