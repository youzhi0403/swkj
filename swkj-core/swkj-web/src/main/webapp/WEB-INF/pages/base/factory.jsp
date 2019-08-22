<%--
  Created by IntelliJ IDEA.
  User: HuangRz
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/10/12
  Time: 17:31
  Description: 
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="S" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>工厂模块管理</title>
    <%@ include file="../common/head.jsp" %>

    <!--引入富文本编辑器KindEditor-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/js/kindeditor4/themes/default/default.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor4/kindeditor-all.js"></script>
    <script src="${pageContext.request.contextPath}/js/kindeditor4/lang/zh-CN.js"></script>
    <script>
        function editContent() {
            $("#div_content_display").css('display', 'none');
            $("#div_content_edit").css('display', '')
            //把数据放到KE中
            KindEditor.instances[0].html($("#div_content").html());
        }

        function saveContent() {
            console.log(editor.html());
            //把数据上传服务器
            $.post(
                "<s:url action="factoryAction_uploadContent"/>",
                {content: editor.html()},
                function (result) {
                    if (result.status === 200) {
                        //切换div
                        $("#div_content_display").css('display', '');
                        $("#div_content_edit").css('display', 'none');

                        //把数据放到div_content中
                        $("#div_content").html(editor.html());
                    } else {
                        alert(result.msg);
                    }
                }, "json"
            );
        }

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            $.ajax({
                url: "<s:url action="factoryAction_getContent"/>",
                success: function (result) {
                    if (result.status === 200) {
                        $("#div_content").html(result.data);
                    } else {
                        alert(result.msg);
                    }
                },
                type: 'POST',
                dataType: "json",
                contentType: "application/json",
                async: true
            });
        });
    </script>
    <style>
        .container {
            margin-right: auto;
            margin-left: auto;
            padding-left: 15px;
            padding-right: 15px;
        }
    </style>
</head>
<body style="height: 100%; width: 100%; visibility: hidden">

<div id="div_content_display" style="width: 100%; height: 100%">
    <div class="datagrid-toolbar" style="padding-left: 10px">
        <a class="easyui-linkbutton" onclick="editContent()" data-options="iconCls:'icon-edit', plain:true">编辑</a>
    </div>
    <div class="wrapper container generic">
        <div id="div_content" class="content">
        </div>
    </div>
</div>

<div id="div_content_edit" style="width: 100%; height: 100%; display: none">
    <div class="datagrid-toolbar" style="padding-left: 10px">
        <a class="easyui-linkbutton" onclick="saveContent()" data-options="iconCls:'icon-save', plain:true">保存</a>
    </div>
    <textarea name="content" style="width: inherit; height: inherit; display: block;"></textarea>
    <script>
        var editor;
        KindEditor.ready(function (K) {
            editor = K.create('textarea[name="content"]', {
                width: '100%',
                height: '500px',
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: false,
                allowFileManager: true,
                items: [
                    'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',
                    'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                    'anchor', 'link', 'unlink', '|', 'about'
                ]
            });
        });
    </script>
</div>

</body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/global.css"/>
</html>
