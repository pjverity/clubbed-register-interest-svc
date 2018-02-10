FROM openjdk:9-jre-slim

ARG OUTPUT_PATH
ARG BUILT_ARTIFACT

ENV BUILT_ARTIFACT ${BUILT_ARTIFACT}

ADD ${OUTPUT_PATH}/${BUILT_ARTIFACT} /opt/${BUILT_ARTIFACT}

EXPOSE 8080

CMD java -Xms50m -Xmx100m --add-modules java.xml.bind \
    -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
    -Dspring.datasource.url=${DATASOURCE_URL} \
    -Dspring.datasource.username=${DATASOURCE_USERNAME} \
    -Dspring.datasource.password=${DATASOURCE_PASSWORD} \
    -Dapplication.amqp.queue.name=${AMQP_QUEUE_NAME} \
    -Dserver.tomcat.max-threads=${SERVER_TOMCAT_MAX_THREADS} \
    -jar /opt/${BUILT_ARTIFACT}
