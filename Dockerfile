# Preparation steps
FROM openjdk:21 AS BUILD_IMAGE
RUN microdnf install findutils
ENV APP_HOME=/root
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME

# Copy all the files
COPY ./build.gradle ./gradlew ./gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY ./src $APP_HOME/src/

# Build desirable JAR
RUN ./gradlew clean build -x test

RUN ls -la /root

#FROM openjdk:21
#WORKDIR /app/
#COPY --from=BUILD_IMAGE /root/build/libs/*.jar ./qzzy.jar
#EXPOSE 8080
#CMD ["java","-jar","qzzy.jar"]




#FROM openjdk:21 AS build
#RUN microdnf install findutils
#EXPOSE 8080
#WORKDIR .
#
#COPY ./build.gradle .
#COPY ./gradlew .
#COPY ./gradlew.bat .
#COPY gradle .
#COPY src ./src
#
#RUN ./gradlew clean build -x test
#
#ENTRYPOINT ["java","-jar","qzzy-0.0.1-SNAPSHOT.jar"]