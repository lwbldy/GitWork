<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>保存菜单</title>
    <%@include file="../../sys_common_head.jsp"%>
    <script src="<%=request.getContextPath()%>/lib/vue.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <style>
        .mycard{
            border: 1px solid #f2f2f2;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div style="padding: 20px" id="app">
    <div class="layui-container">
        <div class="layui-row">

            <%--<form class="layui-form layui-form-pane" action="">--%>

                <!-- 总体信息配置 -->
                <div class="layui-col-md12">
                    <div class="layui-card mycard">
                        <div class="layui-card-header"><i class="fa fa-line-chart icon"></i>生成配置</div>
                        <div class="layui-card-body">
                            <div class="layui-form-item">
                                <label class="layui-form-label">表名</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="tableName" name="tableName" required="" readonly  disabled="disabled" class="layui-input" v-model="tableName"  autocomplete="off">
                                </div>
                                <div class="layui-form-mid layui-word-aux">表名</div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">表说明</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="comments" name="comments" required="" class="layui-input" v-model="comments" autocomplete="off">
                                </div>
                                <div class="layui-form-mid layui-word-aux">表说明和菜单上的名称</div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">作者名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="author" name="author" required="" class="layui-input" v-model="author"  autocomplete="off">
                                </div>
                                <div class="layui-form-mid layui-word-aux">类上面的作者名称</div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">模块名称</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="modelName" name="modelName" required="" class="layui-input" v-model="modelName" autocomplete="off">
                                </div>
                                <div class="layui-form-mid layui-word-aux">模块的名称，请选择项目中已存在的模块</div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">包名</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="packageName" name="packageName" required="" class="layui-input" v-model="packageName" autocomplete="off">
                                </div>
                                <div class="layui-form-mid layui-word-aux">项目包的名称，生成的代码放到哪个包里面</div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">邮箱</label>
                                <div class="layui-input-inline">
                                    <input type="text" id="email" name="email" required="" class="layui-input" v-model="email" autocomplete="off">
                                </div>
                                <div class="layui-form-mid layui-word-aux">类上面的作者联系邮箱</div>
                            </div>

                            <%--<div class="layui-form-item">--%>
                                <%--<label class="layui-form-label">去除的前缀</label>--%>
                                <%--<div class="layui-input-inline">--%>
                                    <%--<input type="text" id="tablePrefix" name="tablePrefix" required="" class="layui-input" v-model="tablePrefix" autocomplete="off">--%>
                                <%--</div>--%>
                                <%--<div class="layui-form-mid layui-word-aux">默认不去除表前缀，可自定义</div>--%>
                            <%--</div>--%>




                        </div>
                    </div>
                </div>

                <div class="layui-col-md12">
                    <br>
                    <br>
                    <div class="layui-card mycard">
                        <div class="layui-card-header">字段配置</div>
                        <div class="layui-card-body">

                            <table class="layui-table">

                                <colgroup>
                                    <%--<col width="150">--%>
                                    <%--<col>--%>
                                </colgroup>
                                <thead>

                                <tr>
                                    <th>字段名称</th>
                                    <th>字段类型</th>
                                    <th>字段描述</th>
                                    <th>列表</th>
                                    <th>表单</th>
                                    <th>必填</th>
                                    <th>是否查询</th>
                                    <th>查询方式</th>
                                </tr>
                                </thead>
                                <tbody>

                                <tr v-for="(tableCol,index) in columns">
                                    <td>{{tableCol.columnName}}</td>
                                    <td>{{tableCol.dataType}}</td>
                                    <td>
                                        <input type="text" class="layui-input" v-model="tableCol.comments">
                                    </td>
                                    <td>
                                        <input type="checkbox" lay-skin="switch" value="yes" name="isMenuShow" v-model="tableCol.isMenuShow" checked></td>
                                    <td><input type="checkbox" lay-skin="switch" value="yes" name="isFormShow" v-model="tableCol.isFormShow" checked></td>
                                    <td>
                                        <input type="checkbox" lay-skin="switch" name="isNullable" v-model="tableCol.isNullable" true-value="NO" false-value="YES">
                                    </td>
                                    <td>
                                        <input type="checkbox" lay-skin="switch" name="isQuery" v-model="tableCol.isQuery" true-value="YES" false-value="NO">
                                    </td>
                                    <td>
                                        <select name="columnQuery" v-model="tableCol.columnQuery">
                                            <option>=</option>
                                            <option>!=</option>
                                            <option>>=</option>
                                            <option><=</option>
                                            <option>like</option>
                                        </select>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div >
                    <div class="layui-input-block">
                        <button type="submit" v-on:click="sendTable" class="layui-btn" style="margin-top: 10px;float: right">立即提交</button>
                    </div>
                </div>
            <%--</form>--%>

        </div>
    </div>
</div>

<script>

    var app = new Vue({
        el: '#app',
        data: ${tableJSON},
        methods: {
            sendTable:function(event){
                console.log(this._data)

                axios({
                    method:'post',
                    url:'<%=request.getContextPath()%>/sys/generator/tableInfo',
                    data:this._data
                }).then(function(res){
                    console.log(res);
                    alert(res.data.msg)

                    // blob = new Blob([res.data]);
                    // var a = document.createElement('a');
                    // a.download = 'code.zip';
                    // a.href=window.URL.createObjectURL(blob);
                    // a.click();
                });

                <%--$.ajax({--%>
                    <%--type : 'post',--%>
                    <%--url : "<%=request.getContextPath()%>/sys/generator/tableInfo",--%>
                    <%--dataType:'json',--%>
                    <%--// data:  table,--%>
                    <%--// contentType : "application/x-www-form-urlencoded;charset=utf-8",--%>
                    <%--success: function(data) {--%>
                        <%--console.log(JSON.stringify(data))--%>
                    <%--},--%>
                    <%--error: function (data){--%>
                    <%--}--%>
                <%--});--%>


            }
        }
    })

</script>

</body>
</html>
