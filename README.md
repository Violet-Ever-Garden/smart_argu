# hzau-Smart-Agriculture

---

### 华农智慧农业项目使用说明

#### 项目简介

- 华中农业大学智慧云系统，实现智慧农业物联网功能
- 项目配置：
  - JDK1.8
  - Maven依赖管理
- 技术栈：
  - 核心框架：springboot+springMvc+mybatisPlus
  - 缓存：redis
  - 鉴权：shiro+jwt 
  - 工具集：hutool、lombook
  - 表格：easyexcel
  - 接口管理测试：swagger2 
  - 数据库：mysql8.0+mongodb

#### 开始使用

- 将项目clone到本地
- 配置maven，导入项目所需的依赖包
- 修改配置文件：
  - 配置文件在`hzau-sa-msg`模块下的`resource`中
  - 主配置文件：`application.yml`
  - 测试配置文件：`application-test.yml`
  - 开发配置文件：`application-dev.yml`
  - redis配置文件：`config/redis.setting`
  - 日志配置文件：`logback-spring.xml`
- 本地启动项目：
  - 启动`hzau-sa-stater`模块下的项目启动类
  - 访问接口文档
- 发布服务器：
  - 打成jar包：`mvn clean package -Dmaven.test.skip=true`
  - 将`hzau-sa-stater`模块下`target`中的`hzau-sa-stater-1.0-SNAPSHOT.jar`发布到服务器
  - 执行`java -jar hzau-sa-stater-1.0-SNAPSHOT.jar`启动项目

#### 项目演示

- [后台接口文档](http://sourceshare.kaistudy.top:8080/swagger-ui.html)


---

### hzau-sa-generator

- mybatisPlus自动代码生成器
- 可以自动生成项目结构下的各种组件
  - `controller`继承`BaseController`类
  - `dao`
  - `service`继承`IService<T>`接口
  - `entity`
  - dao层对应的`xml`
- 项目说明：
  - 项目各个模块的`controller`所包含的接口各不相同，所以舍弃自动生成的`controller`
  - 根据各个Controller的具体情况手动编写控制器

---

### hzau-sa-msg

- 包含以下几部分功能：

  - 切面日志功能：
   - 使用切面技术自定义了`@SysLog`注解
     - 使用在接口方法的上面，自动将记录该方法的参数、返回值等在日志中
   - 日志接口定义在该模块中
   - 日志的配置文件`logback-spring.xml`
     - 配置了日志写到服务器上的文件位置
     - 日志文件每天一份，最长保持30天
     - 对日志的实体类做了数据库字段映射
     - 日志文件的地址 log.path
     - 日志默认30天有效期
     - 最大容量100MB
     - 同一天操作记录在当天的一份文件中
     - 日志同时持久的在数据库中存储
  - 自定义返回类：
    - Result 返回的实体类
    - CodeType 返回状态码的枚举类，包含了常用的状态码
    - ResultUtil 返回的工具类，写了基本的几种返回方法，可以自定义
  - Swagger的抽象配置：
    - 模块需要继承swagger的基础配置抽象类`Swagger2Config` 也可以写自己的配置
    - 使swagger生效需要在方法上写相应的注解
    - swagger-ui 访问地址：http://localhost:端口/swagger-ui.html
  - 配置：
    - 静态资源访问
    - MybatisPlugs配置
    - 数据库表自动添加配置
  - 工具类：
    - 文件处理
    - JWT工具类
    - Redis工具类
    - shiro日志权限
    - 压缩包

---

### hzau-sa-security

- token shiro 权限校验模块
   - 模块中的用户分别来自student表与teacher表 使用角色来进行权限管理
   - Role : teacher,admin,student (RoleConstant)
       - token 存入缓存： key:username , value:token
       - role存入缓存： key:usernamerole , value:role
   - JwtFilter 过滤掉不带token的非法请求
   - JwtRealm 自定义授权与认证方式
   - ShiroConfig 注入shiro配置
   - JwtToken 定义token的获取方法
   - JwtUtils（msg） 定义token的生成方法
   - ShiroKit（msg） 定义密码的加密方式
   - RedisUtil 包装Redis的缓存接口
   - 
---


### hzau-sa-backstage

- 平台设置模块

---

### hzau-sa-expertSystem

- 专家系统模块

---

### hzau-sa-sensorData

- 物联网模块

---

### hzau-sa-stater 

- 项目主启动模块

---

### hzau-sa-trainingRepotr

- 实训报告模块

---




