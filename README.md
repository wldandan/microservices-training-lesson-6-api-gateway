###使用Service的Hystrix Dashboard

##### 启动Eureka Server

cd eureka-server
SERVER_PORT=8011 java -jar build/libs/eureka-server-0.0.1-SNAPSHOT.jar
cd -

##### 启动Composite Service

cd event-composite-service
SERVER_PORT=8084 java -jar build/libs/event-composite-service-0.0.1-SNAPSHOT.jar& 
cd -

##### 启动Review Service

cd review-service
SERVER_PORT=8085 java -jar build/libs/review-service-0.0.1-SNAPSHOT.jar&
cd -

##### 访问Hystrix DashBoard

open 'http://localhost:8084/hystrix'
Input http://localhost:8084/hystrix.stream

查看当前面板的更新变化
停掉review-service，查看当前面板的更新变化

---

###使用Turbine的Hystrix Dashboard
cd turbine-server
SERVER_PORT=8030 java -jar build/libs/turbine-server-0.0.1-SNAPSHOT.jar&
cd -

##### 访问Turbine的Hystrix DashBoard

open 'http://localhost:8030/hystrix'
Input http://localhost:8030/turbine.stream?cluster=default

查看当前面板的更新变化
停掉review-service，查看当前面板的更新变化

---
###在Docker中运行

./build.sh
docker-compose up --build

#####访问composite-service的Hystrix Dashboard

http://localhost:8084/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8084%2Fhystrix.stream

#####访问composite-service的Hystrix Dashboard

http://localhost:8030/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8030%2Fturbine.stream%3Fcluster%3Ddefault





