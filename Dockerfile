# build
FROM maven:3-openjdk-11
WORKDIR /usr/src/app
COPY . .
RUN mvn clean package
ENTRYPOINT ["mvn","spring-boot:run"]