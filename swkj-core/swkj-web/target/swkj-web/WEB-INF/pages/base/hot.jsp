<%--
  Created by IntelliJ IDEA.
  User: HuangRz
  QQ: 917647409
  Email: huangrz11@163.com
  Date: 2017/10/12
  Time: 17:29
  Description: 
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>热销模块管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
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
    <script type="text/javascript">
        var url;

        function searchHotProducts(){
            $('#dg').datagrid('load',{
                product_name:$('#hot_product_name').val(),
                seriesName:$('#form_series').val()
            });
        }

        function openHotProductsDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加热销产品");
            url="<s:url action="hotProductAction_add"/>";
        }

        function saveHotProducts(){
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
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");

                        //重新加载combobox的数据
                        $('#form_product_id').combobox('reload','<s:url action="hotProductAction_getNotHotProduct"/>');

                    }
                }
            });
        }

        function deleteHotProducts(){
            var selectedRows=$("#dg").datagrid('getSelections');
            if(selectedRows.length==0){
                $.messager.alert("系统提示","请选择要删除的数据！");
                return;
            }
            var strIds=[];
            for(var i=0;i<selectedRows.length;i++){
                strIds.push("\'"+selectedRows[i].id+"\'");
            }
            var ids=strIds.join(",");
            $.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
                if(r){
                    $.post("<s:url action='hotProductAction_delete'/>",{delIds:ids},function(result){
                        if(result.success){
                            $.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
                            $("#dg").datagrid("reload");

                            //重新加载combobox的数据
                            $('#form_product_id').combobox('reload','<s:url action="hotProductAction_getNotHotProduct"/>');


                        }else{
                            $.messager.alert('系统提示',result.errorMsg);
                        }
                    },"json");
                }
            });
        }

        function closeHotProducts(){
            $("#dlg").dialog("close");
        }

        jQuery(document).ready(function(){
            $('#dlg').dialog({
                onClose:function(){
                    resetValue();
                }
            });
        });

        function resetValue(){
            $("#fm").form('clear');
        }

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});
        });

    </script>

</head>
<body style="margin: 5px; visibility: hidden">
<table id="dg" class="easyui-datagrid" fitColumns="true"
       pagination="true" rownumbers="true" url="<s:url action="hotProductAction_hotProductList"/> " fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true"></th>

        <th field="id" width="100" hidden="true">id</th>
        <th field="product_name" width="100">产品名称</th>
        <th field="product_number" width="100">产品编号</th>
        <th field="capacity" width="100">容量</th>
        <th field="product_desc" width="100">产品描述</th>
        <th field="seriesName" width="100" >所属系列</th>

    </tr>
    </thead>
</table>

<div id="tb">
    <div>
        <a href="javascript:openHotProductsDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:deleteHotProducts()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>

    <div>
        &nbsp;产品名称：&nbsp;<input type="text" name="product_name" id="hot_product_name" size="10"/>
        &nbsp;系列：&nbsp;
        <input type="text" name="seriesName" id="form_series" class="easyui-combobox"
               data-options="
                           url:'<s:url action="hotSeriesAction_getHotSeries"/>',
                           <%--因为后台写的逻辑是需要传name过去的，所以把valueField的值改为name，本来应该为id--%>
                           valueField:'name',
                           textField:'name',
                           multiple:true,
                           panelHeight:100
                    "/>

        <a href="javascript:searchHotProducts()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 600px;height: 350px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" enctype="multipart/form-data">
        <table cellspacing="5px;" style="width: 400px;height: 200px; margin: 0 auto">

            <tr>
                <td>选择产品：</td>
                <%----%>
                <td><input type="text" name="id" id="form_product_id" class="easyui-combobox"
                           data-options="
                           url:'<s:url action="hotProductAction_getNotHotProduct"/>',
                           <%--因为后台写的逻辑是需要传name过去的，所以把valueField的值改为name，本来应该为id--%>
                           valueField:'id',
                           textField:'nameAndSeries',
                           multiple:true,
                           panelHeight:100
                    "/>
                </td>
            </tr>

        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveHotProducts()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeHotProducts()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>
