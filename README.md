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
如果没有公众号，可从以下地址申请测试公众账号：
[https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)

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

### 使用

```
#获取token
curl -i http://localhost:8082/wechat/accesstoken

#jssdk url签名
curl -X POST -i -d "url=http://wxtest.devincloud.cn/" http://localhost:8082/wechat/sign
```

api已经允许跨域访问，可以直接到过ajax调用

使用Vue的朋友可以参考：
```
Vue.http.post('http://localhost:8082/wechat/sign?url=' + location.href.split('#')[0],{})
  .then(function (response) {
  	console.debug(response)
  	if(response.status == 200){
  		Vue.wechat.config({
	        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	        appId: response.data.appId, // 必填，公众号的唯一标识
	        timestamp: response.data.timestamp, // 必填，生成签名的时间戳
	        nonceStr: response.data.nonceStr, // 必填，生成签名的随机串
	        signature: response.data.signature,// 必填，签名，见附录1
	        jsApiList: ['scanQRCode','chooseImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	    });
	}
  })
  .catch(function (error) {
    console.log(error);
  });
```

### 关于安全

目前仅通过限制跨域访问来限制API的调用，好多工具都能轻松搞定，有以下建议方案：
* 将中控服务器部署在内网，通过防火墙等工具实现仅允许应用服务器访问到中控服务器。
* 自己增加api认证的token，比如使用jwt进行认证……

配置跨域访问：
src/main/resources/application.properties

```
weixin.app.security.domains=http://wxtest.devincloud.cn,http://localhost:8080
```
