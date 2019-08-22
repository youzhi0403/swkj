<%--
  Created by IntelliJ IDEA.
  User: HuangRz
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/10/12
  Time: 17:32
  Description: 
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>联系人模块管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/js/kindeditor4/themes/default/default.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor4/kindeditor-all.js"></script>
    <script src="${pageContext.request.contextPath}/js/kindeditor4/lang/zh-CN.js"></script>
    <style>
        /* 解决kindeditor的单张图片上传弹框的一个样式和easyui的样式冲突的问题 */
        .tabs {
            display: none;
        }
    </style>
</head>
<body onload="loadData()">
<form id="fm" method="post" enctype="multipart/form-data">

    <table cellspacing="5px;" style="width: 600px;height: 350px;margin:0 auto">
        <tr style="display: none">
            <td><input type="text" name="id" id="form_id" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td>美国办公室:</td>
            <td><input type="text" name="USA_office" id="form_USA_office" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td>美国邮件:</td>
            <td><input type="text" name="USA_email" id="form_USA_email" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td>亚洲办事处:</td>
            <td><input type="text" name="CN_agency" id="form_CN_agency" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td>工厂地址：</td>
            <td><input type="text" name="factory_address" id="form_factory_address" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td>中国办公室：</td>
            <td><input type="text" name="CN_office" id="form_CN_office" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td>中国邮件：</td>
            <td><input type="text" name="CN_email" id="form_CN_email" class="easyui-validatebox"/></td>
        </tr>
        <tr>
            <td>电话：</td>
            <td><input type="text" name="phone" id="form_phone" class="easyui-validatebox"/></td>
        </tr>
        <tr>
            <td>公司网站：</td>
            <td><input type="text" name="website_address" id="form_website_address" class="easyui-validatebox"/></td>
        </tr>

        <tr>
            <td style="top: 0px">公司图片：</td>
            <td>
                <input type="button" id="form_image_btn" class="easyui-linkbutton" value="选择上传图片" style="width: 100px">
                <!--隐藏的文本输入框，用于保存上传图片返回的url，方便通过表单一起上传到后台。-->
                <input name="picture_url" id="form_picture_url" type="text"
                       style="display: none"/>
            </td>
        </tr>

        <tr style="display: none" id="pictureTR">
            <td></td>
            <td>
                <div id="p_hold" style="max-width: 200px; height: 120px;"></div>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveContact()">保存</a>
            </td>
        </tr>


        <script>
            KindEditor.ready(function (K) {
                var editor = K.editor({
                    uploadJson: '<s:url action="contactAction_uploadPicture"/>',//图片的上传路径
                    allowFileManager: true
                });

                K("#form_image_btn").click(function () {
                    editor.loadPlugin("image", function () {
                        editor.plugin.imageDialog({
                            showRemote: false,
                            clickFn: function (url, title, width, height, border, align) {
                                //给隐藏的文本输入框赋值。
                                console.log("url:"+url);
                                $("#form_picture_url").val(url);
                                K("#p_hold").parent().parent().css("display", '');
                                K("#p_hold").html("");
                                K("#p_hold").append('<img style="width: inherit; height: inherit" src="' + url + '">');
                                editor.hideDialog();
                            }
                        });
                    });
                });
            })


            function saveContact() {
                var url = "<s:url action='contactAction_save'/>";
                $("#fm").form("submit",{
                    url:url,
                    onSubmit:function(){

                    },
                    success:function(result){
                        if(result.errorMsg){
                            $.messager.alert("系统提示",result.errorMsg);
                            return;
                        }else{
                            $.messager.alert("系统提示","保存成功");

                        }
                    }
                });
            }

            function loadData() {
                $.post(
                    "<s:url action='contactAction_getData'/>",
                    {"id":1},
                    function(result) {
                        $("#fm").form("load",result.data);
                        $("#p_hold").parent().parent().css("display", '');
                        $("#p_hold").html("");
                        $("#p_hold").append('<img style="width: inherit; height: inherit" src="' + result.data.picture_url + '">');

                    }
                );
            }
        </script>
    </table>
</form>
</body>

</html>
