FROM maven:3.9.9-eclipse-temurin-21-alpine as build
WORKDIR /build
COPY pom.xml .
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21.0.7_6-jdk-alpine-3.21
WORKDIR /app
COPY --from=build ./build/target/*.jar ./api.jar

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''

ENV JWT_PRIVATE_KEY=''
ENV JWT_PUBLIC_KEY=''

EXPOSE 8080

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

RUN addgroup -S dev && \ 
    adduser -S -G dev -h /home/default -s /bin/sh default
USER default

ENTRYPOINT ["/entrypoint.sh"]
CMD ["java", "-jar", "api.jar"]

