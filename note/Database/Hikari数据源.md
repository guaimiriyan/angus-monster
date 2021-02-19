### Hikari数据源
1. hikari是springboot2.x 默认数据源
2. 它的速度更加快，更加轻量级
3. 底层将ArraysList替换为自定义的fastList
4. 并发的效率更高，使用的是自动义无锁的设计

![与其他数据源效率比较](https://github.com/brettwooldridge/HikariCP/wiki/HikariCP-bench-2.6.0.png)

#### 一、需要引入的maven依赖
由于springboot帮我们进行了引入，所以无需手动引入
```xml
<!--Java 8 thru 11 maven artifact:-->

    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>4.0.1</version>
    </dependency>
<!--Java 7 maven artifact (maintenance mode):-->

    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP-java7</artifactId>
        <version>2.4.13</version>
    </dependency>
<!--Java 6 maven artifact (maintenance mode):-->

    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP-java6</artifactId>
        <version>2.3.13</version>
    </dependency>

```
#### 整合Hikari
1. 添加mysql驱动和mybatis
```xml
        <!-- mysql驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.41</version>
        </dependency>
        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>
```

2. 数据源配置
```yaml
spring:
  profiles:
    active: dev
  datasource:                                           # 数据源的相关配置
    type: com.zaxxer.hikari.HikariDataSource          # 数据源类型：HikariCP
    driver-class-name: com.mysql.jdbc.Driver          # mysql驱动
    #    url: jdbc:mysql://localhost:3306/angusdb?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true
    username: root
    #    password: root
    hikari:
      connection-timeout: 30000       # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 默认:30秒
      minimum-idle: 5                 # 最小连接数
      maximum-pool-size: 20           # 最大连接数
      auto-commit: true               # 自动提交
      idle-timeout: 600000            # 连接超时的最大时长（毫秒），超时则被释放（retired），默认:10分钟
      pool-name: DateSourceHikariCP     # 连接池名字
      max-lifetime: 1800000           # 连接的生命时长（毫秒），超时而且没被使用则被释放（retired），默认:30分钟 1800000ms
      connection-test-query: SELECT 1
  servlet:
    multipart:
      max-file-size: 512000     # 文件上传大小限制为500kb
      max-request-size: 512000  # 请求大小限制为500kb
```
