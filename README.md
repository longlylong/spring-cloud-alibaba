Plugins：

    Alibaba Java Coding Guidelines
    Lombok
    GsonFormat
    
技术栈：

	Spring Cloud 2.2.10
	Spring Cloud Alibaba 2.2.3

	Spring Cloud Gateway 网关
	-Rate Limit          Api限流
	-Load Balance        流量负载

	Nacos 1.3.2      服务中心,配置中心
	-DiscoveryClient 服务发现
	-Nacos-config    服务配置

    Dubbo 2.7.8      服务间通信

	Sentinel 1.8.0   限流,降级,熔断
	-Datasource Nacos
	
	JPA+QueryDsl(免去直接写SQL,用法和写sql一样灵活)

	RocketMQ 4.5.0   消息队列
	Knife4j 2.0.7    Api文档(Swagger 2.10.5)


单体应用改造：

	关注这个类,里面有详细方法修改3步完事,就不用跑网关和nacos了
	com.thatday.user.filter.SingleAppTokenAspect

项目结构介绍：

	Common （公共模块）
	--constant （常量）
	--dubbo （服务间通信service接口）
	--exception （全局异常配置）
	--jackson （Jackson配置）
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
    
    Dubbo
    http://dubbo.apache.org
    
    Sentinel 1.8.0 控制台 已改造完 持久化到Nacos    
    https://github.com/longlylong/Sentinel
