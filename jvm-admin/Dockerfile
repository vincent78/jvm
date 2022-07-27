FROM openjdk:8u312-jre-slim-buster

WORKDIR /opt
COPY target/jvm-admin.jar .

EXPOSE 9091 5005

ENV APP_PORT=9091
ENV TZ=Asia/Shanghai

# 如果容器分配的内存为 512M时，设计JVM的为512-64=448M
ENV JAVA_OPTS="-Xms448m -Xmx448m -Xss256k -XX:MetaspaceSize=448m -XX:MaxMetaspaceSize=448m -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+UseCGroupMemoryLimitForHeap -XX:+HeapDumpOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"
# ENV EXT_ARG="-Dserver.port=8080 -Dspring.cloud.nacos.config.enabled=false -Dspring.cloud.nacos.discovery.server-addr=svc-nacos:8848 -Dspring.cloud.nacos.discovery.namespace=public"
ENV EXT_ARG=""
# 远程调试
ENV REMOTE_ARG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENTRYPOINT java  $JAVA_OPTS $REMOTE_ARG $EXT_ARG -jar /opt/jvm-admin.jar