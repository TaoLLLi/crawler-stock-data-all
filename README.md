## Java使用selenium抓取同花顺股票数据（附源码）

### 配置环境

先介绍下工程所需要的环境，你需要安装Maven，如果没装，点击：

关键是安装 ChromeDriver ，需要安装和当前chrome版本一致的驱动才写。

查看chrome版本：chrome浏览器输入：`Chrome://version`

ChromeDriver各版本的下载地址：

淘宝镜像：[https://npm.taobao.org/mirrors/chromedriver](https://npm.taobao.org/mirrors/chromedriver)
谷歌下载（需要翻墙，不推荐）：[https://sites.google.com/a/chromium.org/chromedriver/downloads](https://sites.google.com/a/chromium.org/chromedriver/downloads)

下面这一步可做可不做，不做也能启动工程，只是需要修改代码中的一个配置即可。

> 配置方式：
>
> 将下载好的ChromeDriver文件放到`/usr/local/bin/`目录下：
>
> ```shell
> cp chromedriver /usr/local/bin/
> ```
>
> 检测是否安装成功
>
> ```shell
> chromedriver --version
> ```
>
> 

如果不配置，只需要记得ChromeDriver的路径，比如我的是：`/Users/admin/Documents/selenium/chrome/79.0.3945.36/chromedriver`

### 验证方案

![img](https://mmbiz.qpic.cn/mmbiz_png/EAicxTzPVtvK1dyMkicR4T6GchsbVAkhmbhQOcibVh9sibsr2sU7LiasjBIt1wdNnaF2Uj0RMicFkWtF2IL11hdNy4eA/640)

>  **抓取数据入库，验证成功！**

### 注意：

记得修改代码里数据库地址和ChromeDriver的路径。

