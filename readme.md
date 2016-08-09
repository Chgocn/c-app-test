# C-app
## 项目简介

根据以往项目经验封装出一个普通的lib库，包括基类，工具以及自定义View，便于完成公共操作，后续开发只需要直接使用就可以完成大部分操作，节省开发成本。

## Keystore
- storeFile file("../c.jks")
- storePassword "T1nN0ycO"
- keyAlias "release.key"
- keyPassword "T1nN0ycO"

<!--[Internal Dev Version]()-->
[开发版]()

<!--[Release Version]()-->
[发布版]()

### App
<!--All the biz/log code of the app will be under this module.-->
应用程序的所有商业/逻辑代码都会在App模块下。

### Lib
<!--Lib is the common lib include some utils and widgets, **do not** add biz or log code into this module.-->
Lib 是常见的 lib 包括一些 utils 和小部件，不添加 商业和逻辑代码到此模块下。

<!-- ### Share
Share is the lib support share and sns login, powered by [ShareSDK](http://sharesdk.mob.com/#/sharesdk).  -->

### Umeng
<!--Umeng is the lib support app auto update and analytics, powered by [Umeng](http://www.umeng.com/). -->
Umeng is the lib support app auto update and analytics, powered by [Umeng](http://www.umeng.com/). 

## Help
<!--pls visit [wiki pages]()-->
请查看[Wiki Pages]()