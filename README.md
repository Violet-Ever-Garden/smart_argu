# hzau_smart

---

### hzau-sa-generator 说明

- 重构了自动代码生成器
- version = 1
- 后续还需完善

---

### hzau-sa-msg

- 日志和异常类模块
- 目前重构了日志
  - version = 1
  - 还需结合后续的异常处理完善
- 返回模块
  - Result 返回的实体类
  - CodeType 返回状态码的枚举类 包含了常用的状态码  后续可以修改
  - ResultUtil 返回的工具类 写了基本的几种返回方法  可以自定义  后续也可以增加和修改
- 异常处理模块待定：
  - 在实际的业务逻辑中看可能存在哪些异常 然后统一编写  小组成员讨论
- 继续完善日志的业务模块
  - 写了小部分异常处理 
    - 异常这部分涉及到数据库  可能要设置回滚  根据具体的情况看
  - 完善了部分切面日志的模块  还需在拿到当前操作的用户  与其他模块交互
  - 下午准备些日志的controller
  - Swagger的学习与运用
- 日志的controller完成 可以实现功能
- swagger2 配置完成
  - 模块需要继承swagger的基础配置抽象类 也可以写自己的配置
  - 使swagger生效需要在方法上写相应的注解
  - swagger-ui 访问地址：http://localhost:端口/swagger-ui.html
  - 日志的安全访问  权限等  还未设置
- 切面日志的当前用户名仍然未解决
- 初始配置了mybatisplus的配置类

---

### hzau-sa-test

- 我自己的日志、异常测试类

---

### hzau-sa-security

- token shiro 模块


