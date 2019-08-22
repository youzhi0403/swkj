<%--
  Created by IntelliJ IDEA.
  User: HuangRz
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/9/22
  Time: 18:13
  Description: 
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>产品模块管理</title>
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

        td input {
            width: 200px;
        }

        body {
            font-size: 12px;
        }
    </style>
    <script type="text/javascript">
        function openAddProductDialog() {
            $("#add_product_dialog").dialog("open").dialog('setTitle', '添加产品');
        }

        //批量删除产品
        function deleteProducts() {
            var selectedRows = $("#product_datagrid").datagrid('getSelections');
            if (selectedRows.length === 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            } else {

            }
            var strIds = [];
            var product;
            for (var i = 0; i < selectedRows.length; i++) {
                if (selectedRows[i].isRec === "1"){
                    $.messager.alert('系统提示', "产品“"+selectedRows[i].product_name+"”是推荐产品，不能删除！");
                    return;
                }
                if (selectedRows[i].isHot === "1") {
                    $.messager.alert('系统提示', "产品“"+selectedRows[i].product_name+"”是热销产品，不能删除！");
                    return;
                }
                product = {};
                product.id = selectedRows[i].id;
                strIds.push(product);
            }
            $.messager.confirm("系统提示", "您确认要删掉这<font color=red>" + selectedRows.length + "</font>条数据吗？", function (r) {
                if (r) {
                    $.post("<s:url action='productAction_delete'/>", {delIds: JSON.stringify(strIds)}, function (result) {
                        if (result.status === 200) {
                            $("#product_datagrid").datagrid("reload");
                        } else {
                            $.messager.alert('系统提示', result.msg);
                        }
                    }, "json");
                }
            });
        }

        //搜索
        function searchProduct() {
            $("#product_datagrid").datagrid('load',{
                'product_name': $("#p_name").textbox('getText'),
                'product_number': $("#p_number").textbox('getText'),
                'series.s_name': $("#p_series").textbox('getText')
            })
        }

        //定义数据表格标题行所有的列
        var columns = [[
            {field: 'cb', checkbox: true},
            {field: 'id', hidden: true, width: 100},
            {title: '产品名称', field: 'product_name', align: 'center', width: 50},
            {title: '产品编号', field: 'product_number', align: 'center', width: 40},
            {title: '容量(ml)', field: 'capacity', align: 'center', width: 30},
            {
                title: '产品描述', field: 'product_desc', width: 100,halign:'center',
                formatter: function (value, row, index) {
                    return value.replace("\r\n", "<br>");
                }
            },
            {
                title: '所属系列', field: 'series', align: 'center', width: 50,
                formatter: function (value, row, index) {
                    return value.s_name;
                }
            },
            {
                title: '产品功能', field: 'function', width: 100,halign:'center',
                formatter: function (value, row, index) {
                    return value.replace("\r\n", "<br>");
                }
            },
            {
                title: '使用方法', field: 'usage', width: 100,halign:'center',

            }
//            {
//                title: '操作',
//                align: 'center',
//                field: 'operate',
//                width: 100,
//                formatter: function (value, row, index) {
//                    return '<a name="opera">详情</a>';
//                }
//            }
        ]];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            //初始化“数据表格”
            $('#product_datagrid').datagrid({
                //定义标题行所有的列
                columns: columns,
                fitColumns: true,
                rownumbers: "true",
                url: "<s:url action='productAction_list'/>",
                fit: true,
                toolbar: "#tb",  //toolbar
                pagination: true,  //显示分页条
                onDblClickRow: editProducts  //双击行事件(index:行的索引值，从0开始   row:行的记录)
            });

            //初始化"添加产品"对话框
            $("#add_product_dialog").dialog({
                width: 400,
                height: 500,
                closed: true,
                resizable: false,
                modal: true
            });

            //初始化“编辑产品”对话框框
            $("#edit_product_dialog").dialog({
                width: 400,
                height: 500,
                closed: true,
                resizable: false,
                modal: true
            });

            //初始化添加单张图片的kindeditor插件
            KindEditor.ready(function (K) {
                var editor_pp = K.editor({
                    uploadJson: '<s:url action="productAction_uploadPicture"/>',//图片的上传路径
                    allowFileManager: true,
                    extraFileUploadParams : {
                        imgFlag : 0
                    }
                });
                var editor_sp = K.editor({
                    uploadJson: '<s:url action="productAction_uploadPicture"/>',//图片的上传路径
                    allowFileManager: true,
                    extraFileUploadParams : {
                        imgFlag : 1
                    }
                });
                //为“添加产品”的窗口的"选择产品图片"按钮绑定点击事件
                K("#add_select_product_img_btn").click(function () {
                    editor_pp.loadPlugin("image", function () {
                        editor_pp.plugin.imageDialog({
                            showRemote: false,
                            clickFn: function (url, title, width, height, border, align) {
                                //给隐藏的文本输入框赋值。
                                K('#add_picture_url').val(url);
                                //判断是否有会被覆盖的图片，有则在覆盖之前删除该在服务器端的图片
                                if (K("#add_p_hold > img")){
                                    $.get('<s:url action="productAction_delPicture"/>',{del_img_url:K("#add_p_hold > img").attr("src")});
                                }
                                K("#add_p_hold").parent().parent().css("display", '');
                                K("#add_p_hold").html("");
                                K("#add_p_hold").append('<img style="width: inherit; height: inherit" src="' + url + '">');
                                editor_pp.hideDialog();
                            }
                        });
                    });
                });

                //为“编辑产品”的窗口的"选择产品图片"按钮绑定点击事件
                K("#edit_select_product_img_btn").click(function () {
                    editor_pp.loadPlugin("image", function () {
                        editor_pp.plugin.imageDialog({
                            showRemote: false,
                            clickFn: function (url, title, width, height, border, align) {
                                //给隐藏的文本输入框赋值。
                                K('#edit_picture_url').val(url);
                                //判断是否有会被覆盖的图片，有则在覆盖之前删除该在服务器端的图片
                                if (K("#edit_p_hold > img")){
                                    $.get('<s:url action="productAction_delPicture"/>',{del_img_url:K("#edit_p_hold > img").attr("src")});
                                }
                                K("#edit_p_hold").parent().parent().css("display", '');
                                K("#edit_p_hold").html("");
                                K("#edit_p_hold").append('<img style="width: inherit; height: inherit" src="' + url + '">');
                                editor_pp.hideDialog();
                            }
                        });
                    });
                });
            })
        });

        //编辑产品
        function editProducts(index, row) {
            console.log(JSON.stringify(row));
            //将指定行的数据放回去窗口中
            $("#edit_product_dialog").form('load', row);
            //打开窗口
            $('#edit_product_dialog').dialog("open").dialog('setTitle', "编辑产品");

            //TODO 获取“系列”值在下拉框中数据列表对应的id
//        var index = $('#s_name').combobox("getData").index(row.seriesName);
            $('#edit_s_name').combobox("select", row.series.id);

            $("#edit_p_hold").parent().parent().css("display", '');
            $("#edit_p_hold").html("");
            $("#edit_p_hold").append('<img style="width: inherit; height: inherit" src="' + row.picture_url + '">');
        }
    </script>
</head>
<body style="margin: 5px; visibility: hidden">

<!--“产品”数据表格-->
<table id="product_datagrid"></table>

<!--新增“产品”的window-->
<div id="add_product_dialog" class="easyui-dialog" style="width: 850px;height: 450px;padding: 10px 20px"
     closed="true" buttons="#add_dlg_buttons">
    <form id="add_product_form" method="post" action="<s:url action="productAction_save"/>">
        <table title="产品新增" style="margin-top: 10px; margin-bottom: 10px" align="center" cellpadding="5">
            <tbody>
            <tr style="display: none">
                <td>id:</td>
                <td><label><input name="id"></label></td>
            </tr>
            <tr style="display: none">
                <td>是否热销：</td>
                <td><label><input name="isHot"></label></td>
            </tr>
            <tr style="display: none">
                <td>是否推荐：</td>
                <td><label><input name="isRec"></label></td>
            </tr>
            <tr>
                <td>产品名称：</td>
                <td><label><input type="text" name="product_name"
                                  class="easyui-textbox" data-options="required:true"></label></td>
            </tr>
            <tr>
                <td>产品编号：</td>
                <td><label><input type="text" name="product_number"
                                  class="easyui-textbox" data-options="required:true"></label></td>
            </tr>
            <tr>
                <td>容量：</td>
                <td><label><input id="capacity" type="text" name="capacity" class="easyui-textbox"
                                  style="width: 170px" data-options="required:true"></label>&nbsp;<a
                        style="font-size: 16px">ml</a>
                    <script type="text/javascript">
                        //TODO 自定义验证框，只能输入数字，因为验证框跟onkeyup事件冲突，无法通过该方法实现功能。
                        //                        $("#capacity").bind("onkeyup",function () {
                        //                            alert("a");
                        //                            $(this).val($(this).val().replace(/[^0-9.]/g, ''));
                        //                        }).bind("paste", function () {  //CTR+V事件处理
                        //                            $(this).val($(this).val().replace(/[^0-9.]/g, ''));
                        //                        }).css("ime-mode", "disabled"); //CSS设置输入法不可用
                    </script>
                </td>
            </tr>
            <tr>
                <td style="padding-top: 0px">产品描述：</td>
                <td><label><input class="easyui-textbox" type="text" name="product_desc"
                                  data-options="height:'80',multiline:true,required:true"></label></td>
            </tr>
            <tr>
                <td style="padding-top: 0px">功能：</td>
                <td><label><input class="easyui-textbox" type="text" name="function"
                                  data-options="height:'80',multiline:true"></label></td>
            </tr>
            <tr>
                <td>用法：</td>
                <td><label><input type="text" name="usage" class="easyui-textbox"
                                  data-options="height:'80',multiline:true"></label></td>
            </tr>
            <tr>
                <td>针对：</td>
                <td><label><input type="text" name="target_problem" class="easyui-textbox"
                                  data-options="height:'50',multiline:true"></label></td>
            </tr>
            <tr>
                <td>方向：</td>
                <td><label><input type="text" name="direction" class="easyui-textbox"
                                  data-options="height:'50',multiline:true"></label></td>
            </tr>
            <tr>
                <td>图片：</td>
                <td><input type="button" id="add_select_product_img_btn" class="easyui-linkbutton" value="选择产品图片"
                           style="width: 100px">
                    <!--隐藏的文本输入框，用于保存上传图片返回的url，方便通过表单一起上传到后台。-->
                    <input id="add_picture_url" type="text" name="picture_url" style="display: none;"/></td>
            </tr>

            <tr style="display: none">
                <td></td>
                <td>
                    <div id="add_p_hold" style="max-width: 200px; height: 120px;"></div>
                </td>
            </tr>
            <tr>
                <td>所属系列：</td>
                <td><label><input id="add_s_name" name="model.series.id" class="easyui-combobox"
                                  data-options="required:true,valueField:'id',textField:'s_name',
                        url:'/swkj_web_war_exploded/seriesAction_allSeries.action',panelHeight:'auto', panelHeight:'100',limitToList:true"
                                  style="width: 150px"/></label>
                    <input class="easyui-linkbutton" id="addSeriesBtn" value="添加系列" style="width: 70px"></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<!--编辑“产品”的window-->
<div id="edit_product_dialog" class="easyui-dialog" style="width: 850px;height: 450px;padding: 10px 20px"
     closed="true" buttons="#edit_dlg_buttons">
    <form id="edit_product_form" method="post" action="<s:url action="productAction_save"/>">
        <table title="产品新增" style="margin-top: 10px; margin-bottom: 10px" align="center" cellpadding="5">
            <tbody>
            <tr style="display: none">
                <td>id:</td>
                <td><label><input name="id"></label></td>
            </tr>
            <tr style="display: none">
                <td>是否热销：</td>
                <td><label><input name="isHot"></label></td>
            </tr>
            <tr style="display: none">
                <td>是否推荐：</td>
                <td><label><input name="isRec"></label></td>
            </tr>
            <tr>
                <td>产品名称：</td>
                <td><label><input type="text" name="product_name"
                                  class="easyui-textbox" data-options="required:true"></label>
                </td>
            </tr>
            <tr>
                <td>产品编号：</td>
                <td><label><input type="text" name="product_number"
                                  class="easyui-textbox" data-options="required:true"></label></td>
            </tr>
            <tr>
                <td>容量：</td>
                <td><label><input type="text" name="capacity" class="easyui-textbox"
                                  style="width: 170px" data-options="required:true"></label>&nbsp;<a
                        style="font-size: 16px">ml</a>
                    <script type="text/javascript">
                        //TODO 自定义验证框，只能输入数字，因为验证框跟onkeyup事件冲突，无法通过该方法实现功能。
                        //                        $("#capacity").bind("onkeyup",function () {
                        //                            alert("a");
                        //                            $(this).val($(this).val().replace(/[^0-9.]/g, ''));
                        //                        }).bind("paste", function () {  //CTR+V事件处理
                        //                            $(this).val($(this).val().replace(/[^0-9.]/g, ''));
                        //                        }).css("ime-mode", "disabled"); //CSS设置输入法不可用
                    </script>
                </td>
            </tr>
            <tr>
                <td style="padding-top: 0px">产品描述：</td>
                <td><label><input id="product_desc" class="easyui-textbox" type="text" name="product_desc"
                                  data-options="height:'80',multiline:true,required:true"></label></td>
            </tr>
            <tr>
                <td style="padding-top: 0px">功能：</td>
                <td><label><input id="function" class="easyui-textbox" type="text" name="function"
                                  data-options="height:'80',multiline:true"></label></td>
            </tr>
            <tr>
                <td>用法：</td>
                <td><label><input id="usage" type="text" name="usage" class="easyui-textbox"
                                  data-options="height:'80',multiline:true"></label></td>
            </tr>
            <tr>
                <td>针对：</td>
                <td><label><input id="target_problem" type="text" name="target_problem" class="easyui-textbox"
                                  data-options="height:'50',multiline:true"></label></td>
            </tr>
            <tr>
                <td>方向：</td>
                <td><label><input id="direction" type="text" name="direction" class="easyui-textbox"
                                  data-options="height:'50',multiline:true"></label></td>
            </tr>
            <tr>
                <td>图片：</td>
                <td><input type="button" id="edit_select_product_img_btn" class="easyui-linkbutton" value="选择产品图片"
                           style="width: 100px">
                    <!--隐藏的文本输入框，用于保存上传图片返回的url，方便通过表单一起上传到后台。-->
                    <input id="edit_picture_url" type="text" name="picture_url" style="display: none"/></td>
            </tr>

            <tr style="display: none">
                <td></td>
                <td>
                    <div id="edit_p_hold" style="max-width: 200px; height: 120px;"></div>
                </td>
            </tr>
            <tr>
                <td>所属系列：</td>
                <td><label><input id="edit_s_name" name="model.series.id" class="easyui-combobox"
                                  data-options="required:true,valueField:'id',textField:'s_name',
                        url:'/swkj_web_war_exploded/seriesAction_allSeries.action',panelHeight:'auto', panelHeight:'100',limitToList:true"
                                  style="width: 150px"/></label></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<%--数据表格的toolbar--%>
<div id="tb">
    <%--按钮--%>
    <div>
        <a onclick="openAddProductDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加产品</a>
        <a onclick="deleteProducts()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>

    <%--搜索框--%>
    <div>
        &nbsp;<label for="p_name">产品名称：</label>&nbsp;
        <input type="text" class="easyui-textbox" name="n_name" id="p_name" size="10"/>
        &nbsp;<label for="p_number">产品编号：</label>&nbsp;
        <input type="text" class="easyui-textbox" name="n_author" id="p_number" size="10"/>
        &nbsp;<label for="p_series">所属系列：</label>&nbsp;
        <input id="p_series" name="model.series.id" class="easyui-combobox"
               data-options="valueField:'id',textField:'s_name',url:'/swkj_web_war_exploded/seriesAction_allSeries.action',
               panelHeight:'auto', panelHeight:'100',limitToList:true" style="width: 150px"/>

        <a onclick="searchProduct()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
</div>

<%--"添加产品"对话框的下边按钮--%>
<div id="add_dlg_buttons">
    <a id="saveBtn" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a id="saveCancelBtn" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
    <script type="text/javascript">
        $(function () {
            //为“保存”按钮绑定click事件
            $("#saveBtn").linkbutton({
                onClick: function () {
                    var add_product_form = $("#add_product_form");
                    //校验表单数据
                    var validateFlag = add_product_form.form("validate");
                    if (!$('#add_picture_url').val()) {
                        alert("请先选择一张图片！");
                        return;
                    }
                    if (validateFlag) {
                        //提交表单，并刷新页面
                        add_product_form.submit();
                    }
                }
            });
            //为“关闭”按钮绑定click事件
            $("#saveCancelBtn").linkbutton({
                onClick: function () {
                    $("#add_product_dialog").window("close");
                }
            });
        });
    </script>
</div>

<%--“编辑产品”对话框的下边按钮--%>
<div id="edit_dlg_buttons">
    <a id="editBtn" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a id="editCancelBtn" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
    <script type="text/javascript">
        $(function () {
            //为“保存”按钮绑定click事件
            $("#editBtn").linkbutton({
                onClick: function () {
                    var edit_product_form = $("#edit_product_form");
                    //校验表单数据
                    var validateFlag = edit_product_form.form("validate");
                    if (!$('#edit_picture_url').val()) {
                        alert("请先选择一张图片！");
                        return;
                    }
                    if (validateFlag) {
                        //提交表单，并刷新页面
                        edit_product_form.submit();
                    }
                }
            });
            //为“关闭”按钮绑定click事件
            $("#editCancelBtn").linkbutton({
                onClick: function () {
                    $("#edit_product_dialog").window("close");
                }
            });
        });
    </script>
</div>

</body>
</html>