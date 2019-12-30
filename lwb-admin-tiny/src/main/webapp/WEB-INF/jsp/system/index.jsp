
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>后台首页</title>
    <%@include file="../sys_common_head.jsp"%>
    <%--<meta name="renderer" content="webkit">--%>

    <style id="layuimini-bg-color">
    </style>
</head>
<body class="layui-layout-body layuimini-all">
<div class="layui-layout layui-layout-admin">

    <div class="layui-header header">
        <div class="layui-logo">
        </div>
        <a>
            <div class="layuimini-tool"><i title="展开" class="fa fa-outdent" data-side-fold="1"></i></div>
        </a>

        <ul class="layui-nav layui-layout-left layui-header-menu layui-header-pc-menu mobile layui-hide-xs">
        </ul>
        <ul class="layui-nav layui-layout-left layui-header-menu mobile layui-hide-sm">
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="fa fa-list-ul"></i> 选择模块</a>
                <dl class="layui-nav-child layui-header-mini-menu">
                </dl>
            </li>
        </ul>

        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;" data-refresh="刷新"><i class="fa fa-refresh"></i></a>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;" data-clear="清理" class="layuimini-clear"><i class="fa fa-trash-o"></i></a>
            </li>
            <li class="layui-nav-item layuimini-setting">
                <a href="javascript:;">
                    ${adminUser.username}
                </a>
                <dl class="layui-nav-child">
                    <%--<dd>--%>
                        <%--<a href="javascript:;" data-content-href="page/user-setting.html" data-title="基本资料" data-icon="fa fa-gears">基本资料</a>--%>
                    <%--</dd>--%>
                    <dd>
                        <a href="javascript:;"  data-title="修改密码" id="changePWD" data-icon="fa fa-gears">修改密码</a>
                    </dd>
                    <dd>
                        <a href="javascript:;" class="login-out">退出登录</a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item layuimini-select-bgcolor mobile layui-hide-xs">
                <a href="javascript:;" data-bgcolor="配色方案"><i class="fa fa-ellipsis-v"></i></a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll layui-left-menu">
        </div>
    </div>

    <div class="layui-body">
        <div class="layui-card layuimini-page-header layui-hide-xs layui-hide">
            <div class="layui-breadcrumb" id="layuimini-page-header">
                <a><cite>菜单管理</cite></a>
            </div>
        </div>
        <div class="layuimini-content-page">
        </div>
    </div>

</div>

<!--百度统计代码-开始-->

<!--百度统计代码-结束-->


<script>
    layui.use(['element', 'layer', 'layuimini'], function () {
        var $ = layui.jquery,
            element = layui.element,
            layer = layui.layer,
            layuimini = layui.layuimini;

        //初始化页面
        layuimini.init("/api/init.json");


        //初始化左边菜单
        $.getJSON('/sys/menu', function (data, status) {
            layuimini.initMenu(data);
        }).fail(function () {
            layuimini.msg_error('菜单接口有误');
        });

        $("#changePWD").on("click", function () {
            layer.prompt({title: '请输入新密码', formType: 1}, function(pass, index){
                $.ajax({
                    url:'<%=request.getContextPath()%>/sysuser/changeMyPWD',
                    type:'post',
                    data:{newPWD:pass},
                    dataType:'json',
                    success:function (data) {
                        layer.msg(data.msg);
                        layer.close(index);
                    }
                })
            });
        });

        $('.login-out').on("click", function () {
            layer.msg('退出登录成功', function () {
                window.location = '/sys/logout';
            });
        });

        // 监听浏览器前进后退
        // if (window.history && window.history.pushState) {
        //     $(window).on('popstate', function () {
        //         layuimini.refresh();
        //     });
        // }

        layuimini.listen();

    });
</script>
</body>
</html>
