cd eureka-server
SERVER_PORT=8011 java -jar build/libs/eureka-server-0.0.1-SNAPSHOT.jar&
cd -

cd event-composite-service
SERVER_PORT=8084 java -jar build/libs/event-composite-service-0.0.1-SNAPSHOT.jar&
cd -

cd review-service 
SERVER_PORT=8085 java -jar build/libs/review-service-0.0.1-SNAPSHOT.jar&
cd -

cd turbine-server
SERVER_PORT=8030 java -jar turbine-server-0.0.1-SNAPSHOT.jar&
cd -

open 'http://localhost:8030/hystrix'
#http://localhost:8030/turbine.stream?cluster=default