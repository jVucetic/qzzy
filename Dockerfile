FROM openjdk:21 AS build
RUN microdnf install findutils
EXPOSE 8080
WORKDIR .

COPY ./build.gradle .
COPY ./gradlew .
COPY ./gradlew.bat .
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew clean build
RUN ls -la ./

FROM openjdk:21
COPY --from=build ./build/libs/*.jar ./qzzy.jar
CMD ["java","-jar","qzzy.jar"]