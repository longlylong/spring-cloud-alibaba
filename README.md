Plugins：

    Alibaba Java Coding Guidelines
    Lombok
    GsonFormat
    
技术栈：

	Spring Cloud 2.2.7
	Spring Cloud Alibaba 2.2.1

	Spring Cloud Gateway
	-Rate Limit

	Nacos 1.2.1
	-DiscoveryClient
	-Nacos-config

	Sentinel 1.7.1
	Feign
	JPA+QueryDsl(免去直接写SQL,用法和写sql一样灵活)

	RocketMQ 4.5.0
	Knife4j 2.0.1(Swagger 2.9.2)

项目结构介绍：

	Common （公共模块）
	--constant （常量）
	--controller （controller基类）
	--exception （全局异常配置）
	--Jackson （Jackson配置）
	--model （模块之间公用的对象）
	--rocketmp （rocketmq配置）
	--utils （模块之间共用的util）
	--validation （模块之间共用的校验器）

	Pub （公共服务目录,如果前期没业务需求可以不跑）
	--Common-Server （公共服务）

	mm-admin-backend 管理后台后端
	(Mybatis与JPA代码生成)
	mm-admin-front 管理后台前端 Vue 最简化功能
	(前后端分离后台管理系统http://www.ruoyi.vip,
	如果前期没业务需求可以不跑)

	ServerGateway （网关）
	--config （swagger入口、跨域、熔断配置）
	--filter （限流配置，可在Nacos动态配置限流规则）
	----author(鉴权的)
	----ratelimit(限流的)
	--provider(提供配置的类)

	ThatDay （项目业务代码）
	--user （用户服务）
	----config （全局异常，swagger，websocket配置）
	----modules （模块）
	------user(用户模块)
	--------controller(前台的api接口)
	--------backend(后台的api接口)
	--------dao(数据访问对象)
	--------dto(数据传输对象)
	--------entity(数据对象)
	--------service(业务代码)
	--------vo(请求对象)
	----repository （基础的jpa操作）
	----rocketmq （该服务的rocketmq配置）
	----service  （基本的增删改查基类）
	----util （工具类）

