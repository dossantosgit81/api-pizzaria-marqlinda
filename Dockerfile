FROM maven:3.9.9-amazoncorretto-21-al2023 as build
WORKDIR /build
COPY . . 
RUN mvn clean package -DskipTests

FROM amazoncorretto:21.0.6
WORKDIR /app

COPY --from=build ./build/target/*.jar ./api.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE='hml'

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''

ENV JWT_PRIVATE_KEY=''
ENV JWT_PUBLIC_KEY=''
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar api.jar


