FROM openjdk:10-jre-slim

ARG OUTPUT_PATH
ARG BUILT_ARTIFACT

ENV BUILT_ARTIFACT ${BUILT_ARTIFACT}

COPY ${OUTPUT_PATH}/${BUILT_ARTIFACT} /opt/${BUILT_ARTIFACT}

EXPOSE 8080

CMD java -Xms50m -Xmx100m --add-modules java.xml.bind \
    -Dspring.config.additional-location=file:/etc/config/ \
    -Dspring.profiles.active=${ENV_SPRING_PROFILES_ACTIVE} \
    -jar /opt/${BUILT_ARTIFACT}
