FROM openjdk:13-alpine

ADD ./build/libs/affiliate-be-1.0-SNAPSHOT.jar ./app.jar

RUN mkdir data

CMD ["java", "-jar", "app.jar"]
