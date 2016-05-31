FROM java

VOLUME /tmp

ADD tweets-0.1.0.jar tweets.jar

RUN bash -c 'touch /tweets.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tweets.jar"]
