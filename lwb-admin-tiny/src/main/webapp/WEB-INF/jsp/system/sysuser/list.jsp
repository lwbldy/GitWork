<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layuimini-container">
    <div class="layuimini-main">

        <fieldset class="layui-elem-field layuimini-search">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <a class="layui-btn" lay-submit="" lay-filter="data-search-btn">搜索</a>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>

        <div class="layui-btn-group">
<shiro:hasPermission name="sysuser:save">
            <button class="layui-btn data-add-btn">添加</button>
</shiro:hasPermission>
        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <script type="text/html" id="currentTableBar">
<shiro:hasPermission name="sysuser:edit">
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
</shiro:hasPermission>

    <shiro:hasPermission name="sysuser:changePWD">
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="changePWD">修改密码</a>
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
            url: '<%=request.getContextPath()%>/sysuser/list',
            method:'post',
            cols: [[ //表头
                {field: 'userId', title: '用户id'}
                ,{field: 'username', title: '用户名'}
                ,{field: 'mobile', title: '移动电话'}
                ,{field: 'email', title: '邮箱'}
                ,{field: 'status', title: '状态',templet:function(d){
                        if(d.status == 0){
                            return "已禁用";
                        }else{
                            return "正常";
                        }
                    }}
                ,{field: 'addtime', title: '添加时间',templet:function(d){
                        return timestampToTime(d.createTime);
                }}
                ,{fixed: 'right', width: 165, align:'center', toolbar: '#currentTableBar'}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 15,
            page: true
        });

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            var result = JSON.stringify(data.field);
            // layer.alert(result, {
            //     title: '最终的搜索信息'
            // });

            //执行搜索重载
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: data.field
            }, 'data');

            return false;
        });

        // 监听添加操作
        $(".data-add-btn").on("click", function () {
            admin_show('添加角色','<%=request.getContextPath()%>/sysuser/save',500,600)
        });

        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                admin_show('修改角色','<%=request.getContextPath()%>/sysuser/edit/'+data.userId,500,600);
            } else if (obj.event === 'delete') {
                layer.confirm('真的删除行么', function (index) {
                    $.get("<%=request.getContextPath()%>/sysrole/delete/"+data.roleId,
                        function (data) {
                            layer.msg(data.msg);
                            location.reload();
                        },"json");
                    parent.location.reload();

                });
            }else if(obj.event === 'changePWD'){
                layer.prompt({title: '请输入新密码', formType: 1}, function(pass, index){
                    $.ajax({
                        url:'<%=request.getContextPath()%>/sysuser/changePWD',
                        type:'post',
                        data:{userId:data.userId,newPWD:pass},
                        dataType:'json',
                        success:function (data) {
                            layer.msg(data.msg);
                            layer.close(index);
                        }
                    })
                });
            }
        });

    });
</script>
