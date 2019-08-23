生物科技公司门户网站管理项目，分为两个部分，一个是公司的门户网站，一个是门户网站的管理系统。


项目地址：待部署

# 项目介绍

整个项目包括两个部分，一个是公司的门户网站，包括对主页，产品，热销，新闻，联系我们等信息的展示。一个是门户网站的管理系统，包括对主页，产品，热销，新闻，联系我们等信息的管理。目前展示模块，管理模块已经开发完成；**权限模块还在开发当中**。

# 整体效果

## 门户网站
![Image text](https://github.com/youzhi0403/swkj/blob/master/README_PICTURE/p1.png)

## 后台管理系统

登录界面

![Image text](https://github.com/youzhi0403/swkj/blob/master/README_PICTURE/p2.png)

系统界面

![Image text](https://github.com/youzhi0403/swkj/blob/master/README_PICTURE/p3.png)


# 技术栈

## 后端技术栈

1.spring 4.24  
2.hibernate 5.0.7  
3.struts2 2.3.24  
4.freemarker 2.3.22  
5.shiro 1.2.3  
6.log4j 1.2.12  
7.slf4j 1.6.6  
8.maven 3.6.1  
9.mysql  

## 前端技术栈

1.jquery  
2.ztree  
3.kindeditor  
4.easyui  

# 快速部署

1.clone项目到本地。

2.修改swkj-web项目下resources文件下db.properties，该为本机mysql账号密码，并创建数据库，名称与配置文件中数据库名称对应。

3.用IntelliJ IDEA打开项目，按照顺序install，swkj-bean -> swkj-utils -> swkj-dao -> swkj-service -> swkj-web。

4.用IntelliJ IDEA运行项目。

5.通过 http://localhost:8080/swkj_web_war_exploded/ 访问门户网站，通过 http://localhost:8080/swkj_web_war_exploded/login.jsp 访问后台管理系统。

**PS：项目运行时由类自动生成数据库表。**

# 文档

待整理
