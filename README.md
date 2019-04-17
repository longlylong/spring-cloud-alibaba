技术栈：
<br/>
Spring Cloud 2.1.3
<br/>
Spring Cloud Gateway
<br/>
-Rate Limit
<br/>
<br/>
Nacos 1.0.0
<br/>
-DiscoveryClient
<br/>
-Nacos-config
<br/>
<br/>
Sentinel 1.5.0
<br/>
Feign
<br/>
JPA
<br/>
<br/>

RocketMQ 4.5.0
<br/>
Swagger 2.9.2
<br/>
<br/>
项目结构介绍：
<br/>
Common （公共模块）
<br/>
--constant （常量）
<br/>
--controller （controller基类）
<br/>
--exception （全局异常配置）
<br/>
--Jackson （Jackson配置）
<br/>
--model （模块之间公用的对象）
<br/>
--rocketmp （rocketmq配置）
<br/>
--utils （模块之间共用的util）
<br/>
--validation （模块之间共用的校验器）
<br/>
<br/>

Pub （公共服务目录）
<br/>
--Common-Server （公共服务）
<br/>
----tinyid （滴滴开源的序列ID生成器）
<br/>
----controller （接口）
<br/>
<br/>

ServerGateway （网关）
<br/>
--config （swagger入口、跨域、熔断配置）
<br/>
--filter （限流配置，可在Nacos动态配置限流规则）
<br/>
<br/>

ThatDay （项目业务代码）
<br/>
--user （用户服务）
<br/>
----config （全局异常，swagger，websocket配置）
<br/>
----controller （接口）
<br/>
----entity （实体类）
<br/>
----repository （jpa操作）
<br/>
----rocketmq （该服务的rocketmq配置）
<br/>
----service （业务逻辑代码）
<br/>
----util （工具类）
<br/>
<br/>

