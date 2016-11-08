cd eureka-server
java -jar build/libs/eureka-server-0.0.1-SNAPSHOT.jar
cd -

cd event-composite-service
java -jar build/libs/event-composite-service-0.0.1-SNAPSHOT.jar& 
cd -

cd review-service
java -jar build/libs/review-service-0.0.1-SNAPSHOT.jar&
cd -

open 'http://localhost:9000/hystrix'
#Input http://{event-host:event-port}/hystrix.stream