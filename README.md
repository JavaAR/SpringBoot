# 深蓝探索体育科技有限公司打卡程序2.0后端框架  (项目所需：jdk1.8，maven，mysql数据库)
## 整体框架介绍，本框架由Spring-boot框架搭建，集成了Mybatis，Druid连接池，  请求结果统一封装，全局异常处理，全局拦截器，swagger2接口在线文档生成，freemaker代码生成器，redis数据库连接，（后续功能还在添加中...）
> 开发者可以通过Http方式将代码克隆到本地，然后倒入自己的idea中，修改为自己的maven路径，请使用之前先下载idea的  **lombok**  插件，  **本项目主要编写了一些常用的集成框架配置，具体业务代码还请使用者自己实现**  
1. MyBatis集成
> 本框架集成了MyBatis,使用者可在 **\src\main\java\com\deepblue\punchcard\configuration\MyBatisConfiguration** 的  **sqlSessionFactoryBean（）**  方法和  **mapperScanner()**  中自定义配置，如配置类型别名，指定mapper.xml文件的路径，配置dao层接口的位置，
当然，您也可以直接在  **application-dev.yml**  中配置  **mybatis.mapper-locations:classpath: /mapper/\*mapper.xml**  来指定别名类型以及mapper文件扫描位置，
您也可以在主类中使用  **@MapperScan（"com.deepblue.punchcard.dao"）**  来指定dao层接口所在的位置.
2. Druid连接池
> Druid采用了配置文件注入的方式来运行
> * ### Druid数据源配置   
> 使用者可以在  **application-dev.yml**  文件中直接配置Druid数据源，如：在  **spring.datasource.url**  ,  **spring.datasource.username**  ,   **spring.datasource.driverClassName**  ,  **spring.datasource.driverClassName**  ,  **spring.datasource.password**  节点下配置自己的数据库地址，数据库用户名，数据库驱动，数据库密码
>，以及其他Druid配置，作者都给有注释
> * ### Druid 配置监控StatViewServlet 
> 配置监控StatViewServlet 主要用于配置一些访问可视化页面的ip，用户名，密码，以及是否清除数据，
> > ##### 配置白名单 
> > 使用者可以在  **application-dev.yml**  文件中的  **spring.druid.allowIp**  节点下配置访问可视化页面的白名单ip，例如：配置  **spring.druid.allowIp**  节点的值为  **127.0.0.1**  ，那么访问页面时就只有
> >  **127.0.0.1：你的端口/druid**  可以访问到登录页面，  **spring.druid.allowIp**  的值可以有多个，用,分开。
> > ##### 配置黑名单
> > 使用者可以在  **application-dev.yml**  文件中的
> >  **spring.druid.denyIp**  配置黑名单ip,作用与配置白名单一致，但是白名单与黑名单中有相同的ip时，Druid会优先使用黑名单的ip。
> > ##### 配置访问用户名和密码以及是否清除数据
> > 使用者可以在  **application-dev.yml**  文件的  **spring.druid.loginUsername**  ， **spring.druid.loginPassword**  ，  **spring.druid.resetEnable**  节点配置
访问Druid可视化页面的用户名，密码，以及是否清除数据（false不清除，默认true）
> * ### Druid 配置监控WebStatFilter
> 配置监控StatViewServlet 主要用于显示用户发送了那些请求，以及请求的相应，用户可以在  **application-dev.yml**  文件中的  **spring.druid.exclusions**  节点配置要过滤
的url，被过滤的url将不会在页面中显示。
> * ### 配置Druid监控Spring
> 配置Spring监控主要用于发现调用了那些方法，使用者可以在  **druid-bean.xml** 文件中配置那些层被调用方法显示，以本项目为例，我想显示dao层接口方法调用，可以在id为  **druid-stat-pointcut**  的bean标签中的list
标签下配置  **<value>com.deepblue.punchcard.dao.\*</value>**  以此类推，注意  **由于该项目是Spring-Boot项目,所以添加完自定义的配置文件之后请在主类中使用@ImportResource注解来添加自定义的配置文件**  
配置完之后就可以访问[http://localhost:8080/druid/](http://localhost:8080/druid/) （如果您的端口是8080的话）
3. 请求结果统一封装
> 我们调用服务返回的类型有String,List,Map,Bean,Int,Boolean等类型,可以统一成Dto对象返回,  方便项目组其他成员调用接口,
  **src\main\java\com\deepblue\punchcard\dto\Dto.java**  类中定义了是否成功执行，返回错误码，返回的错误信息，而  **src\main\java\com\deepblue\punchcard\dto\DtoUtils.java**  类中定义了一些返回的方法，如:返回成功不带任何数据，返回成功并且携带数据以及信息
返回成功只携带提示信息，返回成功只携带数据，返回错误携带错误信息以及错误码方法，返回错误携带的错误码使用者可以在  **src\main\java\com\deepblue\punchcard\constant\ProjectConstants.java**  类中的  **ErrorCode**  内部类中自行添加，总体返回示例如下:
> ```java
>  @PostMapping("/selectById")
>  public Dto selectById(Integer id){
>    UserInfo userInfo = userInfoService.selectById(id);
>    if(userInfo!=null){
>    //返回成功 携带数据
>    return DtoUtils.returnDataSuccess(userInfo);
>    }
>   return DtoUtils.returnFail("查询失败",ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE);
> }
> ```
4. 全局异常处理
> 全局异常的配置类在:  **src\main\java\com\deepblue\punchcard\configuration\GlobalExceptionConfiguration.java**  类中，使用者可在此类中添加其他异常，如添加空指针异常处理
> ```java
>   @ExceptionHandler(NullPointerException.class)
>   public Dto doNullPointerException(NullPointerException e){
>       //返回结果统一格式化(参数：异常错误码,异常对象)
>       return exceptionResultFormat(ProjectConstants.ErrorCode.SERVICEEXCEPTIONCODE,e);
>   }
> ``` 
> 由于Spring-Boot有自带的404错误页面，本项目关闭了Spring-Boot自带的404页面，由作者自己处理404错误，如果使用者想开启自带的404页面，请在  **application-dev.yml**  文件的  **spring.mvc.throw-exception-if-no-handler-found**  节点改为false，并将   **spring.resources.add-mappings**  节点改为true  
> 自定义的404错误处理请参考  **src\main\java\com\deepblue\punchcard\configuration\GlobalExceptionConfiguration.java**  类中的  **noHandlerFoundException()**  方法
5. 全局请求统一拦截
> 本项目全局请求统一拦截主要用于用户是否登录，配置位置在  **\src\main\java\com\deepblue\punchcard\configuration\WebConfiguration.java**  类中CustomInterceptors内部类中，使用者可以在  **preHandle()**  方法中自定义拦截规则，
> 使用者也可以在  **application-dev.yml**  文件中的  **spring.request.interceptUrl**  节点,  **spring.request.excludeInterceptUrls**  节点配置拦截url请求与放行url请求，注意   **spring.request.excludeInterceptUrls节点的值是list，请以给定的模板格式添加**  
6. 静态资源放行
> 如果使用者的项目为前后端分离项目，可忽略此条  
> 静态资源放行主要用于一些后缀名为.js,.html,.css,.jpg,等资源放行，使用者可以在  **application-dev.yml**  文件中的  **spring.request.url**  配置请求的url路径放行，在  **spring.request.path**  节点配置请求的静态资源文件夹放行
7. Swagger接口在线文档
> 本项目引入了Swagger2接口文档生成，Swagger的配置类在  **src\main\java\com\deepblue\punchcard\configuration\SwaggerConfiguration.java**  类中，使用者可以在  **application-dev.yml**  文件中的  **spring.swagger.swaggerScanLocations**  节点修改Swagger要扫描的包，具体Swagger注解请使用者自行百度，这里不再概述,
配置好的用户可直接访问[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
8. freemaker代码生成器
> 代码生成器的工具类在  **\src\main\java\com\deepblue\punchcard\utils\tempUtils\CodeGeneratorUtil.java**  类中，使用者需将该类中的  **jdbc**  配置修改为自己的数据库配置，模板的位置在  **src/main/resources/templates**  下，如果使用者想自定义模板，可以修改  **TEMPLATE_FILE_PATH**  属性的位置，
> 修改完之后请在相应的方法中修改使用模板的名称,如果用户想自定义代码生成的位置，可以在  **src\main\java\com\deepblue\punchcard\constant\FreeMakerPathConstant.java**  类中自行修改
> 数据库类型转换为java类型可以在  **\src\main\java\com\deepblue\punchcard\utils\tempUtils\TypeConstant.java**  类中的静态代码块中自行修改
9. Redis数据库配置
> 本项目Redis使用主要存储用户信息，使用者可以在  **application-dev.yml**  文件中的  **spring.redis**  配置自己的redis连接，另外作者在  **\src\main\java\com\deepblue\punchcard\utils\RedisApi.java**  工具类中添加了redis的存取方法，使用者可自己参考
10. Shiro实现接口权限保护功能
> 为了方便用户的权限与角色管理，作者引入了shiro框架，集成shiro的主要有两个，分别是：shiro配置类  **\src\main\java\com\deepblue\punchcard\configuration\shiroConfiguration\ShiroConfiguration.java**  Shiro自定义的Realm类:  **\src\main\java\com\deepblue\punchcard\configuration\shiroConfiguration\CustomRealm.java**  
> * ### Shiro配置类
> > #### 拦截器配置
> > shiro配置类中的shiroFilterFactoryBean方法中配置了运行的SecurityManager与拦截的规则与权限管理，由于手动维护太麻烦，所以作者将拦截规则与所需权限放入了数据库中，方便管理(由于配置好shiro之后会与druid与swagger冲突，所以只配置了要拦截的请求与相应的权限)
> > #### SecurityManager配置
> > SecurityManager是Shiro的心脏，所有的认证与授权，密码加密，等等都是在这里进行，shiro配置类中的securityManager方法中配置了当前安全管理与自定义的realm，因为无论是认证还是授权最终所调用的都是Realm，所以用自定义的realm
> > #### 自定义的Realm配置:
> > 注册自己定义的Realm类
> > #### 加密配置(hashedCredentialsMatcher)
> > 自定义的加密算法与加密次数
> * ### 自定义的Realm配置类
> > #### 认证（doGetAuthenticationInfo）
> > shiro认证流程：doGetAuthorizationInfo (认证) 获取用户名之后，从数据库中查找用户信息，如果未查出，则会抛出UnknownAccountException()未知账户异常，组织返回参数simpleAuthorizationInfo（object，password，realmName），设置盐
> > （subject.login()方法调用之后，通过SecurityManager的Authenticator类会将用自定义的Realm与用户的token进行对比，根据用户名查找，如果查找到就返回给Authenticator用户信息，没有则返回null，Authenticator接受到返回的信息之后，如果为null，则抛出UnknownAccountException，如果不为null，则将返回的对象与用户的token密码进行对比，如果一致，则认证成功，如果不一致，则抛出IncorrectCredentialsException异常）
> > s

##  后续添加的功能有  **shiro权限保护接口功能**  ，  **aop异步记录日志功能**  ，  **多文件上传功能**  ，  **系统发送邮件功能**  等
##  最后推荐郭阳大牛推荐给我的一款框架[ruoyi](http://doc.ruoyi.vip/)
