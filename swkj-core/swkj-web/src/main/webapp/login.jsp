<%--
  Created by IntelliJ IDEA.
  User: HuangRZ
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/9/18
  Time: 10:08
  Description: 后台登录页面
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>企业信息管理系统-登录</title>
    <%@ include file="WEB-INF/pages/common/head.jsp"%>
    <link href="./css/login.css" rel="stylesheet" type="text/css" />
</head>

 <body>
 	<div class="second_body">
    	<form action="<s:url action="userAction_login"/>" method="post">
        	<div class="logo"></div>
            <div class="title-zh">好润生物科技</div>
            <div class="title-en" style="">Enterprise Information Manage System</div>
            <div class="message" ><s:actionerror/></div>
            <table border="0" style="width:300px;">
            	<tr>
                	<td style="white-space:nowrap; padding-bottom: 5px;width:55px;"><label for="userName">用户名：</label></td>
                    <td colspan="2"><input type="text" id="userName" class="login" name="username"/></td>
                </tr>
                <tr>
                    <td class="lable" style="white-space:nowrap; letter-spacing: 0.5em; vertical-align: middle"><label for="password">密码：</label></td>
                    <td colspan="2"><input type="password" id="password" class="login" name="password"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="2"><input type="checkbox" name="rememberMe" value="true"/><span>系统记住我</span></td>
                </tr>
                <tr>
                    <td colspan="3" style="text-align:center">
                        <input type="submit" value="登录" class="login_button" />
                        <input type="button" value="重置" class="reset_button" />
                    </td>
                </tr>
            </table>
        </form>
    </div>

 </body>
<script type="text/javascript" src="js/core/utils.js"></script>
<script type="text/javascript" src="js/login/login.js"></script>

<script type="text/javascript">
    $(function () {
        //TODO 通过cookie技术实现“记住我”功能
    });
</script>
</html>