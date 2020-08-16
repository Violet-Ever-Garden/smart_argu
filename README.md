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

---


### hzau-sa-backstage

- 平台设置模块
- 年级管理和班级管理功能完成
- 日志的实体类做了修改
  - mybatisplus 的全局驼峰有点问题  开启之后msg模块不能正常驼峰转换 其他模块可以
  - 对日志的实体类做了数据库字段映射
- 增加了全局日志的xml配置文件 msg模块下的logback-spring.xml
  - 日志文件的地址 log.path
    - 注意linux下系统文件分隔符`/` windows下系统文件分隔符`\` 在项目中要写成`\\`
  - 日志默认30天有效期
  - 最大容量100MB
  - 同一天操作记录在当天的一份文件中
  - 日志同时持久的在数据库中存储
- 作物管理功能完成
- 班级设置功能完成
- 其他模块还需要修改

---

### FileUtil类的使用

- 目前定义了几种方法：
  - `uploadFile`: 上传单个文件 可以在swagger测试
    - 参数`FileEnum fileEnum`：要上传文件到什么目录 上级目录
    - 参数`String fileMsg`：对上传文件的说明  下一级目录
    - 参数`MultipartFile multipartFile`：要上传的文件
    - 返回值`String`：已经上传文件的绝对路径
  - - `uploadFiles`:上传多个文件  swagger测试不了 postman可以正常测试
    - 参数`FileEnum fileEnum`：要上传文件到什么目录 上级目录
    - 参数`String fileMsg`：对上传文件的说明  下一级目录
    - 参数`MultipartFile[] multipartFiles`：要上传的文件列表
    - 返回值`List<String>`：已经上传文件的绝对路径的列表
  - `deleteFile`：删除文件
    - 参数`String filePath`：要删除文件的路径
    - 返回值`boolean`：是否删除
  - `changeFile`：修改文件
    - 参数`String filePath`：需要修改文件的路径
    - 参数`MultipartFile multipartFile`：新的文件
    - 返回值`String`：修改过后文件的绝对路径
  - `getFileUrl`：获取文件的网络映射路由
    - 参数`String filePath`：文件路径
    - 返回值`String`：返回给前端的文件访问路径
  
- 注意：
  - 传文件要使用formData的格式上传
  - 返回给前端的是文件的访问路由 而不是文件路径
  - 要提前做好文件的网络路径映射工作
  - shiro给文件访问是否需要开权限
  - 拦截器是否会起作用等等



