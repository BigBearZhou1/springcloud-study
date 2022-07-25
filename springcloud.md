# SpringCloud

 # 微服务的中间件

- 客户端的负载均衡——Netflix Ribbon
- 熔断、降级——Netflix Hystrix
- 分布式锁——zookeeper 
- 服务监控——Actuator

![image-20220207205104218](D:\learnNotes\springcloud.assets\image-20220207205104218.png)



# 父工程

1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>cloud2022</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <lombok.version>1.18.10</lombok.version>
        <log4j.version>1.2.17</log4j.version>
        <mysql.version>8.0.18</mysql.version>
        <druid.version>1.1.16</druid.version>
        <mybatis.spring.boot.version>2.1.1</mybatis.spring.boot.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-project-info-reports-plugin</artifactId>
                <version>3.0.0</version>
            </dependency>
            <!--spring boot 2.2.2-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR1-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--mysql-->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
                <scope>runtime</scope>
            </dependency>
            <!-- druid-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.spring.boot.version}</version>
            </dependency>
            <!--junit-->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!--log4j-->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

# 子module创建流程

1. 建module

2. 改pom

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <parent>
           <artifactId>cloud2022</artifactId>
           <groupId>org.example</groupId>
           <version>1.0-SNAPSHOT</version>
       </parent>
       <modelVersion>4.0.0</modelVersion>
   
       <artifactId>cloud-provider-payment8001</artifactId>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-actuator</artifactId>
           </dependency>
           <dependency>
               <groupId>org.mybatis.spring.boot</groupId>
               <artifactId>mybatis-spring-boot-starter</artifactId>
           </dependency>
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid-spring-boot-starter</artifactId>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-jdbc</artifactId>
           </dependency>
   
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
       </dependencies>
   </project>
   ```

   

3. 写YML

   /resource/application.yml

   ```yml
   server:
     port: 8001
   
   spring:
     application:
       name: cloud-payment-service
     datasource:
       type: com.alibaba.druid.pool.DruidDataSource
       driver-class-name: com.mysql.jdbc.Driver
       url: jdbc:mysql://localhost:3306/db2020?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2b8
       username: root
       password: root
   ```

   

4. 主启动

   ```java
   package com.zwz.springcloud;
   
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   
   @SpringBootApplication
   public class PaymentMain8001 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentMain8001.class, args);
       }
   }
   
   ```

   

5. 业务类

# run DashBoard

1. 开启run dashboard



# 热部署Devtools

1. 添加dependence

   ```xml
   <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
   ```

2. 添加插件到父pom

   ```xml
    <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
                   <executions>
                       <execution>
                           <id>repackage</id>
                           <goals>
                               <goal>repackage</goal>
                           </goals>
                       </execution>
                   </executions>
               </plugin>
           </plugins>
       </build>
   ```

3. Enabling automatic build

   settings/compiler/

   Display

   build

   complie

   都打上钩

# RestTemplate

1. 是什么？

   发送rest调用的模板

2. 通过配置将restTemplate加入到spring容器中

   ```java
   @Configuration
   public class ApplicationContextConfig {
       @Bean
       public RestTemplate getRestTemplate(){
           return new RestTemplate();
       }
   }
   ```

# Rest相关知识

## @RequestBody

1. 用法

   @RequestBody注解可以将请求体中的json属性，绑定到具体的JOPO上

   ```
   @PostMapping("/payment/create")
   public CommonResult create(@RequestBody Payment payment) {
       int result = paymentService.create(payment);
       log.info("插入结果{}", result);
   
       if (result > 0) {
           return new CommonResult(200, "插入数据库成功", result);
       } else {
           return new CommonResult(444, "fail!");
       }
   }
   ```

# 抽取公共的部分

# Eureka

> 实质：就是一个key(服务名【spring.application.name】)-value(服务url)键值对

## 基础知识

1. 服务治理

   管理每个服务和其他服务之间的依赖关系

2. 服务注册

   Eureka采用了CS的设计架构，Eureka是注册中心，系统中的其他的微服务，使用Eureka的客户端连接到Eureka Server并维持**心跳连接**。

3. Eureka包含的两个组件

   1. Eureka Server

      微服务通过配置启动后，会向Eureka Server中注册。

      Eureka会存所有可用的服务节点信息

      界面可以观察到

   2. Eureka Client

      java客户端：内置的，使用轮询（round-robin)负载算法的负载均衡器

      发送心跳

## 安装Eureka Server

1. 新建module

   cloud-eureka-server7001

2. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2022</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-eureka-server7001</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.example</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

3. application.yml

```yaml
server:
  port: 7001

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url: 
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
```

4. 主启动类

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class, args);
    }
}
```

## 实例注册到eureka

### 注册服务提供者

1. 改pom

   ```xml
   <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId></dependency>
   ```

2. 写yml

   ```yml
   eureka:
     client:
       register-with-eureka: true
       fetch-registry: true
       service-url:
         defaultZone: http://localhost:7001/eureka
   ```

3. 主启动类

   ```java
   @MapperScan(value = "com.zwz.springcloud.dao")
   @SpringBootApplication
   @EnableEurekaClient
   public class PaymentMain8001 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentMain8001.class, args);
       }
   }
   ```

4. 服务名称

   Eureka上的服务名称就是yml中配置的spring.application.name

### 注册服务消费者

1. pom

   ```xml
   <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId></dependency>
   ```

2. yml

   ```yuml
   server:
     port: 80
   
   spring:
     application:
       name: cloud-payment-consumer
   
   eureka:
     client:
       register-with-eureka: true
       fetch-registry: true
       service-url:
         defaultZone: http://localhost:7001/eureka
   ```

   

3. 主启动

   @EnableEurekaClient

## Eureka集群

### Eureka集群的搭建

1. 原理

   互相注册，相互守望

2. 集群搭建

   - 新建出来一个7002 cloud-eureka-server7002

   - 修改hosts

     ```json
     127.0.0.1 eureka7001.com
     127.0.0.1 eureka7002.com
     127.0.0.1 eureka7003.com
     ```

   - yml文件的配置修改

     ```yaml
     server:
       port: 7001
     
     eureka:
       instance:
         hostname: eureka7001.com
       client:
         register-with-eureka: false
         fetch-registry: false
         service-url:
         //相互守望
           defaultZone: http://eureka7002.com:7002/eureka
     ```

3. 将服务注册到Eureka集群

   - 修改yml

     ```yaml
     eureka:
       client:
         register-with-eureka: true
         fetch-registry: true
         service-url:
         #集群版
           defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
     ```

### 服务提供者的集群搭建

1. 克隆一个module

2. 消费者修改controller

   ```java
   package com.atguigu.springcloud.controller;
   
   
   import com.zwz.springcloud.entities.CommonResult;
   import com.zwz.springcloud.entities.Payment;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PathVariable;
   import org.springframework.web.bind.annotation.RestController;
   import org.springframework.web.client.RestTemplate;
   
   import javax.annotation.Resource;
   
   @RestController
   @Slf4j
   public class OrderController {
   //    public static final String PAYMENT_URL = "http://localhost:8001";
       public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
   
       @Resource
       private RestTemplate restTemplate;
   
       @GetMapping("/consumer/payment/create")
       public CommonResult<Payment> create(Payment payment) {
           return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
       }
   
       @GetMapping("/consumer/payment/get/{id}")
       public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
           return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
       }
   }
   ```

   

3. @LoadBalanced修改RestTemple配置

   ```java
   package com.atguigu.springcloud.config;
   
   import org.springframework.cloud.client.loadbalancer.LoadBalanced;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.client.RestTemplate;
   
   @Configuration
   public class ApplicationContextConfig {
       @Bean
       @LoadBalanced
       public RestTemplate getRestTemplate(){
           return new RestTemplate();
       }
   }
   
   ```

## actuator微服务信息完善

1. application.yml

   ```yaml
   eureka:
     client:
       register-with-eureka: true
       fetch-registry: true
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
     instance:
       instance-id: payment8001
       prefer-ip-address: true
   ```

## 服务发现Discovery

1. 用途

   对于注册进入euraka里面的微服务，可以通过服务发现来获得该服务的信息

2. 使用方法

   - Controller

     ```java
     @Resource
     private DiscoveryClient discoveryClient;
     
     @GetMapping("/payment/discovery")
     public Object discovery() {
             List<String> services = discoveryClient.getServices();
             for (String service : services) {
                 log.info("******element: " + service);
             }
     
             List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
             for (ServiceInstance instance : instances) {
                 log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
             }
             return this.discoveryClient;
     }
     ```

   - 主启动类

     @EnableDiscoveryClient

     ```java
     @MapperScan(value = "com.zwz.springcloud.dao")
     @SpringBootApplication
     @EnableDiscoveryClient
     @EnableEurekaClient
     public class PaymentMain8001 {
         public static void main(String[] args) {
             SpringApplication.run(PaymentMain8001.class, args);
         }
     }
     ```

## Eureka自我保护机制

> 某时刻某一个微服务不可用了（心跳检测），Eureka不会立刻清除，依旧会对该微服务的信息进行保存

1. 如何禁止自我保护

- application.yml

  服务端

  ```yaml
  eureka:
   server:
   	enable-self-preservation: false
   	eviction-interval-timer-in-ms: 2000
   	#客户端向服务端发送心跳的时间间隔
   	lease-renewal-interval-in-seconds: 1
   	#服务端在收到最后一次心跳后的等待时间上限，超时将剔除掉服务
  	lease-expiration-duration-in-seconds: 2
  ```

  客户端

  


# Consul

## 简介

分布式服务发现和配置管理，由Go语言开发

## 安装

1. 解压安装包

   https://www.consul.io/downloadshttps://www.consul.io/downloads

2. 当前路径的控制台启动

   consul agent -dev

3. consul的web前端界面

   http://localhost:8500/

## 服务提供者注册到Consul

1. pom文件

   ```xml
    <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-consul-discovery</artifactId>
       </dependency>
   ```

   

2. Yml

   ```yaml
   server:
     port: 8006
   
   spring:
     application:
       name: consul-provider-payment
     cloud:
       consul:
         host: localhost
         port: 8500
         discovery:
           service-name: ${spring.application.name}
   ```

   

3. 主启动类

   ```java
   package springcloud;
   
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
   
   @MapperScan(value = "com.zwz.springcloud.dao")
   @SpringBootApplication
   @EnableDiscoveryClient
   public class PaymentMain8006 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentMain8006.class, args);
       }
   }
   ```

   

4. controller

    ```java
    @RestController
    @Slf4j
    public class PaymentController {
    
        @Value("${server.port}")
        private String serverPort;
    
        @GetMapping("/payment/consul")
        public String paymentInfo() {
            return "springcloud with consul: " + serverPort + "\t\t" + UUID.randomUUID().toString();
        }
    }
    
    ```

   

5. 验证

## 消费者注册到Consul

1. pom

   ```xml
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-consul-discovery</artifactId>
           </dependency>
   ```

   

2. yml

   ```yaml
   server:
     port: 80
   
   spring:
     application:
       name: consul-consumer-order
   
     cloud:
       consul:
         host: localhost
         port: 8500
         discovery:
           service-name: ${spring.application.name}
   ```

   

3. 主启动类

   ```java
   @SpringBootApplication
   @EnableDiscoveryClient
   public class OrderConsulMain80 {
       public static void main(String[] args) {
           SpringApplication.run(OrderConsulMain80.class, args);
       }
   }
   ```

   

4. 配置bean

   ```java
   import org.springframework.cloud.client.loadbalancer.LoadBalanced;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.web.client.RestTemplate;
   
   @Configuration
   public class ApplicationContextConfig {
       @Bean
       @LoadBalanced
       public RestTemplate getRestTemplate(){
           return new RestTemplate();
       }
   }
   ```

   

5. Controller

   ```java
   @RestController
   @Slf4j
   public class OrderConsulController {
       public static final String INVOKE_URL = "http://consul-provider-payment";
   
       @Resource
       private RestTemplate restTemplate;
   
       @GetMapping("/consumer/consul")
       public String paymentInfo() {
           String res = restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
           return res;
       }
   
   }
   ```

   

6. 测试

# CAP理论

## C consistency 强一致性

## A availability 可用性

## P partition tolerance 分区容错性

## 应用

AP - Eureka

CP - Zookeeper/Consul

# Ribbon

> 负载均衡+RestTemplate调用

主要是负载均衡服务调用

1. LB负载均衡是什么？

   将用户的请求平摊分配到多个服务上，从而达到系统的HA（高可用）

   Ribbon本地负载均衡，在调用微服务接口的时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务的调用

## Ribbon的使用

1. pom

```xml
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

这个本身就集成了ribbon:	spring-cloud-starter-netflix-ribbon

2. RestTemplate使用

   1. getForObject方法和getForEntity方法的区别

      - getForObject

        返回对象为响应体中的数据转成的对象，基本上可以理解为json

      - getForEntity

        返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等

        ```java
         @GetMapping("/consumer/payment/getForEntity/{id}")
            public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
                ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
                if (entity.getStatusCode().is2xxSuccessful()) {
                    return entity.getBody();
                } else {
                    return new CommonResult<>(444, "操作失败");
                }
            }
        ```

   2. postForObject方法和postForEntity方法

## IRule接口

- Ribbon自带的负载均衡实现类

  1. RoundRobinRule 轮询
  2. RandomRule 随机
  3. RetryRule 先按照轮询获取服务，如果服务获取失败会在指定时间内重试
  4. WeightedResponseTimeRule 对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择
  5. BestAvailableRule 选择一个并发量最小的服务
  6. AvailabilityFilteringRule 先过滤掉故障实例，再选择并发较小的实例
  7. ZoneAvoidanceRule 复合判断server所在区域的性能和server的可用性选择服务器

- 替换默认的负载均衡算法

  1. 自定义的Rule不能够被@ComponetScan注解所扫描到

  2. 通过注解的方式创建IRule具体对象

     ```java
     @Configuration
     public class MySelfRule {
         @Bean
         public IRule myRule(){
             return new RandomRule();
         }
     }
     ```

     

  3. 主启动类@RibbonClient注解

     ```java
     @RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
     @EnableEurekaClient
     @SpringBootApplication
     public class OrderMain80 {
         public static void main(String[] args) {
             SpringApplication.run(OrderMain80.class, args);
         }
     }
     ```

  ## 负载均衡算法

  1. 基本算法

     服务请求的总次数%服务器集群总数量 = 实际调用服务器位置下标

     每次重启服务后，rest接口技术从1开始

  2. RoundRobinRule的算法实现
  
  3. 手写负载均衡算法
  
     - 去掉@LoadBalanced注解
     - 自己编写LoadBalancer接口和实现类
  
     ```java
     public interface LoadBalancer {
         ServiceInstance instances(List<ServiceInstance> serviceInstances);
     }
     
     ```
  
     ```java
     import org.springframework.cloud.client.ServiceInstance;
     import org.springframework.stereotype.Component;
     
     import java.util.List;
     import java.util.concurrent.atomic.AtomicInteger;
     
     @Component
     public class MyLoadBalancer implements LoadBalancer {
         private AtomicInteger atomicInteger = new AtomicInteger(0);
     
         @Override
         public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
             Integer index = atomicInteger.getAndIncrement() % serviceInstances.size();
             return serviceInstances.get(index);
         }
     }
     
     ```
  
     
  
     - controller中通过@DiscoverClient和loadBalancer来实现调用
  
     ```java
      @Resource
      private LoadBalancer loadBalancer;
     
      @Resource
      private DiscoveryClient discoveryClient;
     
      @GetMapping("/consumer/payment/lb")
      public String getPaymentLB(){
            List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
             if(instances == null || instances.size()<=0){
                 return null;
             }
             ServiceInstance serviceInstance = loadBalancer.instances(instances);
             URI uri = serviceInstance.getUri();
             return restTemplate.getForObject(uri+"/payment/lb",String.class);
        }
     ```
  



# OpenFeign

> 客户端	服务调用	接口+注解=远程调用	服务接口绑定器

- 接口+注解

  微服务调用接口+@FeignClient

## OpenFeign使用

1. 新建cloud-consumer-feign-order80
2. pom.xml

```java
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.example</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

3. application.yml

```yaml
server:
  port: 80

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```

4. 主启动类

   要加入@EnableFeignClients

```java
@EnableFeignClients
@SpringBootApplication
public class OrderFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain80.class,args);
    }
}
```

5. 业务类

   1. 接口

      ```java
      @Component
      @FeignClient(value="CLOUD-PAYMENT-SERVICE")
      public interface PaymentFeignService {
      
          @GetMapping("/payment/get/{id}")
          CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
      
      }
      ```

   2. controller

      ```java
      import com.atguigu.springcloud.services.PaymentFeignService;
      import com.zwz.springcloud.entities.CommonResult;
      import com.zwz.springcloud.entities.Payment;
      import lombok.extern.slf4j.Slf4j;
      import org.springframework.web.bind.annotation.GetMapping;
      import org.springframework.web.bind.annotation.PathVariable;
      import org.springframework.web.bind.annotation.RestController;
      
      import javax.annotation.Resource;
      
      @RestController
      @Slf4j
      public class controller {
          @Resource
          private PaymentFeignService paymentFeignService;
      
          @GetMapping("/consumer/payment/get/{id}")
          public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
              return paymentFeignService.getPaymentById(id);
          }
      }
      
      ```

      ## OpenFeign超时控制
      
      由于feign在内部是由ribbon负责rest调用控制的，因此超时控制也是通过配置ribbon来实现
      
      1. application.yml
      
      ```yaml
      ribbon:
        #两端建立tcp连接所用的时间
        ReadTimeout: 5000
        #从服务端读取可用资源所需要的的时间
        ConnectTimeout: 5000
      ```
      
      # OpenFeign日志增强
      
      - 日志打印功能
      
        使用@Configuration的方式来配置
      
      ```java
      import feign.Logger;
      import org.springframework.context.annotation.Bean;
      import org.springframework.context.annotation.Configuration;
      
      @Configuration
      public class FeignConfig {
          @Bean
          Logger.Level feignLoggerLevel(){
              return Logger.Level.FULL;
          }
      }
      ```
      
      ​			application.yml
      
      ```yaml
      logging:
      	level:
      		com.zwz.service.PaymentFeginService: debug
      ```
      
      

# Hystrix断路器

- 服务雪崩

  多个微服务之间的调用，A调用B和C，B和C又调用了别的服务，这就是——“扇出”，如果扇出的链路上有的微服务调用的时间很长或者出现了不可用，对微服务A的调用就会占用越来越多的系统资源，进而造成了系统崩溃，产生所谓的“雪崩效应”

## Hystrix是用来处理分布式系统的延迟和容错的开源库

- 保证一个依赖出现问题的情况时，不会导致整个服务调用链失败，避免级联故障造成的“雪崩效应”

## 官网

github

## 服务降级-fallback

- 对方系统不可用了，需要给出一个兜底的解决方案

## 服务熔断-break

- 服务承受的访问压力到达极限，使用服务降级的方法返回友好提示，类似保险丝

## 服务限流-flowlimit

- 秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行

## 使用Hystrix

1. 新建module：cloud-provider-hystrix-payment8001

2. pom.xml

   ```xml
   <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
   </dependency>
   ```

3. application.yml

   ```yaml
   server:
     port: 8001
   
   spring:
     application:
       name: cloud-provider-hystrix-payment
   
   eureka:
     client:
       register-with-eureka: true
       fetch-registry: true
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
   ```

   

4. 主启动类

   ```java
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
   
   @EnableEurekaClient
   @SpringBootApplication
   public class PaymentHystrixMain8001 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentHystrixMain8001.class, args);
       }
   }
   
   ```

5. 业务类

   service

   ```java
   import org.springframework.stereotype.Service;
   
   import java.util.concurrent.TimeUnit;
   
   @Service
   public class PaymentService {
       public String paymentInfo_Ok(Integer id) {
           return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id : " + id;
       }
   
       public String paymentInfo_TimeOut(Integer id) {
           try {
               TimeUnit.SECONDS.sleep(3);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK,id: " + id;
       }
   }
   
   ```

   controller

   ```java
   import com.zwz.springcloud.service.PaymentService;
   import lombok.extern.slf4j.Slf4j;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PathVariable;
   import org.springframework.web.bind.annotation.RestController;
   
   import javax.annotation.Resource;
   
   @RestController
   @Slf4j
   public class PaymentController {
       @Resource
       private PaymentService paymentService;
   
       @Value("${server.port}")
       private String serverPort;
   
       @GetMapping("/payment/hystrix/ok/{id}")
       public String paymentInfo_Ok(@PathVariable("id") Integer id){
           String result = paymentService.paymentInfo_Ok(id);
           log.info("*****result: "+result);
           return result;
       }
   
       @GetMapping("/payment/hystrix/timeout/{id}")
       public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
           String result = paymentService.paymentInfo_TimeOut(id);
           log.info("*****result: "+result);
           return result;
       }
   }
   
   ```

## 高并发测试

- JMETER

  1. 设置线程数量-循环次数

     <img src="D:\learnNotes\springcloud.assets\image-20220503205926795.png" alt="image-20220503205926795" style="zoom:200%;" />

  2. 设置http请求

     ![image-20220503210012907](D:\learnNotes\springcloud.assets\image-20220503210012907.png)

- cloud-consumer-feign-hystrix-order80

## 降级容错解决的维度要求

1. 超时

   对方超时，调用者不能一直等待卡死，要有服务降级

2. 宕机

   对方宕机，调用者不能一直等待卡死，必须有服务降级

3. 调用方故障

   对方ok，调用者自己出故障，比如自己等待的时间小于服务提供者，自己处理降级

## 服务降级

> 服务降级要解决的三个问题：1.异常；2.宕机；3.超时

### 服务提供方的服务降级配置

1. 服务降级可以解决两个问题，**超时和宕机**(方法抛出异常)

2. 降级配置

   - **@HystrixCommand**加到服务提供者（8001）的业务类的方法上

     ```java
     @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
                 @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="30000")
         })
         public String paymentInfo_TimeOut(Integer id) {
             try {
                 TimeUnit.SECONDS.sleep(3);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout,id: " + id;
         }
     
         public String paymentInfo_TimeOutHandler(Integer id) {
             return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOutHandler,id: " + id;
         }
     ```

   - 主启动类@EnableCircuitBreaker

     ```java
     import org.springframework.boot.SpringApplication;
     import org.springframework.boot.autoconfigure.SpringBootApplication;
     import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
     import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
     
     @EnableEurekaClient
     @SpringBootApplication
     @EnableCircuitBreaker
     public class PaymentHystrixMain8001 {
         public static void main(String[] args) {
             SpringApplication.run(PaymentHystrixMain8001.class, args);
         }
     }
     ```

### 客户端的服务降级

#### 对于controller的降级处理

1. application.yml

   ```yaml
   feign:
     hystrix:
       enabled: true
   ```

2. 主启动

   @EnableHystrix

3. 业务类@HystrixCommand

   ```java
   @GetMapping("/consumer/payment/hystrix/timeout/{id}")
       @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
               @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
       })
       public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
           String result = paymentService.paymentInfo_TimeOut(id);
           log.info("*****result: "+result);
           return result;
       }
   
       public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
           return "我是消费者80，对方支付系统繁忙请10秒钟后再试";
       }
   ```


#### 对于FeignClient的降级处理

- FeignClient提供的接口级别的微服务调用

  1. 实现Feign接口，实现fallback方法

     ```java
     @Component
     public class PaymentFallbackService implements PaymentService {
         @Override
         public String paymentInfo_Ok(Integer id) {
             return "---------------paymentFallbackService:paymentInfo_Ok----------------";
         }
     
         @Override
         public String paymentInfo_TimeOut(Integer id) {
             return "---------------paymentFallbackService:paymentInfo_TimeOut----------------";
         }
     }
     ```

  2. yaml

     feign:
       hystrix:
         enabled: true

  3. Feign接口类上的@FeignClient注解，做fallback属性配置

     ```java
     @Component
     @FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
     public interface PaymentService {
         @GetMapping("/payment/hystrix/ok/{id}")
         public String paymentInfo_Ok(@PathVariable("id") Integer id);
     
         @GetMapping("/payment/hystrix/timeout/{id}")
         public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
     }
     ```

     

### 全局的服务降级方法

- 抽象出一个全局的fallback，避免代码膨胀

- Controller类上加@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")

  ```java
  import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
  import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
  import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
  import com.zwz.springcloud.service.PaymentService;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RestController;
  
  import javax.annotation.Resource;
  
  @RestController
  @Slf4j
  @DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
  public class PaymentController {
      @Resource
      private PaymentService paymentService;
  
      @GetMapping("/consumer/payment/hystrix/ok/{id}")
      public String paymentInfo_Ok(@PathVariable("id") Integer id){
          String result = paymentService.paymentInfo_Ok(id);
          log.info("*****result: "+result);
          return result;
      }
  
      @GetMapping("/consumer/payment/hystrix/timeout/{id}")
  //    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
  //            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
  //    })
      @HystrixCommand
      public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
          String result = paymentService.paymentInfo_TimeOut(id);
          log.info("*****result: "+result);
          return result;
      }
  
      public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
          return "我是消费者80，对方支付系统繁忙请10秒钟后再试";
      }
  
      public String payment_Global_FallbackMethod(){
          return "全局fallback";
      }
  }
  
  ```




## 服务熔断

> 保险丝，压力到达上限，拉闸，调用服务降级方法返回
>
> 服务降级-》熔断-》恢复调用链路

- 熔断机制是应对雪崩效应的一种微服务链路保护机制

  当扇出链路的某个微服务出错不可用或者响应时间太长时-》服务降级-》熔断-》快速返回错误响应信息

  -》检测到该节点的微服务调用响应正常后-》恢复调用链路

- Spring Cloud框架中，熔断机制的实现

  1. Hystrix实现
  2. Hystrix会监控微服务间调用的状况
  3. 达到调用失败的阈值，默认5秒内20次调用失败，就会启动熔断机制。
  4. 熔断机制的注解还是@HystrixCommand

### 实操

1. 改造服务端8001的PaymentService

```java
@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled" ,value = "true"),/*是否开启断路器*/
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold" ,value = "10"),/*请求次数*/
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds" ,value = "10000"),/*时间窗口*/
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage" ,value = "60")/*失败率达到多少后跳闸*/
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("***** id 不能为负数");
        }
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+ UUID.randomUUID().toString();
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试。id = "+id;
    }
```

2. 参数配置去哪里找?

   https://github.com/Netflix/Hystrix/wiki/Configuration

### 总结

1. 熔断类型

   - 熔断打开

     请求不再调用当前服务，内部设置时钟一般为MTTR，当打开时长达到所设时钟就进入半熔断状态

   - 熔断关闭

     熔断关闭不会对服务进行熔断

   - 熔断半开

     部分请求根据规则调用当前服务，如果请求成功且符合规则就可以从熔断中恢复到正常

2. 流程图

   ![img](D:\learnNotes\springcloud.assets\hystrix-command-flow-chart.png)





# 路由网关

## Gateway

1. WebFlux是一个非阻塞的web框架

## 核心概念

1. Route(路由)

   路由是构建网关的基本模块，它由ID，目标URI，一系列的断言和过滤器组成，如果断言为true就匹配该路由

2. Predicate(断言)

   开发人员可以匹配HTTP请求中的所有内容（例如请求头或者请求参数），如果请求与断言相匹配就进行路由

3. Filter（过滤）

   GatewayFilter实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改

- web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制
- predicate就是我们的匹配条件；而filter，就可以理解为一个无所不能的拦截器。
- 有了predicate和filter以及URI，就可以实现一个具体的路由了

## 使用Springcloud-Gateway

1. 新建module

2. pom.xml

   ```xml
   <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   ```

3. application.yml

   ```yaml
   server:
     port: 9527
   
   spring:
     application:
       name: cloud-gateway
     cloud:
       gateway:
         routes:
           - id: payment_routh
             uri: http://localhost:8001
             predicates:
               - Path=/payment/get/**
   
           - id: payment_routh2
             uri: http://localhost:8001
             predicates:
               - Path=/payment/lb/**
   eureka:
     instance:
       hostname: cloud-gateway-service
     client:
       service-url:
         register-with-eureka: true
         fetch-registry: true
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
   ```

4. 主启动类

   ```java
   @SpringBootApplication
   @EnableEurekaServer
   public class EurekaMain7002 {
       public static void main(String[] args) {
           SpringApplication.run(EurekaMain7002.class, args);
       }
   }
   ```


## Gateway配置

### 一般配置

1. 在配置文件中yml配置

2. 在代码中注入RouteLocator配置

   ```java
   import org.springframework.cloud.gateway.route.RouteLocator;
   import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   @Configuration
   public class GateWayConfig {
       @Bean
       public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
           RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
   
           routes.route("path_route_zwz", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
           return routes.build();
       }
   }
   ```

   

### 配置动态路由

- 通过网关来访问微服务

- 动态路由

  防止写死路由导致对于微服务弹缩的不感知

  默认情况下Gateway会根据注册中心注册的服务列表，以注册中心上微服务名为路径创建动态路由进行转发，从而实现动态路由的功能

- application.yml的配置

  ```yaml
  spring:
    application:
      name: cloud-gateway
    cloud:
      gateway:
        discovery:
          locator:
            enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
        routes:
          - id: payment_routh
  #          uri: http://localhost:8001 #固定的方式配置路由
            uri: lb://CLOUD-PAYMENT-SERVICE
            predicates:
              - Path=/payment/get/**
  ```

### Predicate

1. 常用的Predicate

   https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-request-predicates-factories

   - After Route Predicate

     ```yaml
     spring:
       application:
         name: cloud-gateway
       cloud:
         gateway:
           discovery:
             locator:
               enabled: true #开启从注册中心动态创建路由的功能，利用微服务名进行路由
           routes:
             - id: payment_routh
               uri: lb://CLOUD-PAYMENT-SERVICE
               predicates:
                 - Path=/payment/get/**
                 - After=2022-05-08T15:32:21.920+08:00[Asia/Shanghai]
     ```

   - Cookie Route Predicate

     ```yaml
     spring:
       cloud:
         gateway:
           routes:
           - id: cookie_route
             uri: https://example.org
             predicates:
             - Cookie=chocolate, ch.p
     ```

     

   - Header route predicate

     ```yaml
     predicates:
             - Header=X-Request-Id, \d+
     ```

### Filter

- 生命周期

  1. pre
  2. post

- 种类

  1. GatewayFilter
  2. GlobalFilter

- 责任链模式

- 自定义过滤器

  > 全局日志记录、统一网关鉴权

  1. 实现两个接口GlobalFilter,Ordered，并注入到Spring容器

  ```java
  @Component
  @Slf4j
  public class MyLogGateWayFilter implements GlobalFilter, Ordered {
      @Override
      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("*******come in MyLogGateWayFILTER********"+new Date());
          String uname = exchange.getRequest().getQueryParams().getFirst("uname");
          if(uname == null){
              log.info("*****用户名为null,非法用户，不通过********");
              exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
              return exchange.getResponse().setComplete();
          }
          return chain.filter(exchange);
      }
  
      @Override
      public int getOrder() {
          return 0;
      }
  }
  ```

  

  

# 配置中心

## 配置问题

- 太多的module，每个都有自己配置，抽取相同的配置（比如数据库配置）
- 生产场景，开发dev、发布release等

## 用途

1. 集中管理配置文件
2. 分环境配置，dev/test/prod/beta/release
3. 运行期间动态调整配置，不再需要在每个服务部署的机器上编写配置文件，服务从配置中心拉去自己的配置信息
4. 配置发生变化是，服务不重启即可感知
5. 配置信息通过rest接口的形式暴露



## 搭建配置中心

### Config服务端的配置和测试

1. github新建一个名为springcloud-config的新Repository

2. 建立module: cloud-config-center-3344

   - pom.xml

     ```xml
     <dependency>
                 <groupId>org.springframework.cloud</groupId>
                 <artifactId>spring-cloud-config-server</artifactId>
             </dependency>
     ```

   - application.yml

     ```yaml
     server:
       port: 3344
     
     spring:
       application:
         name: cloud-config-center
       cloud:
         config:
           server:
             git:
               uri: https://github.com/BigBearZhou1/my_config_server.git
               search-paths:
                 - my_config_server
           label: main
     
     eureka:
       client:
         service-url:
           defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
     ```
     
   - 主启动类
   
     ```java
     @SpringBootApplication
     @EnableConfigServer
     public class ConfigCenterMain3344 {
         public static void main(String[] args) {
             SpringApplication.run(ConfigCenterMain3344.class, args);
         }
     }
     ```
   
   - 添加映射到host
   
     127.0.0.1 config-3344.com
   
   - 浏览器访问：http://localhost:3344/main/config-dev.yml

## 配置中心客户端搭建

1. cloud-config-client-3355

   - pom

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
   ```

   - **bootstrap.yml**

     application.yml是用户级的资源配置

     bootstrap.yml是系统级的，优先级更高

     bootstrap.yml先从springcloud的上下文中获取config-server中的配置，之后和application.yml中的配置项合并成程序中的最终配置

   ```
   server:
     port: 3355
   
   spring:
     application:
       name: config-client
     cloud:
       config:
         label: main
         name: config
         profile: dev
         uri: http://localhost:3344
   
   eureka:
     client:
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
   ```

   - 主启动类

   ```
   @EnableEurekaClient
   @SpringBootApplication
   public class ConfigClientMain3355 {
       public static void main(String[] args) {
           SpringApplication.run(ConfigClientMain3355.class,args);
       }
   }
   ```

   - 业务类

   ```
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   @RefreshScope
   @RestController
   public class ConfigClientController {
       @Value("${config.info}")
       private String configInfo;
   
       @GetMapping("/configInfo")
       public String getConfigInfo(){
           return configInfo;
       }
   }
   ```

2. 发送命令手动刷新配置客户端的配置

   curl -X POST "http://localhost:3355/actuator/refresh"

## 配置读取规则

/{label}/{application}-{profile}.yml



# BUS消息总线

1. 概念

- 轻量级的消息代理来构建一个共用的消费主题，系统中所有的微服务都会订阅这个主题
- 由于该主题中生产的消息会被所有实例监听和消费，所以称为总线。
- 任何一个服务都可以通过这个总线主题，广播需要被别的服务知道的消息

## RabbitMQ

1. 启动rabbitMQ

   RabbitMQ server-start

2. http://localhost:15672/

   guest guest

3. 关闭rabbitMQ

   RabbitMQ server-stop

## 同步配置消息

1. 触发客户端

   /bus/refresh 触发一个客户端；

   客户端通过bus消息总线，通知到其他的客户端去刷新配置信息；

2. 触发服务端

   /bus/refresh触发config-server；

   config-server通知到其他的服务；

   更推荐这个方式

## config-server&RabbitMQ

1. pom.xml

   - 客户端和服务端都要加上配置

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-bus-amqp</artifactId>
   </dependency>
   ```

2. application.yml

   - server

   ```
   spring:
    rabbitmq:
     host: localhost
     port: 15672
     username: guest
     password: guest
   
   management:
     endpoints:
       web:
         exposure:
           include: 'bus-refresh'
   ```

   - client

   ```yaml
   spring:
     rabbitmq:
       host: localhost
       port: 5672
       username: guest
       password: guest
   
   # 暴露监控端点
   management:
     endpoints:
       web:
         exposure:
           include: "*"
   ```

# SpringCloud Stream 消息驱动

> 通过Stream来操作各种MQ，他就是一个适配器

## 概念

- input和output与Spring Cloud Stream 的binder做绑定
- 仅支持RabbitMQ和kafka

## 生产者

1. 新建module

   cloud-stream-rabbitmq-provider8801

2. pom.xml

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-devtools</artifactId>
           <scope>runtime</scope>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

3. application.yml

   ```yaml
   server:
     port: 8801
   
   spring:
     application:
       name: cloud-stream-provider
     cloud:
       stream:
         binders:
           defaultRabbit:
             type: rabbit
             environment:
               spring:
                 rabbitmq:
                   host: localhost
                   port: 5672
                   username: guest
                   password: guest
         bindings:
           output:
             destination: studyExchange
             content-type: application/json
             binder: {defaultRabbit}
   
   eureka:
     client:
       register-with-eureka: true
       fetch-registry: true
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
   ```

4. MainClass

   ```java
   @EnableEurekaClient
   @SpringBootApplication
   public class StreamMQMain8801 {
       public static void main(String[] args) {
           SpringApplication.run(StreamMQMain8801.class, args);
       }
   }
   ```

5. service

   - @EnableBinding(Source.class)
   - MessageBuilder.withPayload(T).Build

   ```java
   import com.cloud.service.IMessageProvider;
   import org.springframework.cloud.stream.annotation.EnableBinding;
   import org.springframework.cloud.stream.messaging.Source;
   import org.springframework.messaging.MessageChannel;
   import org.springframework.messaging.support.MessageBuilder;
   import org.springframework.stereotype.Service;
   
   import javax.annotation.Resource;
   import java.util.UUID;
   
   @EnableBinding(Source.class)
   public class MessageProviderImpl implements IMessageProvider {
   
       @Resource
       private MessageChannel output;
   
       @Override
       public String send() {
           String uuid = UUID.randomUUID().toString();
           output.send(MessageBuilder.withPayload(uuid).build());
           System.out.println(String.format("serial id = %s", uuid));
           return uuid;
       }
   }
   ```

## 消费者

1. module

   cloud-stream-rabbitmq-consumer8802

   cloud-stream-rabbitmq-consumer8803

2. pom

   同provider

3. application.yml

   ```yaml
   bindings:
     input:
       destination: studyExchange
       content-type: application/json
       binder: {defaultRabbit}
   ```

4. service

   - @EnableBinding(Sink.class)
   - @StreamListener(Sink.INPUT)
     - Message<T>
       - getPayload

   ```java
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.cloud.stream.annotation.EnableBinding;
   import org.springframework.cloud.stream.annotation.StreamListener;
   import org.springframework.cloud.stream.messaging.Sink;
   import org.springframework.messaging.Message;
   import org.springframework.stereotype.Component;
   
   @Component
   @EnableBinding(Sink.class)
   public class ReceiveMsgListenerController {
       @Value("${server.port}")
       private String serverPort;
   
       @StreamListener(Sink.INPUT)
       public void input(Message<String> message) {
           System.out.println("consumer 1 , receive message is : " + message.getPayload() + "\t port: " + serverPort);
       }
   }
   ```

## 分组

- 不同组可以消费同一个信息
- 同一个组一个消息只能被组内其中一个消费者消费
- 分组来避免重复消费消息

### 自定义分组配置

1. 修改yaml

   - group

   ```yaml
   spring:
     cloud:
       stream:
         bindings:
           input:
             group: groupA
   ```

### 持久化

- 加了group就会有持久化
- 当所有的消费应用停机时，生产应用的发送消息会被暂存，当消费者启动时会拿到宕机期间的消息



# SpringCloud Alibaba / Nacos

## Nacos

1. Nacos是Name,Configuration和service的简写

2. 注册中心和配置中心的组合

3. 官网

   https://github.com/alibaba/Nacos

   https://nacos.io/zh-cn/index.html

## 安装

1. 下载nacos的server包

   https://github.com/alibaba/nacos/releases/tag

2. 解压缩

   E:\work\nacos\nacos-server-2.1.0\nacos\bin

3. 运行startup.cmd

   修改成set MODE="standalone"否则会报错

4. 登录：http://localhost:8848/nacos/

   nacos

   nacos

## 服务提供者注册

1. 新建Module:

   cloudalibaba-provider-payment9001

2. 新建pom

   父pom.xml

   ```xml
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-alibaba-dependencies</artifactId>
       <version>2.1.0.RELEASE</version>
       <type>pom</type>
       <scope>import</scope>
   </dependency>
   ```

   子pom.xml

   ```xml
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
   </dependency>
   ```

3. application.yml

   ```yaml
   server:
     port: 9001
   
   spring:
     application:
       name: nacos-payment-provider9001
     cloud:
       nacos:
         discovery:
           server-addr: 127.0.0.1:8848
   
   management:
     endpoints:
       web:
         exposure:
           include: "*"
   ```

4. 主启动类

   ```java
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
   
   @SpringBootApplication
   @EnableDiscoveryClient
   public class PaymentMain9001 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentMain9001.class, args);
       }
   }
   ```

## 消费者

1. 新建module

   cloudalibaba-consumer-nacos-order83

2. pom

   同服务提供者

3. application.yml

   ```yaml
   server:
     port: 83
   
   spring:
     application:
       name: nacos-payment-consumer83
     cloud:
       nacos:
         discovery:
           server-addr: 127.0.0.1:8848
   
   service-url:
     nacos-user-service: http://nacos-payment-provider
   ```

4. 主启动类

   同服务提供者

5. 业务类

   - RestTmplateConfig，配置RestTemplate，其中一定要配置@LoadBalance

     ```java
     import org.springframework.cloud.client.loadbalancer.LoadBalanced;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.web.client.RestTemplate;
     
     @Configuration
     public class ApplicationContextConfig {
     
         @Bean
         @LoadBalanced
         public RestTemplate getRestTemplate(){
             return new RestTemplate();
         }
     }
     ```

   - Controller类

     ```java
     import org.springframework.beans.factory.annotation.Value;
     import org.springframework.http.ResponseEntity;
     import org.springframework.web.bind.annotation.GetMapping;
     import org.springframework.web.bind.annotation.PathVariable;
     import org.springframework.web.bind.annotation.RestController;
     import org.springframework.web.client.RestTemplate;
     
     import javax.annotation.Resource;
     
     @RestController
     public class NacosConsumerController {
         @Resource
         private RestTemplate restTemplate;
     
         @Value("${service-url.nacos-user-service}")/*从配置的application.yml中读取*/
         private String paymentUrl;
     
     
         @GetMapping("/consumer/payment/nacos/{id}")
         public String getPaymentById(@PathVariable("id") Integer id) {
             String url = paymentUrl + "/payment/nacos/" + id;
             ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
             if(responseEntity.getStatusCode().is2xxSuccessful()){
                 return responseEntity.getBody();
             }else{
                 return "false";
             }
     
         }
     }
     ```



## Nacos的优势

### 什么是CAP？

- C——是所有的节点在同一时间看到的数据都是一致的；
- A——的定义是所有的请求到会受到相应，**高可用**

### Nacos可以切换AP和CP



## Nacos配置中心

###  cloudalibaba-config-nacos-client3377

1. pom

   ```xml
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
   </dependency>
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
   </dependency>
   ```

2. yaml配置文件

   bootstrap.yml

   ```yaml
   server:
     port: 3377
   
   spring:
     application:
       name: nacos-config-client
     cloud:
       nacos:
         discovery:
           server-addr: 127.0.0.1:8848
         config:
           server-addr: 127.0.0.1:8848
           file-extension: yaml
   ```

   application.yml

   ```yaml
   spring:
     profiles:
       active: dev
   ```

3. 主启动类

   ```java
   @EnableDiscoveryClient
   @SpringBootApplication
   public class NacosConfigClientMain3377 {
       public static void main(String[] args) {
           SpringApplication.run(NacosConfigClientMain3377.class, args);
       }
   }
   ```

4. controller

   @RefreshScope自动刷新配置

   ```java
   @RestController
   @RefreshScope//支持动态刷新
   public class ConfigClientController {
       @Value("${config.info}")
       private String configInfo;
   
       @GetMapping("/config/info")
       public String getConfigInfo(){
           return configInfo;
       }
   }
   ```

### dataid

> \${prefix}-\${spring.profiles.active}.\${file-extension}

- `prefix` 默认为 `spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。
- `spring.profiles.active` 即为当前环境对应的 profile，详情可以参考 [Spring Boot文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html#boot-features-profiles)。 **注意：当 `spring.profiles.active` 为空时，对应的连接符 `-` 也将不存在，dataId 的拼接格式变成 `${prefix}.${file-extension}`**
- `file-exetension` 为配置内容的数据格式，可以通过配置项 `spring.cloud.nacos.config.file-extension` 来配置。目前只支持 `properties` 和 `yaml` 类型。

### 在界面上添加配置文件

![image-20220612221253091](D:\learnNotes\springcloud.assets\image-20220612221253091.png)



### 命名空间namespace、分组group、dataid

1. 这样做的原因是尽量让同一个组中的微服务互相调用，可以提高效率。比如广州机房的微服务间相互调用，杭州的微服务相互调用

2. 实现分组

   配置加信息

   ```
   server:
     port: 3377
   
   spring:
     application:
       name: nacos-config-client
     cloud:
       nacos:
         discovery:
           server-addr: 127.0.0.1:8848
         config:
         	group: test
           server-addr: 127.0.0.1:8848
           file-extension: yaml
   ```

3. 实施namespace

### nacos集群和持久化配置

- 解决的问题
  1. 单节点宕机 
  2. 配置持久化
- derby切换到mysql

## 配置nacos集群

### 创建docker网段

**docker network create --driver bridge --subnet 172.18.0.0/16 myNetwork**

### mysql数据库配置

1. 创建docker的mysql容器，版本是5.7

   ```shell
   docker run --network myNetwork --ip 172.18.0.2 -p 3306:3306 -v /mydata/mysql/conf:/etc/mysql -v  /mydata/mysql/log:/var/log/mysql -v /mydata/mysql/data:/var/lib/mysql --name mysql37 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
   ```

   - 创建database "nacos_config"

   ```shell
   docker exec -it 4c17d900a4f0 bash
   
   mysql -u root -p
   
   mysql> create database nacos_config
       -> ;
   Query OK, 1 row affected (0.00 sec)
   
   mysql> show databases
       -> ;
   +--------------------+
   | Database           |
   +--------------------+
   | information_schema |
   | mysql              |
   | nacos_config       |
   | performance_schema |
   | sys                |
   +--------------------+
   5 rows in set (0.00 sec)
   ```

2. 在nacos_config库中执行脚本

   ```shell
   [root@localhost scripts]# docker cp nacos-mysql.sql 4c17d900a4f0:/tmp
   [root@localhost scripts]# docker exec -it 4c17d900a4f0 bash
   
   mysql> source /tmp/nacos-mysql.sql
   
   
   mysql> show tables;
   +------------------------+
   | Tables_in_nacos_config |
   +------------------------+
   | config_info            |
   | config_info_aggr       |
   | config_info_beta       |
   | config_info_tag        |
   | config_tags_relation   |
   | group_capacity         |
   | his_config_info        |
   | permissions            |
   | roles                  |
   | tenant_capacity        |
   | tenant_info            |
   | users                  |
   +------------------------+
   12 rows in set (0.00 sec)
   
   mysql>
   
   ```

### 创建nacos集群

1. 创建一个普通的nacos容器，然后从里面把配置拷到宿主机，便于之后集群时做映射，用同一份配置文件

   ```shell
   mkdir -p /root/nacos/conf
   mkdir -p /root/nacos/logs/nacos-server-3333
   mkdir -p /root/nacos/logs/nacos-server-4444
   mkdir -p /root/nacos/logs/nacos-server-5555
   
   docker run -d --name=nacos -p 8848:8848 nacos/nacos-server
   docker cp nacos:/home/nacos/conf /root/nacos/conf
   docker stop nacos && docker rm nacos
   ```
   
   
   
2. 3333

   ```shell
   docker run -d \
   --network myNetwork --ip 172.18.0.4 --name nacos-server-3333 \
   -e MODE=cluster \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   -e MYSQL_SERVICE_HOST=172.18.0.2 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=123456 \
   -e MYSQL_SERVICE_DB_NAME=nacos_config \
   -e NACOS_SERVER_PORT=8848 \
   -e TZ="Asia/Shanghai" \
   -e NACOS_SERVERS="172.18.0.4:8848 172.18.0.5:8848 172.18.0.6:8848" \
   -e NACOS_SERVER_IP=172.18.0.4 \
   -e JVM_XMS=256m -e JVM_XMX=512m  \
   -v /root/nacos/logs/nacos-server-3333:/home/nacos/logs \
   -v /root/nacos/conf:/home/nacos/conf \
   -p 3333:8848 \
   nacos/nacos-server
   ```

   

3. 4444

   ```shell
   docker run -d \
   --network myNetwork --ip 172.18.0.5 --name nacos-server-4444 \
   -e MODE=cluster \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   -e MYSQL_SERVICE_HOST=172.18.0.2 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=123456 \
   -e MYSQL_SERVICE_DB_NAME=nacos_config \
   -e NACOS_SERVER_PORT=8848 \
   -e TZ="Asia/Shanghai" \
   -e NACOS_SERVERS="172.18.0.4:8848 172.18.0.5:8848 172.18.0.6:8848" \
   -e NACOS_SERVER_IP=172.18.0.5 \
   -e JVM_XMS=256m -e JVM_XMX=512m  \
   -v /root/nacos/logs/nacos-server-4444:/home/nacos/logs \
   -v /root/nacos/conf:/home/nacos/conf \
   -p 4444:8848 \
   nacos/nacos-server
   ```

   

4. 5555

   ```shell
   docker run -d \
   --network myNetwork --ip 172.18.0.6 --name nacos-server-5555 \
   -e MODE=cluster \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   -e MYSQL_SERVICE_HOST=172.18.0.2 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=123456 \
   -e MYSQL_SERVICE_DB_NAME=nacos_config \
   -e NACOS_SERVER_PORT=8848 \
   -e NACOS_SERVERS="172.18.0.4:8848 172.18.0.5:8848 172.18.0.6:8848" \
   -e NACOS_SERVER_IP=172.18.0.6 \
   -e JVM_XMS=256m -e JVM_XMX=512m  \
   -v /root/nacos/logs/nacos-server-5555:/home/nacos/logs \
   -v /root/nacos/conf:/home/nacos/conf \
   -p 5555:8848 \
   nacos/nacos-server
   ```

5. http://192.168.3.14:3333/nacos/

   ![image-20220616232256825](D:\learnNotes\springcloud.assets\image-20220616232256825.png)



### Nginx搭建

1. 为什么使用nginx

   - 对外暴露一个统一的IP
   - 用来做负载均衡器

2. docker pull nginx

3. docker运行nginx为了获取配置文件

   ```shell
   docker run \
   --network myNetwork --ip 172.18.0.7 --name nginx1111 \
   -p 1111:1111 \
   -d nginx
   ```

4. 修改default.conf的配置文件

   将docker中的/etc/nginx/conf.d/default.conf文件拷贝出来

   **upstream cluster一定是宿主机ip**

   ```shell
   upstream cluster{
   	server 192.168.3.14:3333;
   	server 192.168.3.14:4444;
   	server 192.168.3.14:5555;
   }
   server{
   	listen 1111;
   	...
   	location / {
   		#root html;
   		#index ...
   		proxy_pass http://cluster;
   	}
   }
   ```

5. 再次启动docker

   ```shell
   docker run --network myNetwork --ip 172.18.0.7 --name nginx1111 -p 1111:1111 -v /root/nginx/conf/default.conf:/etc/nginx/conf.d/default.conf -v /root/nginx/log:/var/log/nginx -d nginx
   ```

6. 验证

   http://192.168.3.14:1111/nacos

   ![image-20220618235320308](D:\learnNotes\springcloud.assets\image-20220618235320308.png)



# Sentinel

> 流量控制、速率配置、服务熔断、服务降级
>
> 界面化的细粒度统一配置

# Sentinel的特征

- **丰富的应用场景**

  秒杀（突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等

- **完备的实时监控**

- **广泛的开源生态**

  可以和Spring Cloud、dubbo、gRPC的整合

  只需要引入相应的依赖并进行简单的配置就可以快速的接入Sentinel

- **完善的SPI拓展点**

## 安装和配置

1. docker pull

   ```shell
   docker pull bladex/sentinel-dashboard
   ```

2. docker run

   ```shell
   docker run --name sentinel -e TZ="Asia/Shanghai" -d -p 8858:8858 -d  bladex/sentinel-dashboard
   ```

3. 界面

   http://192.168.3.14:8858/

## 微服务搭建

1. new module

   cloudalibaba-sentinel-service8401

2. pom.xml

   ```xml
   <dependencies>
       <dependency>
           <groupId>com.alibaba.cloud</groupId>
           <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-openfeign</artifactId>
       </dependency>
       <dependency>
           <groupId>com.alibaba.cloud</groupId>
           <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-devtools</artifactId>
           <scope>runtime</scope>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
   
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

3. application.yml

   **一定要注意clinetIp要配置成本机的ip，否则没有办法监控成功**

   ```yaml
   server:
     port: 8401
   
   spring:
     application:
       name: cloudalibaba-sentinel-service
     cloud:
       nacos:
         discovery:
           server-addr: 192.168.3.14:1111
       sentinel:
         transport:
           dashboard: 192.168.3.14:8858
           #sentinel会启动一个http server与dashboard进行通信，这个http server要占用8719这个端口
           port: 8719
           clientIp: 192.168.3.6
   management:
     endpoints:
       web:
         exposure:
           include: "*"
   ```

4. 主启动类

   ```java
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;
   import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
   
   @EnableDiscoveryClient
   @SpringBootApplication
   public class MainApp8401 {
       public static void main(String[] args) {
           SpringApplication.run(MainApp8401.class, args);
       }
   }
   ```

5. controller

   sentinel是懒加载，只有访问接口了才会去监控

   ```java
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   @RestController
   public class FlowLimitController {
       @GetMapping("/testA")
       public String testA(){
           return "------testA";
       }
   
       @GetMapping("/testB")
       public String testB(){
           return "-------testB";
       }
   }
   ```

6. 界面显示效果

   ![image-20220619221030707](D:\learnNotes\springcloud.assets\image-20220619221030707.png)

## 流控规则

### 流控模式

1. 直接（默认）

   - 簇点链路-‘+ 流控’

   - **QPS限流**——**每秒请求数**

   - 直接 + 快速失败：超过设定的QPS数量，立即就会返回失败

     ![image-20220720220854828](D:\learnNotes\springcloud.assets\image-20220720220854828.png)

   - **线程数限流**：当调用该api的线程数量达到阈值的时候，进行限流

2. 关联

   - 当关联的资源达到阈值，就限流自己

     比如A和B关联，B的资源访问达到了阈值，就限流A自己

     连坐（比如支付接口要瘫了，就要关联下订单接口，不要让新的订单再过来）

   ![image-20220720222632413](D:\learnNotes\springcloud.assets\image-20220720222632413.png)

   - postman模拟并发密集访问接口

   ![image-20220720223124976](D:\learnNotes\springcloud.assets\image-20220720223124976.png)

3. 链路

### 流控效果

1. 快速失败

   源码：DefaultController

2. 预热Warm Up

   阈值除以coldFactor(默认为3)，经过预热时长（设定）以后才会达到阈值

   预热时间以内阈值等于=用户设定阈值/coldFactor，超过预热时间以后阈值变成用户一开始设定的阈值

   ![image-20220720224334574](D:\learnNotes\springcloud.assets\image-20220720224334574.png)

3. 排队等待

![image-20220720224753472](D:\learnNotes\springcloud.assets\image-20220720224753472.png)

## 降级规则

> 一段时间某个资源的返回有异常或者响应很慢，就给他快速失败，避免级联影响到其他的应用；
>
> 停一段时间后，发现服务可用了就恢复正常，否则就继续快速失败；

1. RT（平均响应时间，秒级）

   平均响应时间 **超过阈值** 并且 **在时间窗口内通过的请求>=5**，两个条件同时满足后出发降级

   窗口期过后关闭断路器

   RT最大4900 （更大需要通过-Dcsp.sentinel.statistic.max.rt=XXXX才能生效）
   
2. 异常比例（秒级）

   QPS >= 5 且异常比例（秒级统计）超过阈值，触发降级；时间窗口结束后，关闭降级

3. 异常数（分钟级）

4. 默认的降级是抛出DegradeException

5. 发生降级之后，在时间窗之后再去检查是否通畅

6. sentinel的降级，只有开闭断路器两种状态，没有半开闭状态

   

### RT

![image-20220724210926108](D:\learnNotes\springcloud.assets\image-20220724210926108.png)

### 异常比例

- 概念

  异常比例：**当资源的每秒请求量 >= 5，<font color=red>并且</font>每秒异常总数占通过量的比值超过阈值**（DegradeRule 中的 count）之后，资源进入降级状态，即在接下来的时间窗口（DegradeRule中的timeWindow，以s为单位）之内，对这个方法的调用都会自动地返回。异常比率的阈值范围是[0.0,1.0]，代表0% - 100%

### 异常数

- 概念

  当资源近1分钟的异常数数目超过阈值之后，会触发熔断。注意由于统计时间窗口是分钟级别的，若timeWindow小于60s，则熔断结束后可能再次进入熔断

  <font color='red'>时间窗口一定要大于等于60s</font>

![image-20220724214458119](D:\learnNotes\springcloud.assets\image-20220724214458119.png)

## 限流-热点规则

> 对资源访问的某个**参数做限流**

### 源码

- BlockException

### @SentinelResource-降级方法

- blockHandler = "fallback方法名"

```java
@GetMapping("/testHotKey")
@SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
public String testHotKey(@RequestParam(value="p1",required = false)String p1,
                         @RequestParam(value="p1",required = false)String p2){
    return "----------testHotKey";
}

public String deal_testHotKey(String p1, String p2, BlockException ex){
    return "------deal_testHotkey,(ಥ﹏ಥ)";
}
```

- 编辑热点
  1. 基础配置

![image-20220725225326637](D:\learnNotes\springcloud.assets\image-20220725225326637.png)			2.	 高级配置

对参数值做配置

![image-20220725225702324](D:\learnNotes\springcloud.assets\image-20220725225702324.png)

- @SentinelResource和RuntimeException

  @SentinelResource中的处理的是限流和熔断的回调函数（blockHandler)

  RunimeException是@SentinelResource管不了的
