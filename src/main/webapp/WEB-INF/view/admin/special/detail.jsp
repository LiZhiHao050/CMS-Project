<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
	label {
		color: red
	}
</style>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<div class="container">
	<center><h1>专题文章设置</h1></center>
	<button type="button" onclick="goBack()" class="btn btn-green">返回</button><br>
	<label>专题名称:</label> ${special.title}
	<br/>
	<label>专题摘要:</label> ${special.digest}
	<br/>
	<label>专题文章:</label>
	<br/>
	<table class="table table-sm  table-hover table-bordered">
		<thead class="thead-light">
			<tr align="center">
				<td>文章ID</td>
				<td>文章标题</td>
				<td>发布时间</td>
				<td>操作</td>
			</tr>
		</thead>
		<c:forEach items="${special.articleList}" var="article">
			<tr align="center">
				<td>${article.id}</td>
				<td>${article.title}</td>
				<td><fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a href="javascript:remove(${special.id},${article.id})">移除</a></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	
	<label>添加新的文章</label> <input name="articleId" id="articleId" placeholder="文章ID"/>  
	   <input type="button" onclick="addArticle()" value="添加文章">
	
	<br/>
	
	<script type="text/javascript">
		
	   function addArticle(){
		   
		   $.post("/special/addArticle",{specId:${special.id},articleId:$("#articleId").val()},function(msg){
				if(msg.result == 1){
					alert(msg.message);
					$("#content-wrapper").load("/special/getDetail?id="+${special.id});
				} else {
					alert(msg.message);
				}
			},"json")
	   }
	
		function remove(specialId,articleId){
			
			$.post("/special/delArticle",{specId:specialId,articleId:articleId},function(msg){
				if(msg.result == 1){
					alert(msg.message)
					$("#content-wrapper").load("/special/getDetail?id="+specialId);
				}else{
					alert(msg.message);
				}
			},"json")
			
		}
		
		function goBack(){
			$("#content-wrapper").load("/special/managerSpecial");
		}
	</script>
			

</div>