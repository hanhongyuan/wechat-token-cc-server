## 微信公众号Access Token中控服务器

详见：[https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183)

### 端口配置
配置文件:src/main/resources/application.properties
可根据需求更改端口：
```
server.port=8082
```

### 公众号配置

需要根据您自己的公众号配置appId和Secret。
```
weixin.app.appid=yourappid
weixin.app.secret=yoursecret
```

### 启动服务

#### 克隆代码

```
git clone https://github.com/ChangjunZhao/wechat-token-cc-server.git
```
#### 调试运行

```
cd wechat-token-cc-server
./gradlew bootRun
```

### Docker中运行

#### 克隆代码

```
git clone https://github.com/ChangjunZhao/wechat-token-cc-server.git
```

#### 打包image

```
cd wechat-token-cc-server
./gradlew buildDocker
```

#### 运行Docker容器
```
docker run -d -p 80:8082 wechat-token-cc-server:1.0
```
