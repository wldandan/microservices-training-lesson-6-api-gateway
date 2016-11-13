FROM java:8

RUN mkdir /app
WORKDIR /app

ADD wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ADD build/libs/turbine-server-0.0.1-SNAPSHOT.jar /app
RUN ln -sf turbine-server-0.0.1-SNAPSHOT.jar turbine-server-latest.jar

CMD java -jar /app/turbine-server-latest.jar