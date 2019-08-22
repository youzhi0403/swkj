<%--
  Created by IntelliJ IDEA.
  User: HuangRz
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/10/23
  Time: 10:36
  Description: 
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>产品系列管理</title>
    <%@ include file="../common/head.jsp" %>

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
    <script>

        function doAdd() {
            //居中展示
            $('#add_Series_Window').window("vcenter").window('setTitle','添加系列').window("open");
        }

        function doDelete() {
            var rows = $("#dg").datagrid('getSelections');
            if (rows.length === 0){
                //没有选中
                $.messager.alert('提示信息', '请选择需要删除的系列！', 'warning');
            } else {
                //选中
                $.messager.confirm("系统提示", "您确认要删掉这<font color=red>" + rows.length + "</font>条数据吗？", function (r) {
                    if (r) {
                        var idsArr = [];
                        for (var i = 0; i < rows.length; i++) {
                            idsArr.push(rows[i].id);
                        }
                        //发送请求，刷新页面
                        $.post('<s:url action="seriesAction_delete"/>',{ids:idsArr.join(",")},function (result) {
                            if (result.status === 200) {
                                $("#dg").datagrid("reload");
                            } else {
                                $.messager.alert('系统提示', result.msg);
                            }
                        },"json");
                    }
                });
            }
        }

        //关闭添加窗口
        function onCloseAddSeriesWindow() {
            //判断是否有图片
            if($('input[name="picture_url"]').get(0).val() !== ""
                && $("#s_hold").find("img").attr("src")){
                //删除上传的图片
                $.get('<s:url action="productAction_delPicture"/>',{del_img_url:$("#add_s_hold").find("img").attr("src")});
            }
        }

        var columns = [[
            {field: 'id', checkbox: true},
            {field: 's_name', title: '系列名称', width: 100},
            {
                field: 'picture_url', title: '图片', width: 150, align: 'center',
                formatter: function (data, row, index) {
                    return "<img src='" + data + "' width='30%'/>";
                }
            }
        ]];

        var toolbar = [
            {id: 'tb_add', iconCls: 'icon-add', text: '添加', handler: doAdd},
            {id: 'tb_delete', iconCls: 'icon-remove', text: '删除', handler: doDelete}
        ];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            $("#dg").datagrid({
                iconCls: 'icon-forward',
                fit: true,
                border: false,
                fitColumns: true,
                rownumbers: true,
                pagination: true,
                toolbar: toolbar,
                url: "<s:url action="seriesAction_pageQuery"/>",
                idField: 'id',
                columns: columns,
                onDblClickRow: doDblClickRow
            });

            //初始化“添加系列”窗口
            $("#add_Series_Window").window({
                width: 400,
                height: 500,
                onClose: onCloseAddSeriesWindow
            });

            //初始化“修改系列”窗口
            $("#edit_Series_Window").window({
                width: 400,
                height: 500,
                onClose: onCloseAddSeriesWindow
            });

            //初始化添加单张图片的kindeditor插件
            KindEditor.ready(function (K) {
                var editor_sp = K.editor({
                    uploadJson: '<s:url action="seriesAction_uploadPicture"/>',//图片的上传路径
                    allowFileManager: true
                });
                //为"选择系列图片"按钮绑定点击事件
                K("#add_select_series_img_btn").click(function () {
                    editor_sp.loadPlugin("image", function () {
                        editor_sp.plugin.imageDialog({
                            showRemote: false,
                            clickFn: function (url, title, width, height, border, align) {
                                //判断是否有会被覆盖的图片，有则在覆盖之前删除该在服务器端的图片
                                if (K("#add_s_hold > img").attr("src") !== "") {
                                    $.get('<s:url action="productAction_delPicture"/>', {del_img_url: K("#add_s_hold > img").attr("src")});
                                }
                                K("#addSeriesForm > input[name='picture_url']").val(url);
                                K("#add_s_hold").css("display", '');
                                K("#add_s_hold").html("");
                                K("#add_s_hold").append('<img style="width: inherit; height: inherit" src="' + url + '">');
                                editor_sp.hideDialog();
                            }
                        });
                    });
                });
                //为"选择系列图片"按钮绑定点击事件
                K("#edit_select_series_img_btn").click(function () {
                    editor_sp.loadPlugin("image", function () {
                        editor_sp.plugin.imageDialog({
                            showRemote: false,
                            clickFn: function (url, title, width, height, border, align) {
                                //判断是否有会被覆盖的图片，有则在覆盖之前删除该在服务器端的图片
                                if (K("#edit_s_hold > img").attr("src") !== "") {
                                    $.get('<s:url action="productAction_delPicture"/>', {del_img_url: K("#edit_s_hold > img").attr("src")});
                                }
                                K("#editSeriesForm > input[name='picture_url']").val(url);
                                K("#edit_s_hold").css("display", '');
                                K("#edit_s_hold").html("");
                                K("#edit_s_hold").append('<img style="width: inherit; height: inherit" src="' + url + '">');
                                editor_sp.hideDialog();
                            }
                        });
                    });
                });
            })
        });

        //双击编辑系列
        function doDblClickRow(index, row) {
            $('#edit_Series_Window').window("vcenter").window('setTitle','修改系列').window("open");
            //回显数据
            $("#editSeriesForm").form('load',row);
            $("#edit_s_hold").css("display", '');
            $("#edit_s_hold").html("");
            $("#edit_s_hold").append('<img style="width: inherit; height: inherit" src="' + row.picture_url + '">');
        }

    </script>

</head>
<body style="margin: 5px; visibility: hidden">
<%-- 数据表格 --%>
<table id="dg"></table>

<%--添加“系列”的窗口--%>
<div id="add_Series_Window" class="easyui-window" title="添加系列"
     style="text-align: center; padding-top: 10px; align-items:center;"
     data-options="modal:true,closed:true, resize:false, collapsible:false, minimizable:false, maximizable:false">
    <form id="addSeriesForm" action="<s:url action="seriesAction_add"/>" method="post">
        <input name="s_name" class="easyui-textbox" style="text-align: center" title="系列名称"
               data-options="prompt:'请输入系列名称'"><br>
        <input name="picture_url" type="text" style="display: none" title="系列图片的访问路径">
    </form>
    <a id="add_select_series_img_btn" class="easyui-linkbutton" style="margin-top: 10px">选择系列图片</a><br>
    <div id="add_s_hold" style="width: 270px; height: 270px; display: none;margin:0 auto ">
    </div>
    <br>
    <div style="margin-top: 10px">
        <a id="addSeriesBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">添加</a>
        <%--<a id="cancelSeriesBtn" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>--%>
    </div>
    <script>
        $(function () {
            //为“添加”按钮绑定click事件
            $('#addSeriesBtn').bind("click", function () {
                //判断是否选择图片
                if (!$("#add_s_hold").find("img")) {
                    alert("请先选择一张图片！");
                    return;
                }
                $("#addSeriesForm").submit();
            });
        });
    </script>
</div>

<%--修改“系列”的窗口--%>
<div id="edit_Series_Window" class="easyui-window" title="添加系列"
     style="text-align: center; padding-top: 10px; align-items:center;"
     data-options="modal:true,closed:true, resize:false, collapsible:false, minimizable:false, maximizable:false">
    <form id="editSeriesForm" action="<s:url action="seriesAction_add"/>" method="post">
        <input type="text" style="display: none" name="id" title="隐藏域">
        <input name="s_name" class="easyui-textbox" style="text-align: center" title="系列名称"
               data-options="prompt:'请输入系列名称'"><br>
        <input name="picture_url" type="text" style="display: none" title="系列图片的访问路径">
    </form>
    <a id="edit_select_series_img_btn" class="easyui-linkbutton" style="margin-top: 10px">选择系列图片</a><br>
    <div id="edit_s_hold" style="width: 270px; height: 270px; display: none;margin:0 auto ">
    </div>
    <br>
    <div style="margin-top: 10px">
        <a id="saveSeriesBtn" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
        <%--<a id="cancelSeriesBtn" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>--%>
    </div>
    <script>
        $(function () {
            //为“添加”按钮绑定click事件
            $('#saveSeriesBtn').bind("click", function () {
                //判断是否选择图片
                if (!$("#edit_s_hold").find("img")) {
                    alert("请先选择一张图片！");
                    return;
                }
                $("#editSeriesForm").submit();
            });
        });
    </script>
</div>

</body>
</html>
