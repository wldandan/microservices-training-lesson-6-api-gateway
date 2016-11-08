## 使用 Hystrix Circuit Breakers

**Part 1 - 启动Eureka Server

运行Eureka Server
 
**Part 2 - 构建Composite-service

1. 实现Composite-service

2. 定义ReviewServie/RecommendationService的Fallback处理，并使用Ribbon访问这些服务

3. 增加Hystrix依赖

3. 运行Event-composite-service

4. 使用[seige-A http load tester and benchmarking utility](https://github.com/JoeDog/siege)对请求进行访问
```siege http://localhost:9030/1```

5. 启动Review Service

**Part 3 - 使用Hystrix Dashboard监控流量

1. 在Composite-service中加入Hystrix Dashboard
	spring-cloud-starter-hystrix-dashboard
	在代码的主类中加入@EnableHystrixDashboard

2. 访问Dashboard http://{event-host:event-port}/hystrix
	输入http://{event-host:event-port}/hystrix.stream

---
**Part 4 - 使用Turbine聚合Hystrix Dashboard

1. 构建Turbine Server

2. 在Turbine Server中定义Cluster以及需要聚合的服务名称
	appConfig: event-composite

3. 启动Tubine Server，http://localhost:8030/hystrix
   输入 http://localhost:8030/turbine.stream?cluster=default

4. 查看显示的聚合内容