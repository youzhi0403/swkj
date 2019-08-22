<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/25
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <!--引入富文本编辑器KindEditor-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/js/kindeditor4/themes/default/default.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor4/kindeditor-all.js"></script>
    <script src="${pageContext.request.contextPath}/js/kindeditor4/lang/zh-CN.js"></script>
    <style>
        /* 解决kindeditor的单张图片上传弹框的一个样式和easyui的样式冲突的问题 */
        .tabs {
            display: none;
        }

        /* 覆盖easyui的行颜色和行选中颜色 */
        .datagrid-row-selected {
            background: #D1EEEE; /*行选中颜色*/
            color: #ccc; /*行颜色*/
        }
    </style>
    <script type="text/javascript">
        var url;
        var imgUrl;

        function deleteNews() {
            var selectedRows = $("#dg").datagrid('getSelections');
            if (selectedRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            var strIds = [];
            for (var i = 0; i < selectedRows.length; i++) {
                strIds.push("\'" + selectedRows[i].id + "\'");
            }
            var ids = strIds.join(",");
            $.messager.confirm("系统提示", "您确认要删掉这<font color=red>" + selectedRows.length + "</font>条数据吗？", function (r) {
                if (r) {
                    $.post("<s:url action='news_delete'/>", {delIds: ids}, function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "您已成功删除<font color=red>" + result.delNums + "</font>条数据！");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert('系统提示', result.errorMsg);
                        }
                    }, "json");
                }
            });
        }

        function searchNews() {
            $('#dg').datagrid('load', {
                title: $('#n_name').val(),
                news_author: $('#n_author').val()
            });
        }

        function openNewsAddDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "添加新闻信息");
            url = "<s:url action="news_save"/>";
        }

        function saveNews() {
            //提交表单之前，先判断富文本内容是否超过指定大小
            if (!restrictStorage(editor.html())) {
                return;
            }

            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {

                },
                success: function (result) {
                    if (result.errorMsg) {
                        $.messager.alert("系统提示", result.errorMsg);
                        return;
                    } else {
                        $.messager.alert("系统提示", "保存成功");
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    }
                }
            });
        }

        function resetValue() {
            $("#form_id").val("");
            $("#form_origin").val("");
            $("#form_create_date").val("");
            $("#form_page_view").val("");
            $("#form_page_url").val("");
            $("#form_title").val("");
            $("#form_author").val("");
            $("#form_picture_url").val("");

            $("#pictureTR").css("display", "none");

            KindEditor.instances[0].html('');
            $("#form_content").val("");

        }

        jQuery(document).ready(function () {
            $('#dlg').dialog({
                onClose: function () {
                    resetValue();
                }
            });
        });

        function closeNewsDialog() {
            $("#dlg").dialog("close");
        }

        function openNewsModifyDialog() {
            var selectedRows = $("#dg").datagrid('getSelections');
            if (selectedRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            }
            var row = selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle", "编辑新闻信息");
            $("#fm").form("load", row);

            //请求新闻内容，load进富文本
            $.post(
                "<s:url action='news_getNewsContent'/>",
                {"id": row.id},
                function (result) {
                    editor.html(result.data);
                    imgUrl = $("#form_picture_url").val();
                    $("#p_hold").parent().parent().css("display", '');
                    $("#p_hold").html("");
                    $("#p_hold").append('<img style="width: inherit; height: inherit" src="' + imgUrl + '">');
                    $("#form_content").val(result.data);
                    url = "<s:url action="news_save"/>";

                }
            );
        }

        var maxStorage = 1024 * 1024 * 2;

        function restrictStorage(str) {

            var bytesCount = 0;
            for (var i = 0; i < str.length; i++) {
                var c = str.charAt(i);
                if (/^[\u0000-\u00ff]$/.test(c)) {  //匹配双字节
                    bytesCount += 1;
                } else {
                    bytesCount += 2;
                }
            }
            if (bytesCount > maxStorage) {
                alert("新闻内容不得超过2M");
                return false;
            }
            return true;
        }

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});
        });

    </script>
    <style>
        body {
            font-size: 12px;
        }
    </style>
</head>
<body style="margin: 5px; visibility: hidden">
<table id="dg" class="easyui-datagrid" fitColumns="true"
       pagination="true" rownumbers="true" url="<s:url action="news_list"/> " fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true"></th>
        <%--<th field="num" width="50">编号</th>--%>
        <th field="title" width="100">标题</th>
        <th field="origin" width="100">来源</th>
        <th field="news_author" width="100">作者</th>
        <th field="create_date" width="100">发布日期</th>
        <th field="page_view" width="100">浏览次数</th>

        <th field="id" width="100" hidden="true">id</th>
        <%--<th field="news_description" width="100" hidden="true">新闻描述</th>--%>
        <th field="page_url" width="100" hidden="true">静态页面url</th>
        <th field="title_picture_url" width="100" hidden="true">新闻图片</th>
    </tr>
    </thead>
</table>

<div id="tb">
    <div>
        <a href="javascript:openNewsAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openNewsModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteNews()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>

    <div>
        &nbsp;标题：&nbsp;<input type="text" name="n_name" id="n_name" size="10"/>
        &nbsp;作者：&nbsp;<input type="text" name="n_author" id="n_author" size="10"/>

        <a href="javascript:searchNews()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 850px;height: 450px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" enctype="multipart/form-data">
        <table cellspacing="5px;" style="width: 600px;height: 350px;">
            <tr style="display: none">
                <td><input type="text" name="id" id="form_id" class="easyui-validatebox"/></td>
            </tr>

            <tr style="display: none;">

                <td><input type="text" name="origin" id="form_origin" class="easyui-validatebox"/></td>
            </tr>

            <tr style="display: none;">
                <td><input type="text" name="create_date" id="form_create_date" class="easyui-validatebox"/></td>
            </tr>

            <tr style="display: none">
                <td><input type="text" name="page_view" id="form_page_view" class="easyui-validatebox"/></td>
            </tr>

            <tr style="display: none">
                <td><input type="text" name="page_url" id="form_page_url" class="easyui-validatebox"/></td>
            </tr>

            <tr>
                <td>新闻标题：</td>
                <td><input type="text" name="title" id="form_title" class="easyui-validatebox"/></td>
            </tr>

            <tr>
                <td>作者：</td>
                <td><input type="text" name="news_author" id="form_author" class="easyui-validatebox"/></td>
            </tr>

            <tr>
                <td style="top: 0px">标题图片：</td>
                <td>
                    <input type="button" id="form_image_btn" class="easyui-linkbutton" value="选择上传图片"
                           style="width: 100px">
                    <!--隐藏的文本输入框，用于保存上传图片返回的url，方便通过表单一起上传到后台。-->
                    <input name="title_picture_url" id="form_picture_url" type="text" name="form_picture_url"
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
                <td valign="top">新闻内容：(图片最好经过压缩再上传)</td>
                <td><textarea name="news_description" id="form_content"
                              style="width: inherit; height: 300px"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveNews()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeNewsDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<script type="text/javascript">
    var editor;
    KindEditor.ready(function (K) {
        editor = K.create('textarea[name="news_description"]', {
            resizeType: 1,
            allowPreviewEmoticons: false,
            uploadJson: '<s:url action="news_uploadPicture"/>',//图片的上传路径
            allowFileManager: true,
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'link'],
            afterBlur: function () {
                this.sync();
            }
        });
        K("#form_image_btn").click(function () {
            editor.loadPlugin("image", function () {
                editor.plugin.imageDialog({
                    showRemote: false,
                    clickFn: function (url, title, width, height, border, align) {
                        //给隐藏的文本输入框赋值。
                        K('#form_picture_url').val(url);
                        K("#p_hold").parent().parent().css("display", '');
                        K("#p_hold").html("");
                        K("#p_hold").append('<img style="width: inherit; height: inherit" src="' + url + '">');
                        editor.hideDialog();
                    }
                });
            });
        });
    });
</script>

</body>

</html>
