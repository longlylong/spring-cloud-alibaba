    
#####Plugins
    Alibaba Java Coding Guidelines
    Lombok
	GsonFormat
####技术栈：
	Spring Cloud 2.1.9
	Spring Cloud Alibaba 2.1.0

	Spring Cloud Gateway
	-Rate Limit

	Nacos 1.1.4
	-DiscoveryClient
	-Nacos-config

	Sentinel 1.6.3
	Feign
	JPA+QueryDsl

	RocketMQ 4.5.0
	Swagger 2.9.2

#####项目结构介绍：
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
	----tinyid （滴滴开源的序列ID生成器）
	----controller （接口）

	RouYi-Fast
	(加了springcloud的若依管理系统http://www.ruoyi.vip,
	如果前期没业务需求可以不跑)

	ServerGateway （网关）
	--config （swagger入口、跨域、熔断配置）
	--filter （限流配置，可在Nacos动态配置限流规则）

	ThatDay （项目业务代码）
	--user （用户服务）
	----config （全局异常，swagger，websocket配置）
	----controller （接口）
	----entity （实体类）
	----repository （jpa操作）
	----rocketmq （该服务的rocketmq配置）
	----service （业务逻辑代码）
	----util （工具类）

