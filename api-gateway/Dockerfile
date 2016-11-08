FROM java:8

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD ./build/libs/api-gateway-0.0.1-SNAPSHOT.jar /app
RUN ln -sf api-gateway-0.0.1-SNAPSHOT.jar api-gateway-latest.jar
CMD java -jar /app/api-gateway-latest.jar