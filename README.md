技术栈：
Spring Cloud 2.1.3

Spring Cloud Gateway
-Rate Limit

Nacos 1.0.0
-DiscoveryClient
-Nacos-config

Sentinel 1.5.0
Feign
JPA

RocketMQ 4.5.0
Swagger 2.9.2

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

Pub （公共服务目录）
--Common-Server （公共服务）
----tinyid （滴滴开源的序列ID生成器）
----controller （接口）

ServerGateway （网关）
--config （swagger入口、跨域、熔断配置）
--filter （限流配置，可动态配置Nacos）

ThatDay （项目业务代码）
--user （用户服务）
----config （全局异常，swagger，websocket配置）
----controller （接口）
----entity （实体类）
----repository （jpa操作）
----rocketmq （该服务的rocketmq配置）
----service （业务逻辑代码）
----util （工具类）

