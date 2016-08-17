# SeeHttp
**SeeHttp** 是一个纯Java实现的HTTP抓包工具。

# 原理
通过创建Socks5代理服务器，来抓取通过的所有明文HTTP报文

# 依赖

1. [sockslib](https://github.com/fengyouchao/sockslib)(socks协议实现库)
2. [http-parse](https://github.com/fengyouchao/http-parse)(HTTP流量探测解析库)
3. [seehttp-brower](https://github.com/fengyouchao/seehttp-browser)(JavaFX实现的简单浏览器)

# 功能
1. 抓取HTTP报文
2. 修改报文重新发送
3. 内置浏览器,无需配置,直接抓包(对其他软件抓包需要手动配置Socks5代理)


# Snapshot

![Screenshot 1](http://fengyouchao.github.io/seehttp/images/example.gif "Screenshot")
