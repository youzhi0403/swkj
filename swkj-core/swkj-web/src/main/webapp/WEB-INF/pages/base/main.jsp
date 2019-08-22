<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/25
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        $(function(){
            // 实例化树菜单
            $("#tree").tree({
                url:'${pageContext.request.contextPath}/json/menuItemTree.json',
                lines:true,
                animate:true,
                onClick:function(node){
                    if(node.attributes){
                        openTab(node.text,node.attributes.url);
                    }
                }
            });

            // 新增Tab
            function openTab(text,url){
                var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
                if($("#tabs").tabs('exists',text)){
                    $("#tabs").tabs('select', text); // 切换tab
                    var tab = $("#tabs").tabs('getSelected');
                    $("#tabs").tabs('update', {
                        tab: tab,
                        options: {
                            title: text,
                            content: content
                        }
                    });
                }else{
                    $("#tabs").tabs('add',{
                        title:text,
                        closable:true,
                        content:content
                    });
                }
            }

            //为“修改密码”的取消按钮绑定事件
            $("#btnCancel").click(function(){
                $('#editPwdWindow').window('close');
            });

            //为“修改密码”的确定按钮绑定事件
            $("#btnEp").click(function(){
                //进行表单校验
                var v = $("#editPasswordForm").form("validate");
                if(v){
                    //表单校验通过，手动校验两次输入是否一致
                    var v1 = $("#txtNewPass").val();
                    var v2 = $("#txtRePass").val();
                    if(v1 == v2){
                        //两次输入一致，发送ajax请求
                        $.post("userAction_editPassword.action",{"password":v1},function(data){
                            if(data.status === 200){
                                //修改成功，关闭修改密码窗口
                                $("#editPwdWindow").window("close");
                            }else{
                                //修改密码失败，弹出提示
                                $.messager.alert("提示信息",data.msg,"error");
                            }
                        });
                    }else{
                        //两次输入不一致，弹出错误提示
                        $.messager.alert("提示信息","两次密码输入不一致！","warning");
                    }
                }
            });
        });

        // 退出登录
        function logoutFun() {
            $.messager
                .confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
                    if (isConfirm) {
                        location.href = '${pageContext.request.contextPath }/userAction_logout.action';
                    }
                });
        }
        // 修改密码
        function editPassword() {
            //打开修改密码窗口
            $('#editPwdWindow').window('open');
        }
        // 版权信息
        function showAbout(){
            $.messager.alert("信息管理系统","管理员邮箱: huangrz11@163.com");
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 80px;background-color: #E0EDFF">
    <%--"信息管理系统"图片--%>
    <div align="left" style="width: 80%;float: left"><img src="${pageContext.request.contextPath}/image/main/main.png"></div>

    <%--显示用户名称--%>
    <div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
        [<strong><s:property value="#session.loginUser.username"/></strong>]，欢迎你！
    </div>

    <div style="position: absolute; right: 15px; bottom: 10px; ">
        <a href="javascript:void(0);" class="easyui-menubutton"
           data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
    </div>

    <%--“控制面板”按钮的菜单--%>
    <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
        <div onclick="editPassword();">修改密码</div>
        <div onclick="showAbout();">联系管理员</div>
        <div class="menu-sep"></div>
        <div onclick="logoutFun();">退出系统</div>
    </div>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" >
            <div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用</font></div>
        </div>
    </div>
</div>

<div region="west" style="width: 150px;" title="导航菜单" split="true">
    <ul id="tree"></ul>
</div>

<div region="south" style="height: 25px;" align="center"></div>

<!--修改密码窗口-->
<div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
     maximizable="false" icon="icon-save"  style="width: 330px; height: 180px; padding: 5px;
        background: #fafafa">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
            <form id="editPasswordForm">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input  required="true" data-options="validType:'length[4,6]'" id="txtNewPass" type="Password" class="txt01 easyui-validatebox" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input required="true" data-options="validType:'length[4,6]'" id="txtRePass" type="Password" class="txt01 easyui-validatebox" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a>
            <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
        </div>
    </div>
</div>
</body>
</html>
