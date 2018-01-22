# 简介
zookeeper 练习项目
本 demo 模拟集群下，有多个 zkCli， 分别部署在多台机器下。
由 Zookeeper 监控多个点的存活状态。

实现方式是将不同 zkCli 映射到 zk 的不同节点下，哪个 zkCli 不存活就移除对应节点。

## 运行
1. 在 config.properties 里修改成你的zk配置
2. 手动创建路径 `/develop/serviceRegistry/demo-zk/5` 
3. 运行 DemoMain

