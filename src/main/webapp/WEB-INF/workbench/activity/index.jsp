<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />


<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <%--支持中文的意思（local）--%>
<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" charset="UTF-8" src="/crm/jquery/bs_pagination/en.js"></script>
<script type="text/javascript" charset="UTF-8" src="/crm/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>

<%--<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/npm.js"></script>--%>

<script type="text/javascript">

	$(function(){
		
		
		
	});
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="createActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" name="owner" id="create-marketActivityOwner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}" >${user.name}</option>
									</c:forEach>
								 <%-- <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="name" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control"name="startDate" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control"name="endDate" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control"name="cost" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" name="description" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					<%--											data-dismiss="modal"自动关闭模态窗口
																	取消掉，手动关闭--%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" >关闭</button>
					<button type="button" class="btn btn-primary" id="creatActivityBtn" >保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="editActivityForm" role="form">
					<input type="hidden"id="edit-ActivityId" name="id"/>
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" name="owner" id="edit-marketActivityOwner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								 <%-- <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control"name="name" id="edit-marketActivityName" />
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="startDate" id="edit-startTime" />
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="endDate" id="edit-endTime" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control"name="cost" id="edit-cost" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3"name="description" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="owner" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTime">
				    </div>
				  </div>
				  
				  <button type="button" onclick="queryBySelect()" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal"  data-target="#createActivityModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" onclick="updateView()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"onclick="deleteActivity($(this))"><span class="glyphicon glyphicon-minus" ></span>删除</button>
				  <button type="button" class="btn btn-warning"onclick="deleteActivity($(this))"><span class="glyphicon glyphicon-minus"></span>批量删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="father" type="checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">--%>
							<%--<td><input type="checkbox" /></td>--%>
							<%--<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                            <%--<td>zhangsan</td>--%>
							<%--<td>2020-10-10</td>--%>
							<%--<td>2020-10-20</td>--%>
						<%--</tr>--%>
                        <%--<tr class="active">--%>
                            <%--<td><input type="checkbox" /></td>--%>
                            <%--<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                            <%--<td>zhangsan</td>--%>
                            <%--<td>2020-10-10</td>--%>
                            <%--<td>2020-10-20</td>--%>
                        <%--</tr>--%>
					</tbody>
				</table>
			</div>
			
			<%--<div id="activityPage" style="height: 50px; position: relative;top: 30px;">--%>

			<%--显示分页下面的样式--%>
				<div style="margin:30px 0px" id="activityPage">
					<div style="height: 50px;position: relative; top: 30px;"></div>
				</div>
				<%--<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>--%>

				<%--<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>--%>
			<%--</div>--%>
			
		</div>
		
	</div>

<script>

    refresh(1,2);
	//定义一个function函数，到时候，用户点击完任意一个按钮，都要调用这个函数刷新一下页面
	function refresh(page,pageSize){        				//'page'是属性名
		$.get("/crm/workbench/Activity/list",
				{   'page':page,
					'pageSize':pageSize,
					'name':$("#name").val(),
					'startDate':$("#startTime").val(),
					'endDate':$("#endTime").val(),
				}
					,function (data) {
			//用户查新完数据之后将上一次的查询结果去掉
			$('#activityBody').html("");
			//后台控制层返回的是一个pageInfo对象，也就是data是pageInfo，这个对象里面有list集合属性，吧这个集合数据给activity
			var activitys = data.list;
			//遍历这个list集合
			for(var i=0;i<activitys.length;i++){
				var activity = activitys[i];
				$('#activityBody').append("<tr class=\"active\">\n" +
						"\t\t\t\t\t\t\t<td><input class='son' value="+activity.id+" type=\"checkbox\" /></td>\n" +
						"\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='/crm/workbench/activity/toDetail02?id="+activity.id+"';\">"+activity.name+"</a></td>\n" +
						"                            <td>"+activity.owner+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
						"\t\t\t\t\t\t</tr>")
			}
					selectedLength = $('.son:checked').length;
			//加载分页插件
            $("#activityPage").bs_pagination({
                currentPage: data.pageNum, // 页码
                rowsPerPage: data.pageSize, // 每页显示的记录条数
                maxRowsPerPage: 20, // 每页最多显示的记录条数
                totalPages: data.pages, // 总页数
                totalRows: page.total, // 总记录条数
                visiblePageLinks: 3, // 显示几个卡片
                showGoToPage: true,
                showRowsPerPage: true,
                showRowsInfo: true,
                showRowsDefaultInfo: true,
                //点击界面上的任意一个按钮，就会触发函数
                onChangePage : function(event, obj){

                    refresh(obj.currentPage,obj.rowsPerPage);
                }
            });
		},'json');
	}


	//异步查询所有的市场活动
//默认查询第一个数据

/*	$.get("/crm/workbench/Activity/list",{},function (data) {
		for(var i=0;i<data.length;i++){
			var activity = data[i];
			$('#activityBody').append("<tr class=\"active\">\n" +
					"\t\t\t\t\t\t\t<td><input type=\"checkbox\" /></td>\n" +
					"\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.jsp';\">"+activity.name+"</a></td>\n" +
					"                            <td>"+activity.owner+"</td>\n" +
					"\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
					"\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
					"\t\t\t\t\t\t</tr>")
		}
		
	},'json');*/

	//前端UI插件（因为好看采用才用）
	// BootStrap分页插件

    //前端插件，日期选择器
    //.time  是类（class）选择器
    $("#startTime").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-dd",//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });

	$("#endTime").datetimepicker({
		language:  "zh-CN",
		format: "yyyy-mm-dd",//显示格式
		minView: "month",//设置只显示到月份
		initialDate: new Date(),//初始化当前日期
		autoclose: true,//选中自动关闭
		todayBtn: true, //显示今日按钮
		clearBtn : true,
		pickerPosition: "bottom-left"
	});

	//定义条件查询方法
function queryBySelect() {
	refresh(1,2)
}

//点击添加按钮，发送异步请求，查询所有的name显示到下拉列表框中(下拉列表框是option)
	/*function queryUsers(){
		$.post("/crm/workbench/activity/queryAllUsers",{},function (data) {
					//遍历所有用户
					for(var i = 0;i< data.length;i++){
						$('#create-marketActivityOwner').append("<option value="+data[i].id+">"+data[i].name+"</option>")
					}

				},
				'json');
	}*/


//点击保存按钮，保存创建市场活动信息，bind（）：第一个参数，事件名称，参数2，触发的函数
	$('#creatActivityBtn').bind('click',function () {


		//表单序列化，一下可以获取到表单里的所有用户提交的数据，不用一个一个的去绑定单击按钮提交了
		//form里面是以k，v的形式存储数据的，所以要给序列化的每个提交按钮 name（k）值，用户输入的值就是v
		var form = $('#createActivityForm').serialize()

		$.post("/crm/workbench/activity/creatOrUpdate",form,function (data) {

					//用户添加完数据之后将上一次的查询结果去掉
					//$('.form-control').val("");

			//返回的data是一个resultVo对象，告诉一下用户成功或者失败就行了
			if (data.isOk) {
				alert(data.message);
				//成功的话，要刷数据
				//手动关闭创建市场活动模态窗口
				$('#createActivityModal').modal('hide');
				refresh(1,2);
				//重置form表单
				//js-->jQuery  a-->$(a)
				//jQuery-->js  b-->b[0] 或者 b.get[0]
				$('#createActivityForm').reset();
			}
		},
		'json')

    })


//点击更新按钮，更新市场活动
	$('#updateActivityBtn').bind('click',function () {


		//表单序列化，一下可以获取到表单里的所有用户提交的数据，不用一个一个的去绑定单击按钮提交了
		//form里面是以k，v的形式存储数据的，所以要给序列化的每个提交按钮 name（k）值，用户输入的值就是v
		var form = $('#editActivityForm').serialize()

		$.post("/crm/workbench/activity/creatOrUpdate",form,function (data) {

					//用户添加完数据之后将上一次的查询结果去掉
					//$('.form-control').val("");

					//返回的data是一个resultVo对象，告诉一下用户成功或者失败就行了
					if (data.isOk) {
						alert(data.message);
						//成功的话，要刷数据
						//手动关闭创建市场活动模态窗口
						$('#editActivityModal').modal('hide');
						refresh(1,2);
					}
				},
				'json'
		)
	})


	//点击修改按钮，判断用户是否是否选中信息
	//全选或者反选


	//全选
	$('#father').click(function () {

		//如果想在里面使用上面的$(this)
		//定义一个变量，var abc = $(this);
		//方式一
		/*if($(this).prop('checked')){
			//father选中了
			//each() 遍历函数
			$('.son').each(function () {
				//this  就近原则，指代son
				//prop  是固有属性
				$(this).prop('checked',true)
			})

		}else {
			//each() 遍历函数
			$('.son').each(function () {
				//this  就近原则，指代son
				//prop  是固有属性
				$(this).prop('checked',false)
			})
		}*/

		//方式二 , 直接把father是否勾中的属性赋给子元素
		var father = $(this);
		$('.son').each(function () {
			//this  就近原则，指代son
			//prop  是固有属性
			$(this).prop('checked',father.prop('checked'))
		})


	})

	/*$('.son').each(function () {
		$(this).click(function () {
			alert(123);
		})
	})*/

	/*
	* 判断son选中的个数和所有的son个数比较，只要不相等，father就不勾中
	* */
//on()  参数1，事件名称，2，本来要绑定的元素名称（绑定son不行，所以绑定到son的上一级不是动态生成的元素上），3，触发的函数

	//定义一个全局变量
	var selectedLength;
	$('#activityBody').on('click','.son',function () {
		//获取所有son的长度
		var length = $('.son').length;

		//获取选中son的长度
		var checkLength = $('.son:checked').length;
		selectedLength = checkLength;
		if (length!= checkLength) {
			$('#father').prop('checked',false);
		}else {
			$('#father').prop('checked',true);
		}

	})


	//这里有bug
	function updateView() {
		//没有勾中不行
		if (selectedLength == 0){
			alert("至少选中一条记录");

		}else if (selectedLength>1){
			alert("只能操作一条记录")

		}else {
			//可以更新了，手动弹出模态窗口
			$('#editActivityModal').modal('show');
			var id = $('.son:checked').val();

			//查询所有用户 信息
			$.post("/crm/workbench/activity/queryAllUsers",{},function (data) {
						//遍历所有用户
						for(var i = 0;i< data.length;i++){
							$('#create-marketActivityOwner').append("<option value="+data[i].id+">"+data[i].name+"</option>")
						}

					},
					'json');

			//异步根据id查询所有的信息(用到了隐藏域的id)
			$.post("/crm/workbench/editActivity/queryById",{'id':id},function (data) {

				/*var owner = data.owner;
				$('#edit-marketActivityOwner option').each(function () {
					if ($(this).val() == owner) {
						//选中
						$(this).prop('selected', true);
					}
				})*/

				$('#edit-marketActivityName').val(data.name)
				$('#edit-startTime').val(data.startDate)
				$('#edit-endTime').val(data.endDate)
				$('#edit-cost').val(data.cost)
				$('#edit-describe').val(data.description)

				//吧市场活动的id号设置到隐藏域之中，如果不设置，就会更新所有的数据（连主键id都更新）
				$('#edit-ActivityId').val(data.id);
			},'json')
		}
	}

	//删除操作
	function deleteActivity($this) {
		//alert($this.text());
		//获取点击按钮上面的文本，这样就能区分是单删还是批量删除
		//正则表达式去除文本空格
		var text = $this.text().replace(/\s*$/g,"");
		if (text=="删除") {
			if ( $('.son:checked').length==0){
				alert("至少选中一条记录")
			}else if ($('.son:checked').length>1) {
				alert("只能操作一条记录")
			}else {
				var result = confirm("确定删除选定的数据吗啊？");
				if (result==true) {
					//开始删除
					deletes();
				}
			}

		}else if(text=="批量删除"){
			if ( $('.son:checked').length==0){
				alert("至少选中一条记录")
			}else {
				var result = confirm("确定删除选定的"+$('.son:checked').length+"数据吗啊？");
				if (result==true) {
					//开始删除
					deletes();
			}

			}
		}

	}
//定义删除函数deletes
	function deletes() {

		var ids = [];
		//获取勾中的主键
		$('.son:checked').each(function () {

			//push（）方法是往数组中添加元素
		ids.push($(this).val());
		//ids.join();
		});
		//alert(ids.join(","));
		//发送异步请求进行删除
		$.post("/crm/workbench/activity/deleteActivity",{'ids':ids.join()},function (data) {
			//console.log(data)
			if("forbidden"==data){
				//console.log(data)
				location.href="/crm/unauthorized.jsp";
			}
			if (data.isOk){
				alert(data.message);
				refresh(1,2)
			}
		},'json')
	}


</script>

//加入layUi插件，让弹窗更好看
<%--<script type="text/javascript" src=" " ></script>--%>

</body>
</html>