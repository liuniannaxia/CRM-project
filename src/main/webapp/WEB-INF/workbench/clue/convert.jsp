﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<%--<form class="form-inline" role="form">--%>
						  <div class="form-group has-feedback">
						    <input type="text"  class="form-control" id="queryRelationActivity"style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						<%--</form>--%>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="tbodyDiv">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>

						</tbody>
					</table>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary"onclick="relateClueActivity()" data-dismiss="modal">关联</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>李四先生-动力节点</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：动力节点
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：李四先生
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" value="0" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" >
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control" id="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
				<c:forEach items="${map['stage']}" var="stage">
					<option value="${stage.value}">${stage.text}</option>
				</c:forEach>
		    	<%--<option></option>
		    	<option>资质审查</option>
		    	<option>需求分析</option>
		    	<option>价值建议</option>
		    	<option>确定决策者</option>
		    	<option>提案/报价</option>
		    	<option>谈判/复审</option>
		    	<option>成交</option>
		    	<option>丢失的线索</option>
		    	<option>因竞争丢失关闭</option>--%>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
			  <input type="hidden" id="activityId"/>
		    <label for="activity">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#searchActivityModal" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>zhangsan</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" onclick="convert()" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>


<script>
	//发送异步请求，查询线索信息，拼接页面
	$.post("/crm/workbench/clue/queryById",{'id':'${id}'},function (data) {
		//传回来的  data 是一个线索对象
		//层级选择器，找title下面的h4
		$('#title h4:first').html(data.fullname+data.appellation+"-"+data.company);
		$('#create-customer').html("新建客户："+data.company);
		$('#create-contact').html(data.fullname+data.appellation);
		$('#owner').html(data.createBy);
	},'json')


	//给转换按钮，绑定一系列转换前的事件
	function convert() {
		$.post("/crm/workbench/clue/convert",{'clueId':'${id}',
										'isCreateTransaction':$('#isCreateTransaction').val(),
											'money':$('#amountOfMoney').val(),
											'name':$('#tradeName').val(),
											'expectedDate':$('#expectedClosingDate').val(),
											'activity':$('#activityId').val(),

				},
		function (data) {

		},'json')
	}

	//判断交易的对钩是否勾中，如果勾中就发生交易
	$('#isCreateTransaction').click(function () {
		//checked	是固有属性，所以用prop
		var isChecked = $('#isCreateTransaction').prop('checked')
		if (isChecked){
			$('#isCreateTransaction').val("1")
		}else {
			$('#isCreateTransaction').val("0")
		}

	})

	//点击放大镜，查询已经关联的市场活动
	$('#queryRelationActivity').keypress(function (event) {
		if(event.keyCode==13){
			//发送异步请求，查询关联的市场活动
			$.post("/crm/workbench/clue/queryRelationActivity",{'id':'${id}','name':$(this).val()},function (data) {
				$('#tbodyDiv').html("");
				for (var i = 0 ; i<data.length;i++) {
					var activity = data[i];

					$('#tbodyDiv').append("<tr>\n" +
							"\t\t\t\t\t\t\t\t<td><input class='son' value='"+activity.id+"' aName='"+activity.name+"'type=\"radio\" name=\"activity\"/></td>\n" +
							"\t\t\t\t\t\t\t\t<td>"+activity.name+"</td>\n" +
							"\t\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
							"\t\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
							"\t\t\t\t\t\t\t\t<td>"+activity.createBy+"</td>\n" +
							"\t\t\t\t\t\t\t</tr>")
			}
			},'json')

			//输入完之后，把刷新页面的方法禁用掉（模态窗口自带的）
		}
	})

	//给关联按钮绑定函数
	function relateClueActivity() {
		//获取勾中数据
		var checked = $('.son:checked:first');
		//将选中的市场活动id号放到隐藏域中，为插入交易表数据做准备
		$('#activityId').val(checked.val());
		$('#activity').val(checked.attr('aName'));

	}


</script>
</body>
</html>