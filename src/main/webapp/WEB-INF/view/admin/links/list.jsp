<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户列表</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script type="text/javascript">

	function query(){
		 //在中间区域显示地址的内容
	    $('#content-wrapper').load("/admin/managerLinks?name="+$("[name='username']").val());
	}

</script>
</head>
<body>

	<div class="container-fluid">

		<div class="form-inline">
			<label for="username">用户名:</label> <input id="username" type="text"
				name="username" value="${name}" class="form-control ">
			<button type="button" class="btn btn-info" onclick="query()">查询</button>
		</div>

		<table class="table table-sm  table-hover table-bordered ">
			<thead class="thead-light">
				<tr align="center">
					<td>序号</td>
					<td>名称</td>
					<td>链接地址</td>
					<td>创建日期</td>
					<td>更新日期</td>
				</tr>
			</thead>
			<c:forEach items="${links.list}" var="ln" varStatus="index">
				<tr align="center">
					<td>${index.index+1 }</td>
					<td>${u.username}</td>
					<td>${u.username}</td>
					<%-- <td>${u.birthday}</td> --%>
					<td>
					 	<fmt:formatDate  value="${u.create_time}" pattern="yyyy-MM-dd HH:mm"/>
					 	<%-- <c:if test="${u.createTime!=null}"></c:if> --%>
					 </td>
					<td><fmt:formatDate  value="${u.update_time}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>

			</c:forEach>

		</table>
		<div>
		    ${pageUtil}
		</div>
	</div>

<script type="text/javascript">



function moption(userid,obj){
	
	if (confirm("确认要修改当前用户的状态吗?")) {
		$.ajax({
			type:"post",
			data:{id:userid,locked:$(obj).text()=="正常" ? 1 : 0},
			url:"/admin/modifyUserStatus",
			success:function(flag){
			  	if(flag){
					$(obj).text($(obj).text()=="正常"?"禁用":"正常");
			  	}
			}
		});
	}
}


</script>
</body>
</html>