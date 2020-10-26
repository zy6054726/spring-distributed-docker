FROM java:8-alpine
VOLUME /logs

ADD /target/distributed-eurekaserver.jar  distributed-eurekaserver.jar

ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 9081
EXPOSE 9082
EXPOSE 9083
EXPOSE 9084
ENTRYPOINT ["java","-jar","distributed-eurekaserver.jar"]
#ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9096","traffic-computer-consumer.jar"]
