## Java使用selenium抓取同花顺股票数据（附源码）

### 调研背景

于是我点开了同花顺的板块页面：[http://q.10jqka.com.cn/gn/](http://q.10jqka.com.cn/gn/)
发现有好268个概念：

![板块网页](https://upload-images.jianshu.io/upload_images/2710833-19be71f804afe66c.png)

分析概念板块的网页HTML发现，268个概念的URL就在HTML中：

![板块-网页源码（全部268个概念）](https://upload-images.jianshu.io/upload_images/2710833-672dc72bcc1187cf.png)

打开其中的“阿里巴巴概念”，发现网页又有分页：

![分页信息](https://upload-images.jianshu.io/upload_images/2710833-75dc170c5869e28f.png)

分页的数据，是根据接口实时获取的，接口中注入了一些Cooki信息和其他标识，同花顺的反爬虫策略一直比较强，使用模拟接口的方式可能难度会比较大，所以使用`selenium`模拟浏览器操作这种方式比较完美。

### 设计方案

技术方向有了，再简单整理一下思路：

* 根据http://q.10jqka.com.cn/gn/，获取板块网页的源码HTML，用Jsoup解析HTML获取每个概念的url信息放到List中
* 遍历List，根据概念的url获取概念网页源码HTML，解析股票信息
  * 再递归点击执行“下一页”操作，获取每一页的股票数据，直至尾页
* 把股票信息存储到数据库

### 配置环境

先介绍下工程所需要的环境，你需要安装Maven，如果没装，点击：

我们使用的方案是模拟浏览器的操作，所以我们需要在电脑安装chrome浏览器和chromedriver驱动。chrome的安装这里就不说了，百度下载个浏览器就行。

关键是安装 ChromeDriver ，需要安装和当前chrome版本一致的驱动才写。

查看chrome版本：chrome浏览器输入：`Chrome://version`

![我的chrome的版本](https://upload-images.jianshu.io/upload_images/2710833-d468cab33620ed8e.png)

在根据版本下载对于的驱动，版本最好要一致，比如我的是：79.0.3945.117 (正式版本) （64 位），我下载的就是 79.0.3945.36。

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

记得修改代码里数据库地址和ChromeDriver的路径。

记得修改代码里数据库地址和ChromeDriver的路径。

**遇到问题也可以关注公众号：java之旅或扫描下方二维码，回复【加群】，加我个人微信询问我**

![关注公众号：java之旅](https://upload-images.jianshu.io/upload_images/2710833-917dd89795bb306a.png)

