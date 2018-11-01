FROM openjdk:8
FROM jboss/wildfly

MAINTAINER jakub.smadis@gmail.com

ADD ./parser.war /opt/jboss/wildfly/standalone/deployments/
ADD ./rest.war /opt/jboss/wildfly/standalone/deployments/

USER root

RUN yum-config-manager --add-repo https://download.opensuse.org/repositories/home:/Alexander_Pozdnyakov/CentOS_7/
RUN rpm --import https://build.opensuse.org/projects/home:Alexander_Pozdnyakov/public_key
RUN yum update -y
RUN yum install tesseract -y
RUN yum install ghostscript -y

RUN yum install wget -y

RUN mkdir /usr/local/share/tessdata

RUN wget https://github.com/tesseract-ocr/tessdata/raw/4.00/ces.traineddata
RUN wget https://github.com/tesseract-ocr/tessdata/raw/4.00/eng.traineddata
RUN wget https://github.com/tesseract-ocr/tessdata/raw/4.00/slk.traineddata

RUN mv *.traineddata /usr/local/share/tessdata/

ENV TESSDATA_PREFIX=/usr/local/share/tessdata

