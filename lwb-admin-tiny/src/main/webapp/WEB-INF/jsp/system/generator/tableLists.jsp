<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layuimini-container">
    <div class="layuimini-main">

        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <script type="text/html" id="currentTableBar">

            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>

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
            url: '<%=request.getContextPath()%>/sys/generator/tableList',
            method:'post',
            cols: [[ //表头
                {field: 'tableName', title: '表格名称'}
                ,{field: 'engine', title: '数据引擎'}
                ,{field: 'tableComment', title: '备注'}
                ,{fixed: 'right', width: 165, align:'center', toolbar: '#currentTableBar'}
            ]],
            page: false
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

        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                admin_show('生成配置','<%=request.getContextPath()%>/sys/generator/tableInfo/'+data.tableName);
            }
        });

    });
</script>
