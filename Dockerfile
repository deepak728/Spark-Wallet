#Author: Deepak Kumar

FROM java:8-jdk

ARG GRADLE_VERSION=3.5
ARG TOMCAT_VERSION=9
ARG TOMCAT_SUB_VERSION=9.0.8
ARG SERVICE_NAME=spark_wallet
ARG PORT=8080


RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    mkdir /opt/gradle && \
    unzip -d /opt/gradle gradle-${GRADLE_VERSION}-bin.zip

ENV PATH="$PATH:/opt/gradle/gradle-${GRADLE_VERSION}/bin"

#download and extract tomcat
RUN apt-get install tzdata -y && \
    ln -fs /usr/share/zoneinfo/US/Pacific /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata && \
    wget "https://archive.apache.org/dist/tomcat/tomcat-9/v${TOMCAT_SUB_VERSION}/bin/apache-tomcat-${TOMCAT_SUB_VERSION}.tar.gz" && \
    tar -xzvf apache-tomcat-${TOMCAT_SUB_VERSION}.tar.gz && \
    mkdir -p ../../apps && \
    mv apache-tomcat-${TOMCAT_SUB_VERSION} /apps/tomcat${TOMCAT_SUB_VERSION}.${SERVICE_NAME}

# copy code from inventory project
RUN mkdir -p /code/${SERVICE_NAME}

#copy the contents from the application root directory to the docker image
COPY . /code/${SERVICE_NAME}

#switch back to the root directory as working directory for in the docker image
WORKDIR "/"
#switch back to the root directory of the application to genearte to run the gradle build
WORKDIR "/code/${SERVICE_NAME}"

RUN chmod +x Startup.sh


RUN gradle clean build && \
   cp -a /code/${SERVICE_NAME}/build/libs/${SERVICE_NAME}-1.0.war /apps/tomcat${TOMCAT_SUB_VERSION}.${SERVICE_NAME}/webapps/

EXPOSE ${PORT}

ENV TOMCAT_VERSION=${TOMCAT_VERSION}

ENV TOMCAT_SUB_VERSION=${TOMCAT_SUB_VERSION}

ENV SERVICE_NAME=${SERVICE_NAME}

ENTRYPOINT ./Startup.sh ${TOMCAT_VERSION} ${TOMCAT_SUB_VERSION} ${SERVICE_NAME}