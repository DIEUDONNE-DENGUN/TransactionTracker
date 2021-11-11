#### Stage 1: Build the application
FROM openjdk:11-jdk-slim as build

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

#-DskipTests
# Package the application
RUN ./mvnw package
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:11-jre-slim

ARG BUILT_PATH=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${BUILT_PATH}/BOOT-INF/lib /app/lib
COPY --from=build ${BUILT_PATH}/META-INF /app/META-INF
COPY --from=build ${BUILT_PATH}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","org.iota.transactiontracking.TransactiontrackingApplication"]
