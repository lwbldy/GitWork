<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .layui-btn:not(.layui-btn-lg ):not(.layui-btn-sm):not(.layui-btn-xs) {height:34px;line-height:34px;padding:0 8px;}
</style>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <div>
            <div class="layui-btn-group">
                <shiro:hasPermission name="sysmenu:save">
                    <button class="layui-btn layui-btn-normal" id="btn-add">添加菜单</button>
                </shiro:hasPermission>
                <button class="layui-btn" id="btn-expand">全部展开</button>
                <button class="layui-btn" id="btn-fold">全部折叠</button>
            </div>
            <table id="munu-table" class="layui-table" lay-filter="munu-table"></table>
        </div>
    </div>
</div>
<!-- 操作列 -->
<script type="text/html" id="auth-state">
                    <shiro:hasPermission name="sysmenu:edit">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
                    </shiro:hasPermission>
    <%--<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>--%>
</script>
<script>
    layui.use(['table', 'treetable','layer'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var treetable = layui.treetable;
        var layer = layui.layer;

        // 渲染表格
        layer.load(2);

        treetable.render({
            treeColIndex: 1,
            treeSpid: 0,
            treeIdName: 'menuId',
            treePidName: 'parentId',
            method:'post',
            elem: '#munu-table',
            url: '<%=request.getContextPath()%>/sysmenu/list',
            page: false,
            cols: [[
                {type: 'numbers'},
                {field: 'name', title: '菜单名称'},
                {field: 'icon', width:60,title: '图标',templet: function (d) {
                    return '<i class="fa '+d.icon+'"></i>';
                }},
                {field: 'perms', title: '权限标识'},
                {field: 'url', title: '菜单url'},

                {field: 'orderNum', width: 80, align: 'center', title: '排序号'},
                {
                    field: 'type', width: 80, align: 'center', templet: function (d) {
                        if (d.type == 0) {
                            return '<span class="layui-badge layui-bg-blue">目录</span>';
                        }
                        if (d.type == 1) {
                            return '<span class="layui-badge-rim">菜单</span>';
                        } else {
                            return '<span class="layui-badge layui-bg-gray">按钮</span>';
                        }
                    }, title: '类型'
                },
                {
                    field: 'tager', width: 100, align: 'center', templet: function (d) {
                        if (d.tager == '_self' || d.tager == '' || d.tager == undefined) {
                            return '当前窗口';
                        }else{
                            return '新窗口';
                        }
                    }, title: '打开方式'
                },
                {templet: '#auth-state', width: 120, align: 'center', title: '操作'}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });

        $('#btn-add').click(function () {
            admin_show('添加菜单','<%=request.getContextPath()%>/sysmenu/save',500,650);
        });

        $('#btn-expand').click(function () {
            treetable.expandAll('#munu-table');
        });

        $('#btn-fold').click(function () {
            treetable.foldAll('#munu-table');
        });

        //监听工具条
        table.on('tool(munu-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'del') {
                layer.msg('删除' + data.id);
            } else if (layEvent === 'edit') {
                admin_show('修改菜单','<%=request.getContextPath()%>/sysmenu/edit/'+data.id,500,650);
            }
        });
    });
</script>
</body>
</html>