<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<%--zTree导入--%>

<link rel="stylesheet" href="/crm/jquery/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/crm/jquery/zTree_v3-master/js/jquery.ztree.all.min.js"></script>

</head>
<body>
<ul id="treeDemo" class="ztree"></ul>
<script>


    var treeObj;

    //异步查询用户的权限
    $.post("/crm/settings/role/queryPermission",{'id':'${id}'},function (data) {

        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var zNodes  =data;
        console.log(zNodes)
        treeObj=$.fn.zTree.init($("#treeDemo"), setting, zNodes);
    },'json')

    function authrize() {
        var nodes = treeObj.getCheckedNodes(true);
        var ids = [];
        for (var i = 0;i<nodes.length;i++) {
            ids.push(nodes[i].id)
        }
        $.post("/crm/settings/role/authrize",{'ids':ids.join(),'id':'${id}'},function(data){
            if(data.isOk){
                alert(data.message);
            }

        },'json')

    }




    //定义一个有下面属性的类，到时候返回这个类 的json串

	/*var zNodes =[
		{ id:"1", pId:"0", name:"随意勾选 1", open:true},
		{ id:"11", pId:"1", name:"随意勾选 1-1", open:true},
		{ id:"111", pId:"11", name:"随意勾选 1-1-1"},
		{ id:"112", pId:"11", name:"随意勾选 1-1-2"},
		{ id:"12", pId:"1", name:"随意勾选 1-2", open:true},
		{ id:"121", pId:"12", name:"随意勾选 1-2-1"},
		{ id:"122", pId:"12", name:"随意勾选 1-2-2"},
		{ id:"2", pId:"0", name:"随意勾选 2", checked:true, open:true},
		{ id:"21", pId:"2", name:"随意勾选 2-1"},
		{ id:"22", pId:"2", name:"随意勾选 2-2", open:true},
		{ id:"221", pId:"22", name:"随意勾选 2-2-1", checked:true},
		{ id:"222", pId:"22", name:"随意勾选 2-2-2"},
		{ id:"23", pId:"2", name:"随意勾选 2-3"}
	];*/
    /*var zNodes =[
{checked: false, id: "9bc017f8f4064444977933167db1a3a1", name: "市场活动", open: true, pId: "0"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a2", name: "线索（潜在客户）", open: true, pId: "0"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a3", name: "交易（商机）", open: true, pId: "0"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a4", name: "统计图表", open: true, pId: "0"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a5", name: "动态", open: true, pId: "0"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a6", name: "市场活动统计图表", open: false, pId: "9bc017f8f4064444977933167db1a3a4"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a7", name: "线索统计图表", open: false, pId: "9bc017f8f4064444977933167db1a3a4"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a8", name: "交易统计图表", open: false, pId: "9bc017f8f4064444977933167db1a3a4"}
{checked: false, id: "9bc017f8f4064444977933167db1a3a9", name: "客户", open: true, pId: "0"}
{checked: false, id: "9bc017f8f4064444977933167db1a3b9", name: "市场活动查询", open: false, pId: "9bc017f8f4064444977933167db1a3a1"}
 {checked: false, id: "9bc017f8f4064444977933167db1a3c9", name: "市场活动添加", open: false, pId: "9bc017f8f4064444977933167db1a3a1"}
 {checked: false, id: "9bc017f8f4064444977933167db1a3d9", name: "市场活动删除", open: false, pId: "9bc017f8f4064444977933167db1a3a1"}
 {checked: false, id: "9bc017f8f4064444977933167db1a3e9", name: "市场活动修改", open: false, pId: "9bc017f8f4064444977933167db1a3a1"}
    ];*/


</script>
<button class="btn btn-success btn-lg" onclick="authrize()">授权</button>

</body>
</html>