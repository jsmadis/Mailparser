FROM openjdk:8
FROM jboss/wildfly

MAINTAINER jakub.smadis@gmail.com

ADD ./parser.war /opt/jboss/wildfly/standalone/deployments/
ADD ./rest.war /opt/jboss/wildfly/standalone/deployments/

# PostgreSQL datasource to wildfly
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/jdbc/main; \
    cd /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/jdbc/main; \
    curl -O https://jdbc.postgresql.org/download/postgresql-42.0.0.jar

ADD module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/jdbc/main/

ADD datasource-install.cli /opt/jboss/wildfly/bin

RUN $JBOSS_HOME/bin/jboss-cli.sh --file=$JBOSS_HOME/bin/datasource-install.cli

USER root

# Tesseract OCR
RUN yum-config-manager --add-repo https://download.opensuse.org/repositories/home:/Alexander_Pozdnyakov/CentOS_7/; \
    rpm --import https://build.opensuse.org/projects/home:Alexander_Pozdnyakov/public_key; \
    yum update -y; \
    yum install tesseract -y; \
    yum install ghostscript -y; \
    yum install wget -y; \
    mkdir /usr/local/share/tessdata; \
    wget https://github.com/tesseract-ocr/tessdata/raw/4.00/ces.traineddata; \
    wget https://github.com/tesseract-ocr/tessdata/raw/4.00/eng.traineddata; \
    wget https://github.com/tesseract-ocr/tessdata/raw/4.00/slk.traineddata; \
    mv *.traineddata /usr/local/share/tessdata/


ENV TESSDATA_PREFIX=/usr/local/share/tessdata