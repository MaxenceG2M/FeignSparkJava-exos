FROM anapsix/alpine-java:jre8
MAINTAINER Igor Laborie <ilaborie@gmail.com> / Laurent Baresse <laurent.baresse@gmail.com>

COPY target/exos-1.0-jar-with-dependencies.jar ./exos-1.0.jar

EXPOSE 8080

CMD java -jar exos-1.0.jar