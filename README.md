Plugins：

    Alibaba Java Coding Guidelines
    Lombok
    GsonFormat
    
技术栈：

	Spring Cloud 2.2.7
	Spring Cloud Alibaba 2.2.2

	Spring Cloud Gateway
	-Rate Limit
	-Load Balance

	Nacos 1.3.2
	-DiscoveryClient
	-Nacos-config

	Sentinel 1.8.0 Datasource Nacos
	Feign
	JPA+QueryDsl(免去直接写SQL,用法和写sql一样灵活)

	RocketMQ 4.5.0
	Knife4j 2.0.5(Swagger 2.9.2)


单体应用改造：

	关注这个类,里面有详细方法修改3步完事,就不用跑网关和nacos了
	com.thatday.user.filter.SingleAppTokenAspect

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
	--hutool    (HuTool.md)工具包https://hutool.cn/docs


	Pub （公共服务目录,如果前期没业务需求可以不跑）
	--Common-Server （公共服务）

	mm-admin-backend 管理后台后端
	mm-admin-front 管理后台前端 Vue 最简化功能
	(前后端分离后台管理系统，改自https://el-admin.vip/,
	如果前期没业务需求可以不跑)

	ServerGateway （网关）
	--config （swagger入口、跨域、熔断配置）
	--filter （过滤器）
	----author(鉴权的)
	----ratelimit(限流的，关联Sentinel Dashboard)
	----loadbalance(自定义负载 按照权重走的 配合nacos)
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

工具：
    
    Nacos
    https://nacos.io
    
    Sentinel 1.8.0 控制台 已改造完 持久化到Nacos    
    https://github.com/longlylong/Sentinel
