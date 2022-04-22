FROM gradle:7.2.0-jdk11-hotspot as builder
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace -x test

FROM adoptopenjdk/openjdk11:jre
WORKDIR /app
COPY  --from=builder /builder/build/libs/BookShop_Server-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "BookShop_Server-0.0.1-SNAPSHOT.jar"]
