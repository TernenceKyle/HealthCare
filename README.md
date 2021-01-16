# HealthCare
一个分布式的项目
>SSM + dubbo + zookeeper Maven 分布式项目 
## 模块结构
* hc_parent 父项目
* hc_service_provider 服务生产
* hc_backend 服务消费
* hc_interface 公共接口
* hc_common  公共依赖
#### 遇到的问题
* dubbo RPC 框架 的 qos port 占用问题。缺省的 qos port = 222222

