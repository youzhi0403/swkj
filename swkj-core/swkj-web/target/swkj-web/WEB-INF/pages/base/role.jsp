<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/9
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
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
    </style>
    <script type="text/javascript">
        var url;
        var imgUrl;

        function deleteRole(){
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
                    $.post("<s:url action='roleAction_delete'/>",{delIds:ids},function(result){
                        if(result.success){
                            $.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
                            $("#dg").datagrid("reload");
                        }else{
                            $.messager.alert('系统提示',result.errorMsg);
                        }
                    },"json");
                }
            });
        }

        function searchRole(){
            $('#dg').datagrid('load',{
                name:$('#r_name').val()
            });
        }

        function openRoleAddDialog(){
            $("#dlg").dialog("open").dialog("setTitle","添加角色信息");
            url="<s:url action="roleAction_save"/>";
        }

        function saveRole(){
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
                        resetValue();
                        $("#dlg").dialog("close");
                        $("#dg").datagrid("reload");
                    }
                }
            });
        }


        function resetValue(){
            /*$("#form_id").val("");
            $("#form_username").val("");
            $("#form_password").val("");
            $("#form_telephone").val("");
            $("#form_roles").val("");*/
            $("#fm").form('clear');
        }

        jQuery(document).ready(function(){
            $('#dlg').dialog({
                onClose:function(){
                    resetValue();
                }
            });
        });

        function closeRoleDialog(){
            $("#dlg").dialog("close");
        }

        function openRoleModifyDialog(){
            var selectedRows=$("#dg").datagrid('getSelections');
            if(selectedRows.length!=1){
                $.messager.alert("系统提示","请选择一条要编辑的数据！");
                return;
            }
            var row=selectedRows[0];
            $("#dlg").dialog("open").dialog("setTitle","编辑角色信息");
            $("#fm").form("load",row);

            var strs = row.authIds.split(",");
            for(var i=0;i<strs.length;i++){
                $('#form_auths').combobox('select',strs[i]);
            }
            url="<s:url action="roleAction_save"/>";

        }

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility:"visible"});
        });

    </script>
</head>

<body style="margin: 5px; visibility: hidden">

<table id="dg" class="easyui-datagrid" fitColumns="true"
       pagination="true" rownumbers="true" url="<s:url action="roleAction_list"/> " fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true"></th>

        <th field="name" width="100">角色</th>
        <th field="code" width="100" hidden="true">代码</th>
        <th field="description" width="100">角色描述</th>

        <th field="authIds" width="100" hidden="true">authIds</th>
        <th field="id" width="100" hidden="true">id</th>

    </tr>
    </thead>
</table>

<div id="tb">
    <div>
        <a href="javascript:openRoleAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="javascript:openRoleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        <a href="javascript:deleteRole()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    </div>

    <div>
        &nbsp;用户：&nbsp;<input type="text" name="r_name" id="r_name" size="10"/>
        <a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 600px;height: 350px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post" enctype="multipart/form-data">
        <table cellspacing="5px;" style="width: 400px;height: 200px;margin: 0 auto">

            <tr style="display: none">
                <td><input type="text" name="id" id="form_id" class="easyui-validatebox" /></td>
            </tr>

            <tr>
                <td>角色：</td>
                <td><input type="text" name="name" id="form_name" class="easyui-validatebox" /></td>
            </tr>

            <tr>
                <td>角色描述：</td>
                <td><input type="text" name="description" id="form_description" class="easyui-validatebox" /></td>
            </tr>

            <tr>
                <td>角色权限：</td>
                <td><input type="text" name="authIdsOrNames" id="form_auths" class="easyui-combobox"
                           data-options="
                           url:'<s:url action="functionAction_getFunciton"/>',
                           valueField:'id',
                           textField:'name',
                           multiple:true,
                           panelHeight:100
                    "/>
                </td>
            </tr>

        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:saveRole()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>

</html>
