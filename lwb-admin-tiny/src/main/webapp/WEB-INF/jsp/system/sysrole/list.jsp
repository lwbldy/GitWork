<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layuimini-container">
    <div class="layuimini-main">

        <div class="layui-btn-group">
<shiro:hasPermission name="sysrole:save">
            <button class="layui-btn data-add-btn">添加</button>
</shiro:hasPermission>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <script type="text/html" id="currentTableBar">
<shiro:hasPermission name="sysrole:edit">
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
</shiro:hasPermission>
<shiro:hasPermission name="sysrole:delete">
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
</shiro:hasPermission>
        </script>
    </div>
</div>

<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;

        table.render({
            elem: '#currentTableId',
            url: '<%=request.getContextPath()%>/sysrole/list',
            method:'post',
            cols: [[ //表头
                {field: 'roleId', title: '角色ID'}
                ,{field: 'roleName', title: '角色名'}
                ,{field: 'remark', title: '备注'}
                ,{field: 'createTime', title: '添加时间',templet:function(d){
                        return timestampToTime(d.createTime);
                    }}
                ,{title: '操作', minWidth: 50, templet: '#currentTableBar', fixed: "right", align: "center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 15,
            page: true
        });

        // 监听添加操作
        $(".data-add-btn").on("click", function () {
            admin_show('添加角色','<%=request.getContextPath()%>/sysrole/save',500,600)
        });

        // 监听删除操作
        $(".data-delete-btn").on("click", function () {
            var checkStatus = table.checkStatus('currentTableId')
                , data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        });

        //监听表格复选框选择
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });

        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                admin_show('修改角色','<%=request.getContextPath()%>/sysrole/edit/'+data.roleId,500,600);
            } else if (obj.event === 'delete') {
                layer.confirm('真的删除行么', function (index) {
                    // obj.del();
                    // layer.close(index);

                    $.get("<%=request.getContextPath()%>/sysrole/delete/"+data.roleId,
                        function (data) {
                        layer.msg(data.msg);
                        location.reload();
                    },"json");

                    parent.location.reload();

                });
            }
        });

    });
</script>
