# iotdb-kmx-rest

#### 项目介绍
IoTDB与KMX的rest接口

#### 使用说明

1. 运行启动脚本
> ./start.sh
2. 客户端向rest服务所在机器IP的6666端口发送请求
如：
```
http://192.168.130.165:6666/query?db=tsdb&q=SELECT+s_0%2C+s_1+FROM+root.performf.group_0.d_0
```
3.返回查询结果JSON字符串，格式按照KMX标准