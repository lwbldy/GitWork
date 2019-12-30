<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@include file="../../sys_common_head.jsp"%>
</head>
<body>
<div style="padding: 20px">
    <form class="layui-form layui-form-pane" action="" id="ajaxForm">

        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-inline">
                <input type="text" id="username" name="username" required="" lay-verify="required" class="layui-input" autocomplete="off">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写角色名称</div>
        </div>

        <%--<div class="layui-form-item">--%>
            <%--<label class="layui-form-label">登录密码</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<input type="password" id="password" name="password" required="" lay-verify="required" class="layui-input" autocomplete="off" value="123456">--%>
            <%--</div>--%>
            <%--<div class="layui-form-mid layui-word-aux">默认密码是：123456</div>--%>
        <%--</div>--%>

        <div class="layui-form-item">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-block">
                <input type="text" id="mobile" name="mobile" class="layui-input" autocomplete="off">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-block">
                <input type="text" id="email" name="email" class="layui-input" autocomplete="off">
            </div>
        </div>

        <div class="layui-form-item" pane="">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-block">
                <c:forEach items="${roleList}" var="role">
                    <input type="checkbox" name="roleIds" value="${role.roleId}" title="${role.roleName}">
              </c:forEach>
            </div>
        </div>

        <div class="layui-form-item" pane="">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-inline">
                <input type="radio" name="status" lay-skin="primary" title="启用" checked="" value="1">
                <input type="radio" name="status" lay-skin="primary" title="禁用" value="0">
            </div>
        </div>

        <input type="hidden" name="userId" id="userId">

        <div class="layui-form-item">
            <div class="layui-input-inline">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="save">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>

    </form>
</div>

<script>
    layui.use(['form','layer','jquery'], function(){
        var  $ = layui.jquery,
        form = layui.form
            ,layer = layui.layer;
        //监听提交
        form.on('submit(save)', function(data){
            //提交数据
            $("#ajaxForm").ajaxSubmit({
                type : 'post',
                url : "<%=request.getContextPath()%>/sysuser/edit",
                dataType:'json',
                //data:  //注意只要是写在表单里面的，都不需要加这个属性。在controller中可以根据@RequestParam String str获取到属性值。
                //contentType : "application/x-www-form-urlencoded; charset=utf-8",
                success: function(data) {
                    if(data.code == 0){
                        layer.alert(data.msg, {icon: 6},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            // 可以对父窗口进行刷新
                            parent.location.reload();
                        });
                    }else{
                        layer.alert(data.msg, {icon: 5},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            // 可以对父窗口进行刷新
                            parent.location.reload();
                        });
                    }
                },
                error: function (data)//服务器响应失败处理函数
                {
                    layer.msg('返回数据不对');
                }
            });
            return false;
        });
        //初始化加载
        $(document).ready(function(){
            var index = layer.load(2);
            $.get("<%=request.getContextPath()%>/sysuser/info/${id}", function(data){

                $("#ajaxForm").autofill(data.data);

                obj = document.getElementsByName("roleIds");
                for(k in obj){
                    var roleIdList = data.roleIds;
                    for(var i =0;i<roleIdList.length;i++){
                        if(obj[k].value == roleIdList[i]){
                            console.log(roleIdList[i]);
                            obj[k].checked = true;
                            form.render();
                        }
                    }
                }
                layer.close(index);
                // form.render();
            },"json");
        });
    });
</script>

</body>
</html>