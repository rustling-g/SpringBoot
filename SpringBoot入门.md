# SpringBoot学习



## 微服务

一种架构风格

一个应用是一组小型服务，通过HTTP方式互通

把每个功能元素放到一个独立的服务中

## 入门



**@SpringBootApplication**

说明该类是SpringBoot的主配置类，SpringBoot启动该类的main方法



**@RestController = @controller + @responsebody**



**给类中的属性赋值**

+ @ConfigurationProperties + yaml
+ @ConfigurationProperties + properties
+ @Value 里面可直接赋值/${key}（从环境变量/配置文件中获取值）/#{SpEL表达式}

二者区别

|                | ConfigurationProperties | Values |
| -------------- | ----------------------- | ------ |
| JSR303数据校验 |                         | ❌      |
| SpEL表达式     | ❌                       |        |
| 复杂类型封装   |                         | ❌      |

**@PropertySource** 

​		加载指定的配置文件

**@ImportResource**  

​		此注解位于主配置类上导入Spring的配置文件



**Springboot推荐使用配置类替代配置文件**

```java
@Configuration      //表示当前类是配置类（代替配置文件的作用）
public class MyAppConfig {

    @Bean       //将方法的返回值添加到容器中
    public HelloService helloService(){
        System.out.println("return helloService");
        return new HelloService();
    }
}
```

## 日志

日志门面（抽象层）：SLF4j

日志实现：logback

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```

Springboot能自动适配所有的日志，在引入其他框架的时候，只需要把该框架依赖的日志框架排除掉

```java
 Logger logger = LoggerFactory.getLogger(getClass());
        //下列日志级别：由低到高，可在配置文件中调整
        logger.trace("trace日志");
        logger.debug("debug日志");
        //springboot默认使用info级别的日志
        logger.info("info日志");
        logger.warn("warn日志");
        logger.error("error日志");
```

```properties
logging.level.com=trace
#在当前项目下生成springboot.log日志
logging.file.name=springboot.log
#在当前磁盘的根路径下创建文件夹，包含默认名称的日志文件
logging.file.path=/spring/log
#控制在控制台输出的日志格式
logging.pattern.console=
```

## Web开发

### 自动配置原理

```java
xxxAutoConfigration：给容器中自动配置组件
xxxProperties：配置类，封装配置文件的内容
```

> jar包：执行springboot主类的main方法，启动ioc容器，创建嵌入式的servlet容器(默认不支持jsp)
>
> war包：启动服务器，服务器启动Springboot应用，启动ioc容器

### 模版引擎

jsp freemarker velocity <u>**thymeleaf**</u>

<img src="/Users/mac/Desktop/springboot核心篇+整合篇-尚硅谷/01尚硅谷SpringBoot核心技术篇/Spring Boot ±Ê¼Ç+¿Î¼þ/images/template-engine.png" alt="template-engine" style="zoom:50%;" /> 

### thymeleaf使用&语法

把html页面放在类路径下的/templates里

<img src="/Users/mac/Desktop/springboot核心篇+整合篇-尚硅谷/01尚硅谷SpringBoot核心技术篇/Spring Boot ±Ê¼Ç+¿Î¼þ/images/2018-02-04_123955.png" alt="thymeleaf th:使用" style="zoom:75%;" />

```html
<!-- th:html任意属性 如th:id th:class 用来替换掉原生属性 -->
        <div th:text="${hello}">显示欢迎信息</div><br>
        <hr/>
        <div th:text="${hello}"></div><br>
        <div th:utext="${hello}"></div><br>
        <hr/>
        <!-- th:each 写在哪个标签上，该标签每次遍历的时候都会生成一次 -->
        <h4 th:text="${user}" th:each="user:${users}"></h4>
        <hr/>
        <h4>
            <!-- 行内取数据的话，[[]] = th:text ; [()] = th:utext -->
            <span th:each="user:${users}">[[${user}]]</span>
        </h4>
```

**thymeleaf抽取公共片段**

```html
抽取
<footer th:fragment="topbar">.....</footrt>
引入
<div th:insert="~{dashboard :: topbar}" ></div>			将公共片段整个插入到div中
<div th:replace="~{dashboard :: topbar}" ></div>		将div替换为footer
<div th:copy="~{dashboard :: topbar}" ></div>				将footer替换为div
		~{模板名 :: 选择器}
		~{模板名 :: 片段名}
```



### 国际化

+ <img src="/Users/mac/Library/Application Support/typora-user-images/截屏2020-12-08 10.18.33.png" alt="截屏2020-12-08 10.18.33" style="zoom:50%;" />

+ 在配置文件中加：

```properties
spring.messages.basename=i18n.login
```

使用#{}获取国际化的值

```ht
<a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
<a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
```

+ 配置MyLocaleResolver

  ```java
  public class MyLocaleResolver implements LocaleResolver {
      @Override
      public Locale resolveLocale(HttpServletRequest request) {
          String l = request.getParameter("l");
          Locale locale = Locale.getDefault();
          if (! StringUtils.isEmpty(l)){
              String[] s = l.split("_");
              locale = new Locale(s[0],s[1]);
          }
          return locale;
      }
  
      @Override
      public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
  
      }
  }
  ```

+ 在MyMvcConfig 中

  ```java
  @Bean
  public LocaleResolver localeResolver(){
      return new MyLocaleResolver();
  }
  ```

### CRUD（Rest风格）

|      | 普通CRUD（使用URI来区分操作） | RestfulCRUD（使用请求方式来区分操作）        |
| ---- | ----------------------------- | -------------------------------------------- |
| 查询 | getEmp                        | emp  GET请求                                 |
| 添加 | addEmp?xxx                    | emp  POST请求                                |
| 修改 | updateEmp?xxx                 | emp/{xxx}  PUT请求                           |
| 删除 | deleteEmp?xxx                 | emp/{xxx}  DELETE请求                        |
|      |                               | 当跳转到某个页面（添加/修改）时，使用GET请求 |

```html
发送put/delete请求方法：
<form th:action="@{/emp}+${e.id}" method="post">
   <input type="hidden" name="_method" value="delete">
   <button type="submit" class="btn btn-sm btn-danger">删除</button>
</form>
```

### 错误处理机制

分为返回错误网页&返回json。

## Docker

```shell
**检索**
docker search 关键字
**拉取下载**
docker pull 镜像名
**查看所有本地镜像**
docker images
**删除指定本地镜像**
docker rmi 镜像名
**后台运行镜像**
docker run --name 自定义容器名 -d 镜像名
**查看运行中的容器(加-a可查看所有容器)**
docker ps
**停止容器的运行**
docker stop 容器名/容器id
**启动容器**
docker start 容器名/容器id
**删除容器**
docker rm 容器id
**端口映射**
-p 主机端口:容器内部的端口  -->  docker run -d -p 8088:8080  --name mysql01 mysql
**容器日志**
docker logs 容器名/容器id
```

## Springboot + Mybatis + Druid

```yaml
spring:
  datasource:
    initialization-mode: always
    # 数据库访问配置, 使用druid数据源(默认数据源是HikariDataSource)
    type: com.alibaba.druid.pool.DruidDataSource

    #配置sql文件地址(第一次启动之后就不需要了)
#    schema:
#      - classpath:sql/*.sql

    #链接池配置
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3307/mybatis?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
      username: root
      password: gc14,xyk

      # 连接池配置：大小，最小，最大
      initial-size: 5
      min-idle: 5
      max-active: 20

      # 连接等待超时时间
      max-wait: 30000

      # 配置检测可以关闭的空闲连接，间隔时间
      time-between-eviction-runs-millis: 60000

      # 配置连接在池中的最小生存时间
      min-evictable-idle-time-millis: 300000
      # 检测连接是否有，有效得select语句
      validation-query: select '1' from dual
      # 申请连接的时候检测，如果空闲时间大于time-between-eviction-runs-millis，执行validationQuery检测连接是否有效，建议配置为true，不影响性能，并且保证安全性。
      test-while-idle: true
      # 申请连接时执行validationQuery检测连接是否有效，建议设置为false，不然会会降低性能
      test-on-borrow: false
      # 归还连接时执行validationQuery检测连接是否有效，建议设置为false，不然会会降低性能
      test-on-return: false

      # 是否缓存preparedStatement，也就是PSCache  官方建议MySQL下建议关闭   个人建议如果想用SQL防火墙 建议打开
      # 打开PSCache，并且指定每个连接上PSCache的大小
      pool-prepared-statements: false
      max-open-prepared-statements: 20
      max-pool-prepared-statement-per-connection-size: 20

      # 配置监控统计拦截的filters, 去掉后监控界面sql无法统计, 'wall'用于防火墙防御sql注入，stat监控统计,logback日志
      filters: stat,wall
      # Spring监控AOP切入点，如x.y.z.service.*,配置多个英文逗号分隔
      #aop-patterns: com.springboot.servie.*
      # lowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500

      # WebStatFilter监控配置
      web-stat-filter:
        enabled: true
        # 添加过滤规则：那些访问拦截统计
        url-pattern: /*
        # 忽略过滤的格式：哪些不拦截，不统计
        exclusions: '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*'

      # StatViewServlet配置（Druid监控后台的Servlet映射配置，因为SpringBoot项目没有web.xml所在在这里使用配置文件设置）
      stat-view-servlet:
        enabled: true
        # 配置Servlet的访问路径：访问路径为/druid/**时，跳转到StatViewServlet，会自动转到Druid监控后台
        url-pattern: /druid/*
        # 是否能够重置数据
        reset-enable: false
        # 设置监控后台的访问账户及密码
        login-username: admin
        login-password: 123456

        # IP白名单：允许哪些主机访问，默认为“”任何主机
        # allow: 127.0.0.1
        # IP黑名单：禁止IP访问，（共同存在时，deny优先于allow）
        # deny: 192.168.1.218

      # 配置StatFilter
      filter:
        stat:
          log-slow-sql: true

mybatis:
  # 全局配置文件的位置
  config-location: classpath:mybatis/mybatis-config.xml
  # sql映射文件位置
  mapper-locations: classpath:mybatis/dao/*.xml
```

## 整合SpringData JPA

### 步骤

#### 1、编写实体类

```java
//使用jpa注解配置映射关系
@Entity //告诉JPA这是一个实体类
@Table(name = "user")   //和数据库中的表映射,name若省略，默认表名=类名
public class User {
    @Id     //指明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private Integer id;
    @Column(length = 50)
    private String lastName;
    @Column
    private String email;
}
```

#### 2、编写dao接口

```java
//继承JpaRepository，具有分页和排序的功能
public interface IUserDao extends JpaRepository<User,Integer> {
}
```

#### 基本配置

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jpa?characterEncoding=utf-8
    username: root
    password: gc14,xyk
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      # 更新或创建数据表结构
      ddl-auto: update
    # 在控制台显示sql
    show-sql: true
```

## 事件监听机制

配置在META-INF/spring.factories中：

**ApplicationContextInitializer   SpringApplicationRunListener**

只要放在ioc容器中

**ApplicationRunner   CommandLineRunner**

## 自定义starter

1 这个场景需要用到的依赖是什么？

2  如何编写自动配置

```java
@Configuration							//该类是一个配置类
@ConditionalOnxxx						//在指定条件成立的情况下自动配置类生效
@AutoConfigureAfter					//指定自动配置类的顺序
@Bean												//给容器中添加组件

@ConfigurationProperties		//结合相关xxxProperties类来绑定相关的配置
@EnableConfigurationProperties//让xxxProperties生效，加入到容器中

//将需要启动就加载的自动配置类，配置在META-INF下的spring.factories中
```

3 模式

启动器只用来做依赖导入 例如 mybatis-spring-boot-starter

## 缓存

默认使用ConcurrentMapCacheManager创建的组件作为缓存，开发中使用缓存中间件：Redis

```java
@EnableCaching		//在主方法上开启注解缓存
@Cacheable				//根据方法的请求参数对结果进行缓存，以后再用时就不调数据库了
		 * key:缓存数据使用的key，默认为参数的值，可写SpEL
     * keyGenerator:key的生成器，和key二选一使用
     * cacheManager/cacheResolver:指定缓存管理器
     * condition/unless:指定符合条件的情况下才缓存/才不缓存
@CacheEvict				//清空缓存
     * allEntries:删除该缓存中的所有key-value数据
     * beforeInvocation:在方法执行之前清除缓存（不管当前方法是否出错，都会清缓存）
@CachePut					//先调用方法，再缓存结果（更新缓存）
@Caching					//定义复杂的缓存规则
@CacheConfig			//写在类上，指定公共缓存名/keyGenerator等
```

### 自定义缓存管理器

```java
@Configuration
@EnableCaching
public class MyRedisConfig extends CachingConfigurerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRedisConfig.class);

    //缓存管理器
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //60s缓存失效
                .entryTtl(Duration.ofSeconds(60))
                //设置key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                //设置value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                //不缓存null值
                .disableCachingNullValues();

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
        LOGGER.info("自定义Redis缓存管理器加载完毕");
        return redisCacheManager;
    }

    //key序列化方式
    private RedisSerializer keySerializer(){
        return new StringRedisSerializer();
    }
    //value序列化方式
    private GenericJackson2JsonRedisSerializer valueSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

    //自定义key生成器
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return Arrays.asList(objects).toString();
            }
        };
    }
}
```

## 消息

通过消息服务中间件来提升系统异步通信、扩展解耦的能力

当消息发送者发送消息后，将由*<u>**消息代理**</u>*接管，保证消息传递到<u>***目的地***</u>

```
目的地形式：

1. 队列——点对点消息通信
   + 消息发送者发送消息，接受者从队列中获取消息内容，消息读取后被移出
   + 发送者和接受者唯一
2. 主题——发布/订阅消息通信
   + 多个接收者监听消息发送者发布的主题，在消息到达时同时收到消息
```

消息服务规范：JMS  /  AMQP(RabbitMQ),可兼容JMS,跨语言跨平台,将消息序列化后发送

**消息交换器的3种格式：**

1. direct		精确匹配Routine Key 和队列名

2. fanout       把消息发给绑定的所有队列(监听模式)

3. topic         使用简单的Routine Key通配符，把消息发给符合名称格式的队列

使用@EnableRabbit + @RabbitListener 来监听消息队列的内容

当已经在rabbitmq服务器中创建好了Exchanges &Queues 时，直接使用RabbitTemplate，否则使用Amqpadmin先创建并绑定

## 检索

elasticsearch 以json格式存储（索引）数据

ES集群-索引-类型-文档-属性   类比Mysql中的 连接-数据库-表-记录-属性

```sense
PUT /索引名/类型名/唯一标识
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
```

ElasticsearchRepository内含基本的CRUD操作，RestHighLevelClient用于复杂查询

## 任务

**异步任务：**主方法@EnableAsync  +  方法@Async

**定时任务：**主方法@EnableScheduling  +  方法@Scheduled

> ​	@Scheduled的corn参数：
>
> ​	        second | minute | hour | day of month | month | day of week
>
> 例：		 0             *		  *				 *				 *		 MON-FRI	  	周一到周五每分钟启动

**邮件任务：**

```java
public void testMail() throws MessagingException {
        //创建简单的消息邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮件标题");
        message.setText("邮件正文");
        message.setTo("目标邮箱");
        message.setFrom("发件人邮箱");
  
        //创建一个复杂的消息邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("邮件标题");
        helper.setText("<b style='color:red'>邮件正文</b>",true);
        helper.setTo("目标邮箱");
        helper.setFrom("发件人邮箱");
        helper.addAttachment("上传文件名",new File("上传文件路径"));

        mailSender.send(message);
    }
```

```yaml
spring:
  mail:
    username: qq邮箱地址
    password: 授权码(非邮箱密码)
    host: smtp.qq.com
    properties:
      mail:
       smtp:
        ssl:
         enable: true
```

## 安全

## 分布式

### zookeeper+dubbo

zookeeper：分布式注册中心，负责保存不同模块的位置

dubbo：分布式服务框架，负责模块间的调用

<img src="/Users/mac/Desktop/截屏2020-12-14 20.32.21.png" alt="截屏2020-12-14 20.32.21" style="zoom:50%;" />

 ### springboot+springcloud

```yaml
# eureka-server  注册中心
server:
  port: 8761
eureka:
  instance:
    hostname: eureka-server
  client:
    register-with-eureka: false #不把eureka本身注册在注册中心
    fetch-registry: false #不从eureka中获取服务的注册信息
    service-url:
      defaultZone: http://localhost:8761/eureka/ #注册中心的注册地址
```

```yaml
#provider / consumer
server:
  port: 8002
spring:
  application:
    name: provider
eureka:
  instance:
    prefer-ip-address: true #注册服务时，使用服务的ip地址
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/ #注册中心的注册地址
```

```java
/*consumer 的主方法*/
@SpringBootApplication
@EnableDiscoveryClient  //开启发现服务功能
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced   //使用负载均衡机制
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```