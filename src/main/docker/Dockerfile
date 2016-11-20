FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD spammer-1.0.jar app.jar
ADD comments.txt comments.txt
RUN sh -c 'touch /app.jar'
RUN sh -c 'touch /comments.txt'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]