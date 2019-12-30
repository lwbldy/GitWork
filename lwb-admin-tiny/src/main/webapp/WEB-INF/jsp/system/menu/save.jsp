
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>保存菜单</title>
    <%@include file="../../sys_common_head.jsp"%>
    <style>
        .layui-iconpicker-body.layui-iconpicker-body-page .hide {display:none;}
    </style>
</head>
<body>

<div style="padding: 20px">

    <div id="menuLayer" style="display:none">
        <ul id="menuTree"></ul>
    </div>

    <form class="layui-form layui-form-pane" action="">

        <div class="layui-form-item" pane="">
            <label class="layui-form-label">菜单类型</label>
            <div class="layui-input-block">
                <input type="radio" name="type" value="0" title="目录">
                <input type="radio" name="type" value="1" title="菜单"  checked="">
                <input type="radio" name="type" value="2" title="按钮">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-inline">
                <input type="text" id="name" name="name" required="" lay-verify="required" class="layui-input" autocomplete="off">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写菜单名称</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">上级菜单</label>
            <div class="layui-input-inline">
                <input type="text" id="parentName" name="parentName" required="" placeholder="请选择上级菜单" lay-verify="required"
                       readonly    autocomplete="off" class="layui-input">
                <input type="hidden" name="parentId" id="parentId">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写菜单名称</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">菜单URL</label>
            <div class="layui-input-inline">
                <input type="text" id="url" name="url" required="" class="layui-input" autocomplete="off">
            </div>
            <div class="layui-form-mid layui-word-aux"></div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">排序号</label>
            <div class="layui-input-inline">
                <input type="number" id="orderNum" name="orderNum" required lay-verify="required" value="0" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">排序越大，越靠前</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">图标</label>
            <div class="layui-input-inline">
                <input class="hide layui-input" autocomplete="off" type="text" id="icon" name="icon" >
            </div>
            <div class="layui-form-mid layui-word-aux">
                fa fa-adjust:具体图标可参考
                <a href="<%=request.getContextPath()%>/#//sys/icon" target="_blank">图标示例</a>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">授权标识</label>
            <div class="layui-input-inline">
                <input type="text" id="perms" name="perms" required="" placeholder=""
                       autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">多个用逗号分隔，如：user:list,user:create</div>
        </div>

        <div class="layui-form-item" pane="">
            <label class="layui-form-label">打开方式</label>
            <div class="layui-input-block">
                <input type="radio" name="tager" value="_self" title="当前窗口" checked="">
                <input type="radio" name="tager" value="_blank" title="新窗口" >
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="save">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>

<script>
    layui.use(['form','layer','tree'], function() {
        var $ = layui.jquery,
        form = layui.form
            , layer = layui.layer
            , tree =  layui.tree;

        var checkedId,checkedName,dateMenuTree;
        $(document).ready(function(){
            $.get("<%=request.getContextPath()%>/sysmenu/findTreeMenu", function (data) {
                var rootMenu = [{title:"顶级菜单",id:0,children:data.data}];
                tree.render({
                    elem: '#menuTree' //传入元素选择器
                    ,click:function(item){
                        checkedName= item.data.title;
                        checkedId = item.data.id;
                        $("#parentName").val(checkedName);
                        $("#parentId").val(checkedId);
                        layer.close(dateMenuTree);
                    }
                    ,data: rootMenu
                });
            },"json");
        });

        //绑定点击事件
        $('#parentName').on('click', function () {
            dateMenuTree = layer.open({
                type: 1,
                offset: '50px',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                end:function(){
                    $("#menuLayer").hide();
                }
            });
        });

        form.on('submit(save)', function(data){
            console.log(data);
            $.ajax({
                type : 'post',
                url : "<%=request.getContextPath()%>/sysmenu/save",
                data:data.field,
                dataType: "json",
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
    });
</script>


</body>
</html>
