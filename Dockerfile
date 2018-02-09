FROM openjdk:9.0.1-11-jdk-slim

ARG OUTPUT_PATH
ARG BUILT_ARTIFACT

ENV BUILT_ARTIFACT ${BUILT_ARTIFACT}

ADD ${OUTPUT_PATH}/${BUILT_ARTIFACT} /opt/${BUILT_ARTIFACT}

EXPOSE 8080

CMD java -Xms50m -Xmx100m --add-modules java.xml.bind \
    -Dserver.tomcat.max-threads=${SERVER_TOMCAT_MAX_THREADS} \
    -Dspring.cloud.config.uri=http://config-server:${CONFIG_SERVER_PORT} \
    -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
    -jar /opt/${BUILT_ARTIFACT}
