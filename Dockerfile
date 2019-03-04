FROM frekele/ant:1.10.3-jdk8 as builder
RUN mkdir /src
WORKDIR /src
COPY . /src

RUN ant -f build.xml clean jar

FROM openjdk:8-jre-alpine
COPY --from=builder /src/dist/SimpleTCPTunnel.jar /SimpleTCPTunnel.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/SimpleTCPTunnel.jar", "0.0.0.0", "8000"]
CMD ["iot.eclipse.com", "1883"]
