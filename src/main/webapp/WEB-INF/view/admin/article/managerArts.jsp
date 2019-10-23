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
	$(function(){
		
		$(".form-control-sm").change(function(){
			
			$("#content-wrapper").load("/admin/managerArts?status="+$(this).val())
		})
		//下拉框回显
		$(".form-control-sm").val('${status}')
	})


	//查看文章详情
	function toDetail(id){
		$("#content-wrapper").load("/admin/getArticle?id="+id)
		
	}
	
	// 分页(同时根据状态分页)
	function page(url) {
		$("#content-wrapper").load("managerArts?pageNum="+url+"&status="+$(".form-control-sm").val());
	}

</script>


</head>
<body>
<div class="container-fluid">

		<div>
		     文章状态
			  <select class="form-control-sm" >
			    <option value="3">全部</option>
			    <option value="0">待审核</option>
			    <option value="1">已审核</option>
			    <option value="-1">审核未通过</option>
			  </select>
		
		</div>

		<table class="table table-sm  table-hover table-bordered ">
			<thead class="thead-light">
				<tr align="center">
					<td>序号</td>
					<td>作者</td>
					<td>标题</td>
					<td>当前状态</td>
					<td>发布时间</td>
					<td>栏目</td>
					<td>分类</td>
					<td>操作</td>
				</tr>
			</thead>
			<c:forEach items="${managerArts.list}" var="article" varStatus="index">
				<tr align="center">
					<td>${index.index+1 }</td>
					<td>${article.user.username}</td>
					<td>${article.title}</td>
					<td>${article.status==0?"待审核":article.status==1?"已审核":"未通过" }</td>
					<td><fmt:formatDate value="${article.created}" pattern="yyyy年MM月dd日  HH:mm:ss"/> </td>
					<td>${article.channel.name}</td>
					<td>${article.category.name}</td>
					<td><button type="button" class="btn btn-info" onclick="toDetail(${article.id})">详情</button> </td>
				</tr>

			</c:forEach>
		
		</table>
		<div>
			<input type="button" onclick="page(1)" value="首页">&nbsp;
			<input type="button" onclick="page(${managerArts.prePage == 0 ? 1 : managerArts.prePage})" value="上一页">
			&emsp;${managerArts.lastPage == 0 ? 0 : managerArts.pageNum}/${managerArts.lastPage}&emsp;
			<input type="button" onclick="page(${managerArts.nextPage == 0 ? managerArts.lastPage : managerArts.nextPage})" value="下一页">&nbsp;
			<input type="button" onclick="page(${managerArts.lastPage})" value="尾页">
			<br>
			${pageUtil}
		</div>
	</div>
</body>
</html>