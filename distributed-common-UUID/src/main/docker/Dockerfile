FROM java:8-alpine
VOLUME /logs
ADD /target/distributed-common-UUID.jar  distributed-common-UUID.jar

ENV LANG en_US.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 20001
ENTRYPOINT ["java","-jar","distributed-common-UUID.jar"]
