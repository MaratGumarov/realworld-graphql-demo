FROM openjdk:17-alpine
COPY "target/*.jar" "graphql-api.jar"
ENTRYPOINT ["java","-jar","/graphql-api.jar"]
EXPOSE 8080
