<%--
  Created by IntelliJ IDEA.
  User: HuangRz
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/10/12
  Time: 17:04
  Description: 管理前台首页的后台页面
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>首页模块管理</title>
    <%@ include file="../common/head.jsp" %>

    <!--引入富文本编辑器KindEditor-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/js/kindeditor4/themes/default/default.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor4/kindeditor-all.js"></script>
    <script src="${pageContext.request.contextPath}/js/kindeditor4/lang/zh-CN.js"></script>

    <style>
        /* 覆盖easyui的行颜色和行选中颜色 */
        .datagrid-row-selected {
            background: #D1EEEE; /*行选中颜色*/
            color: #ccc; /*行颜色*/
        }

        /* 解决kindeditor的单张图片上传弹框的一个样式和easyui的样式冲突的问题 */
        .tabs {
            display: none;
        }
    </style>
    <script>
        //删除轮播图
        function deleteImg() {
            var selectedRows = $("#pic_table").datagrid('getSelections');
            if (selectedRows.length === 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            var strIds = [];
            var slideImg;
            for (var i = 0; i < selectedRows.length; i++) {
                slideImg = {};
                slideImg.img_url = selectedRows[i].img_url;
                slideImg.id = selectedRows[i].id;
                strIds.push(slideImg);
            }
            $.messager.confirm("系统提示", "您确认要删掉这<font color=red>" + selectedRows.length + "</font>条数据吗？", function (r) {
                if (r) {
                    $.post("<s:url action='frontAction_deleteImgs'/>", {delIds: JSON.stringify(strIds)}, function (result) {
                        if (result.status === 200) {
                            $.messager.alert("系统提示", "您已成功删除<font color=red>" + strIds.length + "</font>条数据！");
                            $("#pic_table").datagrid("reload");
                        } else {
                            $.messager.alert('系统提示', result.msg);
                        }
                    }, "json");
                }
            });
        }

        //上移
        function moveUp(index) {
            alert("暂未实现该功能！")
        }

        //下移
        function moveDown(index) {
            alert("暂未实现该功能！")
        }

        //编辑
        function editImg(index) {
            $('#pic_table').datagrid('selectRow', index);
            var row = $('#pic_table').datagrid('getSelected');
            KindEditor.ready(function (K) {
                var editor = K.editor({
                    uploadJson: '<s:url action="frontAction_updateImg"/>?id=' + row.id + '&&img_url=' + row.img_url + '&&img_index=' + row.img_index,//图片的上传路径
                    allowFileManager: true
                });
                //添加轮播图片时，图片单张上传插件
                editor.loadPlugin("image", function () {
                    editor.plugin.imageDialog({
                        showRemote: false,
                        clickFn: function (url, title, width, height, border, align) {
                            editor.hideDialog();
                            //刷新数据表格
                            $("#pic_table").datagrid('reload');
                        }
                    });
                });
            });
        }

        //“轮播图”数据表格的列
        var pic_columns = [[
            {
                field: 'id',
                checkbox: true
            },
            {
                title: '轮播图片', field: 'img_url', width: 100, align: 'center',
                formatter: function (data, row, index) {
                    return "<img src='" + data + "' width='80%'/>";
                }
            },
            {
                title: '操作', field: 'operate', width: 20, align: 'center',
                formatter: function (data, row, index) {
//                    var up = '<a onclick="moveUp(' + index + ')" href="javascript:void(0);">上移</a>&nbsp;';
//                    var down = '<a onclick="moveDown(' + index + ')" href="javascript:void(0);">下移</a>&nbsp;';
                    var edit = '<a onclick="editImg(' + index + ')" href="javascript:void(0);">修改</a>';
                    return edit;
                }
            }
        ]];

        //打开添加“推荐产品”窗口
        function openRecProductWindow() {
            $("#add_recPro_window").window("open").window('setTitle', '添加产品');
        }

        //删除“推荐产品”
        function deleteRecProduct() {
            var selectedRows = $("#pro_table").datagrid('getSelections');
            if (selectedRows.length === 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }
            var strIds = [];
            for (var i = 0; i < selectedRows.length; i++) {
                strIds.push(selectedRows[i].id);
            }
            $.messager.confirm("系统提示", "您确认要删掉这<font color=red>" + selectedRows.length + "</font>条数据吗？", function (r) {
                if (r) {
                    $.post("<s:url action='frontAction_deleteRecPro'/>", {delIds: JSON.stringify(strIds)}, function (result) {
                        if (result.status === 200) {
                            $.messager.alert("系统提示", "您已成功删除<font color=red>" + strIds.length + "</font>条数据！");
                            $("#pro_table").datagrid("reload");
                        } else {
                            $.messager.alert('系统提示', result.msg);
                        }
                    }, "json");
                }
            });
        }

        //清空添加“推荐产品”窗口的内容
        function clearAddRecProWindowContent() {
            $("#pro_title").text('clear');
            $("#s_hold").css('display', 'none');
            $("#pro_img").attr('src', "");
            $("#cb_productList").combobox('clear');
        }

        //“推荐产品”数据表格的列
        var pro_columns = [[
            {field: 'id', checkbox: true},
            {title: '产品名称', field: 'product_name', width: 50, align: 'center'},
            {
                title: '产品图片', field: 'picture_url', width: 100, align: 'center',
                formatter: function (data, row, index) {
                    return "<img src='" + data + "' width='50%'/>";
                }
            }
        ]];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});

            //轮播图片的数据表格
            $("#pic_table").datagrid({
                //定义标题行所有的列
                columns: pic_columns,
                fitColumns: true,
                fit: true,
                rownumbers: "true",
                url: "<s:url action='frontAction_pictureList'/>",
                toolbar: "#pic_tb",
                border: false
            });

            //产品的数据表格
            $("#pro_table").datagrid({
                //定义标题行所有的列
                columns: pro_columns,
                fitColumns: true,
                fit: true,
                rownumbers: "true",
                url: "<s:url action='frontAction_recommendProductList'/>",
                toolbar: "#pro_tb",
                border: false
            });

            //添加“推荐产品”窗口
            $("#add_recPro_window").window({
                onClose: function () {
                    clearAddRecProWindowContent();
                }
            });

            //初始化添加单张图片的kindeditor插件
            KindEditor.ready(function (K) {
                var editor = K.editor({
                    uploadJson: '<s:url action="frontAction_uploadPicture"/>',//图片的上传路径
                    allowFileManager: true
                });
                //添加轮播图片时，图片单张上传插件
                K("#addPicture").click(function () {
                    editor.loadPlugin("image", function () {
                        editor.plugin.imageDialog({
                            showRemote: false,
                            clickFn: function (url, title, width, height, border, align) {
                                editor.hideDialog();
                                //刷新数据表格
                                $("#pic_table").datagrid('load');
                            }
                        });
                    });
                });
            });
        });
    </script>
</head>
<body class="easyui-layout" style="margin: 5px; visibility: hidden">

<%--左边“首页轮播图”--%>
<div id="pictures" title="首页轮播图" data-options="region:'west',collapsible:false, split:true"
     style="width: 50%;">
    <div id="pic_tb">
        <a id="addPicture" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">添加</a>
        <a class="easyui-linkbutton" onclick="deleteImg()" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
    </div>

    <table id="pic_table" style="width: 100%;"></table>
</div>

<%--右边“首页推荐产品”--%>
<div id="products" title="首页推荐产品" data-options="region:'east',collapsible:false, split:true"
     style="width: 50%">
    <table id="pro_table" style="width: 100%; "></table>

    <div id="pro_tb">
        <a class="easyui-linkbutton" onclick="openRecProductWindow()" data-options="iconCls:'icon-add',plain:'true'">添加</a>
        <a class="easyui-linkbutton" onclick="deleteRecProduct()" data-options="iconCls:'icon-remove',plain:'true'">删除</a>
    </div>
</div>

<%--添加“推荐产品”的窗口--%>
<div id="add_recPro_window" class="easyui-window"
     style="width: 400px; height: 400px; text-align: center; padding-top: 10px; align-items:center;"
     data-options="closed:true">
    <input id="cb_productList" class="easyui-combobox" title="所有产品的下拉列表" data-options="valueField:'id',textField:'product_name',
           url:'<s:url action="frontAction_proList"/>',panelHeight:'auto', panelHeight:'100',limitToList:true, onSelect:onRecProSelect"
           style="width: 100px"><br>
    <div id="s_hold" style="width: 230px; height: 180px; display: none;; margin:0 auto">
        <img id="pro_img" src="" style="width: inherit; height: inherit"/>
        <br>
        <a id="pro_title" title="产品名称"></a>
    </div>
    <br>
    <br>
    <br>
    <a onclick="savePro()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="margin-top: 10px">确定</a>
    <script>
        //保存用户选中的“产品”id
        var pro_select_id = null;
        //产品下拉框选项被选中时触发的回调方法
        function onRecProSelect(rec) {
            pro_select_id = rec.id;
            //回显选中的产品
            $("#pro_title").text(rec.product_name);
            $("#s_hold").css('display', '');
            $("#pro_img").attr('src', rec.picture_url);
        }
        //保存选中的“产品”为“推荐产品”
        function savePro() {
            $("cb_productList").combobox('');
            $.post("<s:url action="frontAction_addRecProduct"/>",
                {rec_pro_id: pro_select_id, isRec: "1"},
                function (result) {
                    if (result.status === 200) {
                        $("#add_recPro_window").window('close');
                        //刷新数据表格
                        $("#pro_table").datagrid("reload");
                    } else {
                        alert(result.msg);
                    }
                }
            );
        }
    </script>
</div>

</body>
</html>
