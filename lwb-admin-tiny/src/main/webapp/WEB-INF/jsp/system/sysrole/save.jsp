
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@include file="../../sys_common_head.jsp"%>

    <!-- 引入树 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/lib/zTree_v3/css/metroStyle/metroStyle.css">
    <script src="<%=request.getContextPath()%>/lib/zTree_v3/js/jquery.ztree.all.min.js"></script>

</head>
<body>
<div style="padding: 20px">
    <form class="layui-form layui-form-pane" action="" id="ajaxForm">

        <div class="layui-form-item">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-inline">
                <input type="text" id="roleName" name="roleName" required="" lay-verify="required" class="layui-input" autocomplete="off">
            </div>
            <div class="layui-form-mid layui-word-aux">请务必填写角色名称</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <input type="text" id="remark" name="remark" required=""  class="layui-input" autocomplete="off">
            </div>
        </div>

        <div class="layui-form-item">
            <label for="remark" class="layui-form-label">
                功能权限
            </label>
            <div class="layui-input-inline">
                <ul id="menuTree" class="ztree"></ul>
            </div>
        </div>

        <input type="hidden" id="roleMenuIds" name="roleMenuIds">

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="save">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>

    <script>

        var menu_ztree;
        // zTree设置
        var menu_setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "menuId",
                    pIdKey: "parentId",
                    rootPId: -1
                },
                key: {
                    url:"nourl"
                }
            },
            check:{
                enable:true,
                nocheckInherit:true,
                chkboxType:{ "Y" : "ps", "N" : "s" }
            }
        };

        $(function(){
            $.get("<%=request.getContextPath()%>/sysmenu/fundAll", function(r){
                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
                //展开所有节点
                menu_ztree.expandAll(true);
            },'json');
        });

        //获取所有选中菜单
        function selectMenuId(){
            //获取选择的菜单
            var nodes = menu_ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            var roleMenuIds = '';
            for(var i=0; i<nodes.length; i++) {
                menuIdList.push(nodes[i].menuId);
                roleMenuIds +=nodes[i].menuId+','
            }
            console.log(JSON.stringify(menuIdList));
            console.log(JSON.stringify(roleMenuIds));
            $("#roleMenuIds").val(roleMenuIds);
        }
        layui.use(['form','layer','tree'], function(){
            $ = layui.jquery;
            var form = layui.form
                ,layer = layui.layer;

            //监听提交
            form.on('submit(save)', function(data){

                selectMenuId();

                //提交数据
                $("#ajaxForm").ajaxSubmit({
                    type : 'post',
                    url : "<%=request.getContextPath()%>/sysrole/save",
                    dataType:'json',
                    //data:  //注意只要是写在表单里面的，都不需要加这个属性。在controller中可以根据@RequestParam String str获取到属性值。
                    //contentType : "application/x-www-form-urlencoded; charset=utf-8",
                    success: function(data) {
                        if(data.code == 0){
                            layer.alert("新增成功", {icon: 6},function () {
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

</div>
</body>
</html>
